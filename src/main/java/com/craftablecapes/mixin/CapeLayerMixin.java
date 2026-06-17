package com.craftablecapes.mixin;

import com.craftablecapes.integration.CuriosIntegration;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to skip vanilla CapeLayer rendering when a Curios cape is equipped.
 * This prevents double-rendering (CapeLayer + ICurioRenderer).
 */
@OnlyIn(Dist.CLIENT)
@Mixin(CapeLayer.class)
public class CapeLayerMixin {

    @Inject(
        method = "render",
        at = @At("HEAD"),
        cancellable = true
    )
    private void onRenderCape(PoseStack poseStack, MultiBufferSource buffer, int light,
                              AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                              float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
                              CallbackInfo ci) {
        boolean hasCape = CuriosIntegration.hasCapeEquipped(player);
        if (hasCape) {
            ci.cancel();
        }
    }
}
