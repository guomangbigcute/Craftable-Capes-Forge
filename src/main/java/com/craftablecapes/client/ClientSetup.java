package com.craftablecapes.client;

import com.craftablecapes.CraftableCapes;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Client-side initialization handler
 * Sets up client-only systems like the FileCache
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = CraftableCapes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    private static final Logger LOGGER = LogManager.getLogger();
    
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Setting up Craftable Capes client...");
        
        // Initialize the cape texture cache
        // Use the same directory structure as Minecraft's skin cache
        File gameDir = Minecraft.getInstance().gameDirectory;
        File cacheDir = new File(gameDir, "cache/craftablecapes");
        
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        
        FileCache.capeCache = new FileCache(cacheDir.toPath());
        LOGGER.info("Cape texture cache initialized at: {}", cacheDir.getAbsolutePath());
    }
}
