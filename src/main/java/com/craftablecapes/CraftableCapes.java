package com.craftablecapes;

import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import com.craftablecapes.registry.CapeRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
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
    
    private static final Logger LOGGER = LogUtils.getLogger();
    
    // Register CreativeModeTab via DeferredRegister (like items!)
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    
    public static final RegistryObject<CreativeModeTab> CAPE_TAB = CREATIVE_MODE_TABS.register("capes", () ->
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.craftablecapes"))
            .icon(() -> {
                if (CapeItem.ALL_CAPES.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                return new ItemStack(
                    CapeItem.ALL_CAPES.get(new Random().nextInt(CapeItem.ALL_CAPES.size())));
            })
            .build()
    );
    
    public static final ArrayList<CapeItem> ALL_CAPES_LIST = new ArrayList<>();
    
    public CraftableCapes() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        CapeRegistry.ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);
        modEventBus.addListener(this::onBuildCreativeTabContents);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        LOGGER.info("Craftable Capes initialized!");
        LOGGER.info("Ported from Fabric (Trinkets) to Forge (Curios API)");
    }
    
    private void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CAPE_TAB.get()) {
            for (RegistryObject<Item> entry : CapeRegistry.ITEMS.getEntries()) {
                Item item = entry.get();
                if (item != null) {
                    event.accept(new ItemStack(item));
                }
            }
        }
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Craftable Capes setup complete!");
        LOGGER.info("Total capes registered: {}", ALL_CAPES_LIST.size());
    }
    
    private void enqueueIMC(final InterModEnqueueEvent event) {
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
    
    public static CapeItem registerCape(String name) {
        CapeItem cape = new CapeItem(name, new Item.Properties().stacksTo(1));
        ALL_CAPES_LIST.add(cape);
        return cape;
    }
    
    public static OnlineCapeItem registerOnlineCape(String hash, String name) {
        OnlineCapeItem cape = new OnlineCapeItem(hash, new Item.Properties().stacksTo(1));
        ALL_CAPES_LIST.add(cape);
        return cape;
    }
}
