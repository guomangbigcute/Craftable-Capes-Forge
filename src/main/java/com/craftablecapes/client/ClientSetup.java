package com.craftablecapes.client;

import com.craftablecapes.client.renderer.CapeCurioRenderer;
import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import com.google.common.hash.Hashing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.io.File;

@OnlyIn(Dist.CLIENT)
public class ClientSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSetup.class);

    public static void onClientSetup(FMLClientSetupEvent event) {
        // Register Curios renderer for ALL cape items
        for (CapeItem cape : CapeItem.ALL_CAPES) {
            CuriosRendererRegistry.register(cape, CapeCurioRenderer::new);
        }
        LOGGER.info("Registered {} cape renderers", CapeItem.ALL_CAPES.size());

        // Preload online cape textures
        Minecraft mc = Minecraft.getInstance();
        File onlineCacheDir = new File(mc.gameDirectory, "cache/craftablecapes/capes");
        onlineCacheDir.mkdirs();
        for (OnlineCapeItem onlineCape : OnlineCapeItem.ONLINE_CAPES) {
            try {
                String hash = onlineCape.getHash();
                String hashStr = Hashing.sha1().hashUnencodedChars(hash).toString();
                String subDir = hashStr.length() > 2 ? hashStr.substring(0, 2) : "xx";
                File cacheFile = new File(onlineCacheDir, subDir + "/" + hashStr);
                cacheFile.getParentFile().mkdirs();
                String url = "https://textures.minecraft.net/texture/" + hash;
                mc.getTextureManager().register(onlineCape.getTextureLocation(),
                    new HttpTexture(cacheFile, url, DefaultPlayerSkin.getDefaultTexture(), false, null));
            } catch (Exception e) {
                LOGGER.error("Failed to register online cape", e);
            }
        }
    }
}
