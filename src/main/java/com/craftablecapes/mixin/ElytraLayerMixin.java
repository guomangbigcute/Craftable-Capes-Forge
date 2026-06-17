package com.craftablecapes.mixin;

import com.craftablecapes.integration.CuriosIntegration;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Cancel vanilla ElytraLayer when a Curios cape is equipped.
 */
@OnlyIn(Dist.CLIENT)
@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {

    @Redirect(
        method = "render",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/ElytraLayer;shouldRender(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Z")
    )
    private boolean redirectShouldRender(ElytraLayer instance, ItemStack stack, LivingEntity entity) {
        // If player has Curios cape equipped, skip vanilla elytra rendering
        if (entity instanceof AbstractClientPlayer player && CuriosIntegration.hasCapeEquipped(player)) {
            return false;
        }
        // For non-player entities or players without Curios cape, vanilla behavior
        return stack.is(net.minecraft.world.item.Items.ELYTRA);
    }
}
