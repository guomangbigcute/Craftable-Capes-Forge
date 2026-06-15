package com.craftablecapes.mixin;

import com.craftablecapes.integration.CuriosIntegration;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to override the cape texture retrieval for players
 * Injects into getCloakTextureLocation() to return custom cape textures from Curios
 */
@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
    
    /**
     * Inject at the beginning of getCloakTextureLocation() to check for Curios cape first
     * If a cape is equipped in Curios, return its texture and cancel the original method
     */
    @Inject(
        method = "getCloakTextureLocation",
        at = @At("HEAD"), 
        cancellable = true
    )
    private void onGetCapeTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        
        // Try to get the equipped cape texture from Curios
        ResourceLocation capeTexture = CuriosIntegration.getEquippedCapeTexture(player);
        
        // If a cape is equipped, return its texture and cancel the vanilla method
        if (capeTexture != null) {
            cir.setReturnValue(capeTexture);
            cir.cancel();
        }
    }
}
