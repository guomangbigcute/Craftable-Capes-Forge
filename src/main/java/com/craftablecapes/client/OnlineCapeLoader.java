package com.craftablecapes.client;

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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class OnlineCapeLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineCapeLoader.class);

    public static void loadOnlineCap() {
        // Already loaded
    }

    public static void downloadAndRegister(OnlineCapeItem cape) {
        CompletableFuture.runAsync(() -> {
            try {
                String url = cape.getTextureUrl();
                LOGGER.info("Downloading online cape texture: {}", url);

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
                HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

                if (response.statusCode() != 200) {
                    LOGGER.error("Failed to download cape: {} (HTTP {})", url, response.statusCode());
                    return;
                }

                byte[] imageBytes = response.body();
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
                if (bufferedImage == null) {
                    LOGGER.error("Failed to decode cape image from: {}", url);
                    return;
                }

                // Convert BufferedImage to NativeImage
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

                // Register with TextureManager on render thread
                // Must match the path that ClientAsset.ResourceTexture builds:
                // "textures/" + cape.getTextureName() + ".png"
                Minecraft.getInstance().execute(() -> {
                    Identifier id = Identifier.fromNamespaceAndPath("craftablecapes",
                            "textures/capes/" + cape.getTextureName() + ".png");
                    Minecraft.getInstance().getTextureManager().register(id,
                            new DynamicTexture(() -> cape.getTextureName(), nativeImage));
                    LOGGER.info("Registered online cape texture: {}", id);
                });
            } catch (Exception e) {
                LOGGER.error("Error downloading cape texture", e);
            }
        });
    }
}
