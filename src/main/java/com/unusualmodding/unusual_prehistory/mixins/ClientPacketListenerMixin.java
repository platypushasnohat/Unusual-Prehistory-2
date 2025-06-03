package com.unusualmodding.unusual_prehistory.mixins;

import com.unusualmodding.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import com.unusualmodding.unusual_prehistory.client.sounds.KimmeridgebrachypteraeschnidiumSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(at = @At(value = "HEAD"), method = "postAddEntitySoundInstance")
    private void handleAddMob(Entity entity, CallbackInfo ci) {
        if (entity instanceof Kimmeridgebrachypteraeschnidium kimmer) {
            Minecraft.getInstance().getSoundManager().queueTickingSound(new KimmeridgebrachypteraeschnidiumSoundInstance(kimmer));
        }
    }
}
