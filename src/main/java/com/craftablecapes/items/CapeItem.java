package com.craftablecapes.items;

import com.craftablecapes.CraftableCapes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;

public class CapeItem extends Item implements ICurioItem {
    public static final List<CapeItem> ALL_CAPES = new ArrayList<>();

    private final ResourceLocation textureLocation;

    public CapeItem(String textureName, Properties properties) {
        super(properties);
        this.textureLocation = ResourceLocation.fromNamespaceAndPath(
                "craftablecapes", "textures/capes/" + textureName + ".png");
        ALL_CAPES.add(this);
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public String getTexturePath() {
        return textureLocation.toString().replace("textures/", "").replace(".png", "");
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
