package com.craftablecapes;

import com.craftablecapes.client.ClientSetup;
import com.craftablecapes.items.CapeItem;
import com.craftablecapes.items.OnlineCapeItem;
import com.craftablecapes.registry.CapeRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Random;

@Mod(CraftableCapes.MOD_ID)
public class CraftableCapes {
    public static final String MOD_ID = "craftablecapes";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CAPE_TAB =
            CREATIVE_MODE_TABS.register("capes", () ->
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

    public CraftableCapes(IEventBus modEventBus) {
        CapeRegistry.ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(ClientSetup::onClientSetup);
        modEventBus.addListener(this::onBuildCreativeTabContents);

        LOGGER.info("Craftable Capes initialized for NeoForge!");
    }

    private void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == CAPE_TAB.get()) {
            for (DeferredHolder<Item, ? extends Item> entry : CapeRegistry.ITEMS.getEntries()) {
                Item item = entry.get();
                if (item != null) {
                    event.accept(new ItemStack(item));
                }
            }
        }
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
