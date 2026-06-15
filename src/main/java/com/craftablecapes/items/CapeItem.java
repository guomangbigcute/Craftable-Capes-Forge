package com.craftablecapes.items;

import com.craftablecapes.CraftableCapes;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;

/**
 * Base cape item class for local texture capes
 * Replaces Fabric's TrinketItem with Forge + Curios compatibility
 */
public class CapeItem extends Item {
    public static final List<CapeItem> ALL_CAPES = new ArrayList<>();
    
    private final ResourceLocation textureLocation;
    
    public CapeItem(String textureName, Properties properties) {
        super(properties);
        // Texture path: assets/craftablecapes/textures/capes/{textureName}.png
        this.textureLocation = new ResourceLocation("craftablecapes", "textures/capes/" + textureName + ".png");
        
        ALL_CAPES.add(this);
    }
    
    /**
     * Get the texture location for this cape
     * @return ResourceLocation pointing to the cape texture
     */
    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
    
    /**
     * Get the texture path for rendering (without textures/ prefix and .png suffix)
     * This is what Minecraft's cape renderer expects
     * @return Texture path like "craftablecapes:capes/old_mojang"
     */
    public String getTexturePath() {
        return textureLocation.toString().replace("textures/", "").replace(".png", "");
    }
    
    /**
     * Attach Curios capability so this item can be equipped in the cape slot
     */
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new ICapabilityProvider() {
            private final LazyOptional<ICurio> curio = LazyOptional.of(() -> new ICurio() {
                @Override
                public ItemStack getStack() {
                    return stack;
                }
                
                @Override
                public boolean canEquip(SlotContext slotContext) {
                    // Only allow in the "cape" slot type
                    return "cape".equals(slotContext.identifier());
                }
                
                @Override
                public boolean canUnequip(SlotContext slotContext) {
                    return true;
                }
            });
            
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, curio);
            }
        };
    }
}
