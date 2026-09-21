package com.barl_inc.unusual_prehistory.mixins.client;

import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Inject(method = "getMaxZoom", at = @At("RETURN"), cancellable = true)
    private void unusualPrehistory$getMaxZoom(float maxZoom, CallbackInfoReturnable<Float> cir) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player && player.isPassenger()) {
            float zoom = 4.0F;
            if (player.getVehicle() != null) {
                if (player.getVehicle().getType() == UP2Entities.LEEDSICHTHYS.get()) {
                    cir.setReturnValue(zoom + 7.5F);
                }
            }
        }
    }
}