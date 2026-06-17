package com.craftablecapes.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

/**
 * Curios renderer for cape items.
 * Cape rendering handled by vanilla CapeLayer via AbstractClientPlayerMixin
 * (injects our cape texture into PlayerSkin.getSkin()).
 */
public class CapeCurioRenderer implements ICurioRenderer {

    @Override
    public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack poseStack,
            MultiBufferSource bufferSource, int light, S renderState,
            RenderLayerParent<S, M> renderLayerParent,
            EntityRendererProvider.Context rendererContext,
            float partialTicks, float someFloat) {
        // Cape rendered by vanilla CapeLayer via AbstractClientPlayerMixin
    }
}
