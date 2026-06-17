package com.craftablecapes.mixin;

import com.craftablecapes.integration.CuriosIntegration;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true, require = 1)
    private void injectCapeTexture(CallbackInfoReturnable<net.minecraft.world.entity.player.PlayerSkin> cir) {
        AbstractClientPlayer self = (AbstractClientPlayer) (Object) this;
        Identifier ourCape = CuriosIntegration.getEquippedCapeTexture(self);
        if (ourCape == null) return;

        net.minecraft.world.entity.player.PlayerSkin original = cir.getReturnValue();
        if (original == null) return;

        net.minecraft.world.entity.player.PlayerSkin modified = original.with(
            new net.minecraft.world.entity.player.PlayerSkin.Patch(
                Optional.empty(),
                Optional.of(new ClientAsset.ResourceTexture(ourCape)),
                Optional.empty(),
                Optional.empty()
            )
        );
        cir.setReturnValue(modified);
    }
}
