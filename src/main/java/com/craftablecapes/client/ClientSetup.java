package com.craftablecapes.client;

import com.craftablecapes.CraftableCapes;
import com.craftablecapes.client.renderer.CapeCurioRenderer;
import com.craftablecapes.registry.CapeRegistry;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.io.File;

public class ClientSetup {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSetup.class);

    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Setting up Craftable Capes client...");

        File gameDir = Minecraft.getInstance().gameDirectory;
        File cacheDir = new File(gameDir, "cache/craftablecapes");

        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        FileCache.capeCache = new FileCache(cacheDir.toPath());
        LOGGER.info("Cape texture cache initialized at: {}", cacheDir.getAbsolutePath());

        // Register Curios renderer for ALL cape items
        CapeRegistry.ITEMS.getEntries().forEach(holder -> {
            if (holder.get() instanceof com.craftablecapes.items.CapeItem) {
                CuriosRendererRegistry.register(holder.get(), () -> new CapeCurioRenderer());
            }
        });
        LOGGER.info("Registered Curios renderers for all cape items");
    }
}
