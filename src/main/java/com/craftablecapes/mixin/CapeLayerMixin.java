package com.craftablecapes.mixin;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin to change CapeLayer rendering from solid to cutout
 * This allows cape textures with transparency to render correctly
 * Equivalent to Fabric's CapeFeatureRendererMixin in 1.21.4
 */
@OnlyIn(Dist.CLIENT)
@Mixin(CapeLayer.class)
public class CapeLayerMixin {

    @Redirect(
        method = "render",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;")
    )
    private RenderType redirectCapeRenderLayer(ResourceLocation location) {
        return RenderType.entityCutout(location);
    }
}
