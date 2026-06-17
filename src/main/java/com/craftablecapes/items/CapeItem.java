package com.craftablecapes.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;

public class CapeItem extends Item implements ICurioItem {
    public static final List<CapeItem> ALL_CAPES = new ArrayList<>();

    private final String textureName;

    public CapeItem(String textureName, Properties properties) {
        super(properties);
        this.textureName = textureName;
        ALL_CAPES.add(this);
    }

    public String getTextureName() {
        return textureName;
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return "cape".equals(slotContext.identifier());
    }

    @Override
    public boolean canUnequip(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
