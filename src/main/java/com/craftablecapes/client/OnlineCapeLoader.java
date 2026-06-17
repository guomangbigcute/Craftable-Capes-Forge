package com.craftablecapes.client;

import com.craftablecapes.CraftableCapes;
import com.craftablecapes.items.OnlineCapeItem;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Downloads online cape textures from Mojang's texture server and registers
 * them as DynamicTextures. Registration is deferred to {@link #registerAll()}
 * which should be called after resource reload completes (e.g., on first client tick).
 *
 * Textures are cached to disk so subsequent launches are instant.
 */
public class OnlineCapeLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineCapeLoader.class);

    /** Cache directory for downloaded cape textures */
    private static Path cacheDir;

    /** Prepared NativeImages keyed by hash, ready to register with TextureManager */
    private static final Map<String, NativeImage> preparedTextures = new ConcurrentHashMap<>();

    /** Whether registerAll() has been called at least once */
    private static boolean registered = false;

    public static void initCache(Path gameDir) {
        cacheDir = gameDir.resolve("craftablecapes_cache").resolve("capes");
        try {
            Files.createDirectories(cacheDir);
            LOGGER.info("Online cape cache directory: {}", cacheDir);
        } catch (IOException e) {
            LOGGER.error("Failed to create cache directory", e);
        }
    }

    /**
     * Download cape texture and prepare NativeImage for later registration.
     * Safe to call on any thread.
     */
    public static void downloadAndCache(OnlineCapeItem cape) {
        String hash = cape.getHash();

        if (preparedTextures.containsKey(hash)) return;

        try {
            byte[] imageData = loadImageData(cape);
            if (imageData == null) {
                LOGGER.error("Failed to load online cape: {}", hash);
                return;
            }

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
            if (bufferedImage == null) {
                LOGGER.error("Failed to decode cape image: {}", hash);
                return;
            }

            NativeImage nativeImage = new NativeImage(bufferedImage.getWidth(), bufferedImage.getHeight(), false);
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    int argb = bufferedImage.getRGB(x, y);
                    int a = (argb >> 24) & 0xFF;
                    int r = (argb >> 16) & 0xFF;
                    int g = (argb >> 8) & 0xFF;
                    int b = argb & 0xFF;
                    nativeImage.setPixelABGR(x, y, (a << 24) | (b << 16) | (g << 8) | r);
                }
            }

            preparedTextures.put(hash, nativeImage);
            LOGGER.info("Prepared online cape texture: {}", hash.substring(0, 8));
        } catch (Exception e) {
            LOGGER.error("Error loading cape texture: {}", hash, e);
        }
    }

    /**
     * Register all prepared textures with the TextureManager.
     * Must be called on the render thread.
     * Called from first ClientTickEvent (after resource reload completes).
     */
    public static void registerAll() {
        if (registered) return;
        registered = true;

        Minecraft.getInstance().execute(() -> {
            int count = 0;
            for (Map.Entry<String, NativeImage> entry : preparedTextures.entrySet()) {
                String hash = entry.getKey();
                Identifier capeId = Identifier.fromNamespaceAndPath(CraftableCapes.MOD_ID,
                        "textures/capes/" + hash + ".png");

                NativeImage original = entry.getValue();
                NativeImage copy = new NativeImage(original.getWidth(), original.getHeight(), false);
                copy.copyFrom(original);

                Minecraft.getInstance().getTextureManager().register(capeId,
                        new DynamicTexture(() -> "cape_" + hash.substring(0, 8), copy));
                count++;
            }
            LOGGER.info("Registered {} online cape textures", count);
        });
    }

    private static byte[] loadImageData(OnlineCapeItem cape) throws Exception {
        String hash = cape.getHash();

        // Try disk cache first
        if (cacheDir != null) {
            Path cachedFile = cacheDir.resolve(hash + ".png");
            if (Files.exists(cachedFile)) {
                LOGGER.info("Loading cape from cache: {}", hash.substring(0, 8));
                return Files.readAllBytes(cachedFile);
            }
        }

        // Download from Mojang
        String url = cape.getTextureUrl();
        LOGGER.info("Downloading cape: {}", url);

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(10))
                .build();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(java.time.Duration.ofSeconds(15))
                .build();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() != 200) {
            LOGGER.error("Download failed: {} (HTTP {})", url, response.statusCode());
            return null;
        }

        byte[] data = response.body();

        if (cacheDir != null && data != null) {
            Path cachedFile = cacheDir.resolve(hash + ".png");
            Files.write(cachedFile, data);
            LOGGER.info("Cached cape: {}", hash.substring(0, 8));
        }

        return data;
    }
}
