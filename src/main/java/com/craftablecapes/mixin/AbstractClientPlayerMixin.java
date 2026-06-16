package com.craftablecapes.mixin;

import com.craftablecapes.integration.CuriosIntegration;
import com.craftablecapes.items.CapeItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/**
 * Mixin to override PlayerSkin returned by getSkin()
 * CapeLayer uses getSkin() to get the cape texture, so we intercept that.
 */
@OnlyIn(Dist.CLIENT)
@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    @Inject(
        method = "getSkin",
        at = @At("RETURN"),
        cancellable = true
    )
    private void onGetSkin(CallbackInfoReturnable<PlayerSkin> cir) {
        PlayerSkin originalSkin = cir.getReturnValue();
        if (originalSkin == null) return;

        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;

        // Check if player has a cape equipped in Curios
        Optional<CapeItem> equippedCape = CuriosIntegration.getEquippedCape(player);
        if (equippedCape.isEmpty()) return;

        CapeItem cape = equippedCape.get();
        ResourceLocation capeTexture = cape.getTextureLocation();
        
        // Create new PlayerSkin with the cape's texture replacing the default cape slot
        PlayerSkin modifiedSkin = new PlayerSkin(
            originalSkin.texture(),
            originalSkin.textureUrl(),
            capeTexture,
            originalSkin.elytraTexture(),
            originalSkin.model(),
            originalSkin.secure()
        );

        cir.setReturnValue(modifiedSkin);
    }
}
