package com.craftablecapes.client;

import com.craftablecapes.CraftableCapes;
import com.craftablecapes.client.renderer.CapeCurioRenderer;
import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import com.google.common.hash.Hashing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.io.File;

public class ClientSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSetup.class);

    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Setting up Craftable Capes client...");

        Minecraft mc = Minecraft.getInstance();
        File gameDir = mc.gameDirectory;
        File cacheDir = new File(gameDir, "cache/craftablecapes");
        if (!cacheDir.exists()) cacheDir.mkdirs();

        // Preload all online capes using Minecraft's HttpTexture system
        File onlineCacheDir = new File(cacheDir, "capes");
        if (!onlineCacheDir.exists()) onlineCacheDir.mkdirs();

        for (OnlineCapeItem onlineCape : OnlineCapeItem.ONLINE_CAPES) {
            try {
                String hash = onlineCape.getHash();
                String hashStr = Hashing.sha1().hashUnencodedChars(hash).toString();
                String subDir = hashStr.length() > 2 ? hashStr.substring(0, 2) : "xx";
                File cacheFile = new File(onlineCacheDir, subDir + "/" + hashStr);
                cacheFile.getParentFile().mkdirs();

                String url = "https://textures.minecraft.net/texture/" + hash;

                HttpTexture texture = new HttpTexture(
                    cacheFile,
                    url,
                    DefaultPlayerSkin.getDefaultTexture(),
                    false,
                    null
                );

                mc.getTextureManager().register(onlineCape.getTextureLocation(), texture);
                LOGGER.debug("Registered online cape texture: {}", onlineCape.getTextureLocation());
            } catch (Exception e) {
                LOGGER.error("Failed to register online cape texture", e);
            }
        }

        // Register Curios renderer for ALL cape items
        for (CapeItem cape : CapeItem.ALL_CAPES) {
            CuriosRendererRegistry.register(cape, () -> new CapeCurioRenderer());
        }
        LOGGER.info("Registered Curios renderers for {} cape items", CapeItem.ALL_CAPES.size());
    }
}
