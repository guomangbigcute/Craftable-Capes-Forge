package com.craftablecapes;

import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import com.craftablecapes.registry.CapeRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
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
 * Craftable Capes - Forge 1.20.1
 * Ported from Fabric version with Trinkets to Forge with Curios API
 */
@Mod(CraftableCapes.MOD_ID)
public class CraftableCapes {
    public static final String MOD_ID = "craftablecapes";
    
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    
    // Creative Tab for all capes (1.20.1+ builder pattern)
    public static final CreativeModeTab CAPE_TAB = CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.craftablecapes"))
            .icon(() -> {
                if (CapeItem.ALL_CAPES.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                return new ItemStack(CapeItem.ALL_CAPES.get(new Random().nextInt(CapeItem.ALL_CAPES.size())));
            })
            .displayItems((parameters, output) -> {
                for (Item item : CapeRegistry.ITEMS.getEntries().stream().map(reg -> reg.get()).toList()) {
                    output.accept(new ItemStack(item));
                }
            })
            .build();
    
    // Cape instances will be registered here
    public static final ArrayList<CapeItem> ALL_CAPES_LIST = new ArrayList<>();
    
    public CraftableCapes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // Register DeferredRegister for items (1.20.1+)
        CapeRegistry.ITEMS.register(modEventBus);
        
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
    // Note: tab() was removed in 1.20.1; items go into tab via displayItems()
    public static CapeItem registerCape(String name) {
        CapeItem cape = new CapeItem(name, new Item.Properties().stacksTo(1));
        ALL_CAPES_LIST.add(cape);
        return cape;
    }
    
    // Helper method to create online texture capes
    public static OnlineCapeItem registerOnlineCape(String hash, String name) {
        OnlineCapeItem cape = new OnlineCapeItem(hash, new Item.Properties().stacksTo(1));
        ALL_CAPES_LIST.add(cape);
        return cape;
    }
}
