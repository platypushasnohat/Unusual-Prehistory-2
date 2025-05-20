package com.unusualmodding.unusual_prehistory.client.sounds;

import com.unusualmodding.unusual_prehistory.entity.KimmeridgebrachypteraeschnidiumEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2Sounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KimmeridgebrachypteraeschnidiumSoundInstance extends AbstractTickableSoundInstance {

    protected final KimmeridgebrachypteraeschnidiumEntity entity;

    public KimmeridgebrachypteraeschnidiumSoundInstance(KimmeridgebrachypteraeschnidiumEntity entity) {
        super(UP2Sounds.KIMMERIDGEBRACHYPTERAESCHNIDIUM_FLAP.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0f;
    }

    @Override
    public void tick() {
        if (this.entity.isRemoved()) {
            this.stop();
            return;
        }
        this.x = (float) this.entity.getX();
        this.y = (float) this.entity.getY();
        this.z = (float) this.entity.getZ();
        float horizontalDistance = (float) this.entity.getDeltaMovement().horizontalDistance();
        if (horizontalDistance >= 0.01F) {
            this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, 1.5F, 1.75F), 1.5F, 1.75F);
            this.volume = Mth.lerp(Mth.clamp(horizontalDistance, 0.0F, 0.15F), 0.1F, 0.4F);
        } else {
            this.pitch = 0.0F;
            this.volume = 0.0F;
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent();
    }
}
