package com.craftablecapes;

import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Random;

/**
 * Craftable Capes - Forge 1.18.2
 * Ported from Fabric version with Trinkets to Forge with Curios API
 */
@Mod(CraftableCapes.MOD_ID)
public class CraftableCapes {
    public static final String MOD_ID = "craftablecapes";
    
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    
    // Creative Tab for all capes
    public static final CreativeModeTab CAPE_TAB = new CreativeModeTab("craftablecapes_capes") {
        @Override
        public ItemStack makeIcon() {
            if (CapeItem.ALL_CAPES.isEmpty()) {
                return ItemStack.EMPTY;
            }
            return new ItemStack(CapeItem.ALL_CAPES.get(new Random().nextInt(CapeItem.ALL_CAPES.size())));
        }
    };
    
    // Cape instances will be registered here
    public static final ArrayList<CapeItem> ALL_CAPES_LIST = new ArrayList<>();
    
    public CraftableCapes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // Register the setup method for modloading
        modEventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        modEventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        modEventBus.addListener(this::processIMC);
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        LOGGER.info("Craftable Capes initialized!");
        LOGGER.info("Ported from Fabric (Trinkets) to Forge (Curios API)");
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Craftable Capes setup complete!");
        LOGGER.info("Total capes registered: {}", ALL_CAPES_LIST.size());
    }
    
    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Register the "cape" slot type with Curios API
        InterModComms.sendTo("curios", "register_type", () -> {
            // SlotTypeMessage is the message format Curios expects
            // This creates a slot group "cape" with a single slot
            // Parameter order: identifier, priority, icon, size, addDynamic, useNativeGui
            return new top.theillusivec4.curios.api.SlotTypeMessage.Builder("cape")
                    .priority(200)
                    .icon(new ResourceLocation("curios", "slot/empty_cape_slot"))
                    .size(1)
                    .build();
        });
    }
    
    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Craftable Capes received {} IMC messages", 
                event.getIMCStream().count());
    }
    
    // Helper method to create local texture capes
    public static CapeItem registerCape(String name) {
        CapeItem cape = new CapeItem(name, new Item.Properties().tab(CAPE_TAB).stacksTo(1));
        ALL_CAPES_LIST.add(cape);
        return cape;
    }
    
    // Helper method to create online texture capes
    public static OnlineCapeItem registerOnlineCape(String hash, String name) {
        OnlineCapeItem cape = new OnlineCapeItem(hash, new Item.Properties().tab(CAPE_TAB).stacksTo(1));
        ALL_CAPES_LIST.add(cape);
        return cape;
    }
}
