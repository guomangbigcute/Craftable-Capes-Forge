package com.craftablecapes.client.renderer;

import com.craftablecapes.items.CapeItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource multiBufferSource,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (!(stack.getItem() instanceof CapeItem capeItem)) return;
        if (!(renderLayerParent.getModel() instanceof PlayerModel<?> playerModel)) return;
        if (!(slotContext.entity() instanceof AbstractClientPlayer player)) return;

        if (player.isInvisible()) return;
        if (!player.isModelPartShown(PlayerModelPart.CAPE)) return;
        
        ItemStack chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
        if (chestItem.is(Items.ELYTRA)) return;

        ResourceLocation capeTexture = capeItem.getTextureLocation();
        if (capeTexture == null) return;

        // Verify texture exists in resource manager, then use entityCutout render
        var resourceManager = Minecraft.getInstance().getResourceManager();
        boolean textureExists = resourceManager.getResource(capeTexture).isPresent();
        if (!textureExists) {
            // Try with default textures path if ours isn't found
            return;  // Skip rendering if texture file doesn't exist
        }

        // Vanilla-style cape rendering
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.0F, 0.125F);

        double d0 = Mth.lerp((double) partialTicks, player.xCloakO, player.xCloak)
                - Mth.lerp((double) partialTicks, player.xo, player.getX());
        double d1 = Mth.lerp((double) partialTicks, player.yCloakO, player.yCloak)
                - Mth.lerp((double) partialTicks, player.yo, player.getY());
        double d2 = Mth.lerp((double) partialTicks, player.zCloakO, player.zCloak)
                - Mth.lerp((double) partialTicks, player.zo, player.getZ());

        float f = Mth.rotLerp(partialTicks, player.yBodyRotO, player.yBodyRot);
        double d3 = Math.sin(f * (Math.PI / 180.0));
        double d4 = -Math.cos(f * (Math.PI / 180.0));

        float f1 = (float) d1 * 10.0F;
        f1 = Mth.clamp(f1, -6.0F, 32.0F);

        float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
        f2 = Mth.clamp(f2, 0.0F, 150.0F);

        float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
        f3 = Mth.clamp(f3, -20.0F, 20.0F);

        if (f2 < 0.0F) f2 = 0.0F;

        float f4 = Mth.lerp(partialTicks, player.oBob, player.bob);
        f1 += Mth.sin(Mth.lerp(partialTicks, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * f4;

        if (player.isCrouching()) f1 += 25.0F;

        poseStack.mulPose(Axis.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
        poseStack.mulPose(Axis.ZP.rotationDegrees(f3 / 2.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - f3 / 2.0F));

        // Use entityCutout for the same rendering as the vanilla CapeLayer
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entityCutout(capeTexture));
        playerModel.renderCloak(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
