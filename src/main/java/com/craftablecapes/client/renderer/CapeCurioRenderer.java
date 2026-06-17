package com.craftablecapes.client.renderer;

import com.craftablecapes.items.CapeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@OnlyIn(Dist.CLIENT)
public class CapeCurioRenderer implements ICurioRenderer {

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer,
            int light, float limbSwing, float limbSwingAmount,
            float partialTicks, float ageInTicks, float netHeadYaw, float headPitch
    ) {
        if (!(stack.getItem() instanceof CapeItem capeItem)) return;
        if (!(slotContext.entity() instanceof AbstractClientPlayer player)) return;
        if (player.isInvisible()) return;
        if (!player.isModelPartShown(PlayerModelPart.CAPE)) return;

        ResourceLocation capeTexture = capeItem.getTextureLocation();
        if (capeTexture == null) return;

        boolean wearingElytra = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.ELYTRA;

        if (wearingElytra) {
            // Only render elytra model with cape texture (hide the cape cloak)
            ElytraModel<LivingEntity> elytraModel = new ElytraModel<>(
                Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.ELYTRA));
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.0F, 0.125F);
            // Copy parent model properties for correct positioning
            ((EntityModel)renderLayerParent.getModel()).copyPropertiesTo(((EntityModel)elytraModel));
            elytraModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer vc = ItemRenderer.getArmorFoilBuffer(
                buffer, RenderType.armorCutoutNoCull(capeTexture), false);
            elytraModel.renderToBuffer(poseStack, vc, light, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        } else {
            // Render cape cloak normally
            PlayerModel<?> playerModel = renderLayerParent.getModel() instanceof PlayerModel<?> pm ? pm : null;
            if (playerModel == null) return;

            poseStack.pushPose();
            poseStack.translate(0.0F, 0.0F, 0.125F);
            applyCapeTransform(poseStack, player, partialTicks);
            VertexConsumer vc = buffer.getBuffer(RenderType.entitySolid(capeTexture));
            playerModel.renderCloak(poseStack, vc, light, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }

    private static void applyCapeTransform(PoseStack ps, AbstractClientPlayer player, float pTicks) {
        double d0 = Mth.lerp(pTicks, player.xCloakO, player.xCloak) - Mth.lerp(pTicks, player.xo, player.getX());
        double d1 = Mth.lerp(pTicks, player.yCloakO, player.yCloak) - Mth.lerp(pTicks, player.yo, player.getY());
        double d2 = Mth.lerp(pTicks, player.zCloakO, player.zCloak) - Mth.lerp(pTicks, player.zo, player.getZ());
        float f = Mth.rotLerp(pTicks, player.yBodyRotO, player.yBodyRot);
        double d3 = Math.sin(f * (Math.PI / 180.0)), d4 = -Math.cos(f * (Math.PI / 180.0));
        float f1 = Mth.clamp((float)d1 * 10.0F, -6.0F, 32.0F);
        float f2 = Mth.clamp((float)(d0 * d3 + d2 * d4) * 100.0F, 0.0F, 150.0F);
        float f3 = Mth.clamp((float)(d0 * d4 - d2 * d3) * 100.0F, -20.0F, 20.0F);
        if (f2 < 0) f2 = 0;
        float f4 = Mth.lerp(pTicks, player.oBob, player.bob);
        f1 += Mth.sin(Mth.lerp(pTicks, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * f4;
        if (player.isCrouching()) f1 += 25.0F;
        ps.mulPose(Axis.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
        ps.mulPose(Axis.ZP.rotationDegrees(f3 / 2.0F));
        ps.mulPose(Axis.YP.rotationDegrees(180.0F - f3 / 2.0F));
    }
}
