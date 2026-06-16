package com.craftablecapes.mixin;

import com.craftablecapes.integration.CuriosIntegration;
import com.craftablecapes.items.CapeItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

/**
 * Directly intercept getElytraTexture() to return our cape texture
 * when a Curios cape is equipped.
 */
@OnlyIn(Dist.CLIENT)
@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {

    @Redirect(
        method = "render",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/ElytraLayer;getElytraTexture(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/resources/ResourceLocation;")
    )
    private ResourceLocation redirectGetElytraTexture(ElytraLayer<?, ?> instance, ItemStack stack, LivingEntity entity) {
        if (entity instanceof AbstractClientPlayer player) {
            Optional<CapeItem> equippedCape = CuriosIntegration.getEquippedCape(player);
            if (equippedCape.isPresent()) {
                ResourceLocation capeTexture = equippedCape.get().getTextureLocation();
                if (capeTexture != null) {
                    return capeTexture;
                }
            }
        }
        return ResourceLocation.withDefaultNamespace("textures/entity/elytra.png");
    }
}
