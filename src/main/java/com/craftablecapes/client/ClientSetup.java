package com.craftablecapes.client;

import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import com.craftablecapes.client.renderer.CapeCurioRenderer;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class ClientSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSetup.class);

    public static void onClientSetup(FMLClientSetupEvent event) {
        java.nio.file.Path gameDir = Minecraft.getInstance().gameDirectory.toPath();
        OnlineCapeLoader.initCache(gameDir);
        FileCache.capeCache = new FileCache(gameDir.resolve("craftablecapes_cache").resolve("capes"));

        for (CapeItem cape : CapeItem.ALL_CAPES) {
            ICurioRenderer.register(cape, CapeCurioRenderer::new);
        }
        LOGGER.info("Registered {} cape renderers", CapeItem.ALL_CAPES.size());

        for (OnlineCapeItem onlineCape : OnlineCapeItem.ONLINE_CAPES) {
            OnlineCapeLoader.downloadAndCache(onlineCape);
        }
        LOGGER.info("Prepared {} online cape textures", OnlineCapeItem.ONLINE_CAPES.size());
    }

    /**
     * Register DynamicTextures on the first client tick.
     * This runs AFTER resource reload completes, so DynamicTextures won't be cleared.
     */
    public static void onClientTick(ClientTickEvent.Pre event) {
        OnlineCapeLoader.registerAll();
    }
}
