package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Tartuosteus;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class TartuosteusGlideGoal extends AquaticLeapGoal {

    private final Tartuosteus tartuosteus;

    public TartuosteusGlideGoal(Tartuosteus tartuosteus) {
        super(tartuosteus, 10, 1.0D, 0.5D);
        this.tartuosteus = tartuosteus;
    }

    @Override
    public void start() {
        super.start();
        this.tartuosteus.setGliding(true);
    }

    @Override
    public void stop() {
        this.tartuosteus.setGliding(false);
    }

    @Override
    public void tick() {
        boolean flag = this.breached;
        tartuosteus.getNavigation().stop();
        if (!flag) {
            FluidState fluidstate = tartuosteus.level().getFluidState(tartuosteus.blockPosition());
            this.breached = fluidstate.is(FluidTags.WATER);
        }

        if (this.breached && !flag) {
            tartuosteus.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vec3 vec3 = tartuosteus.getDeltaMovement();
        if (vec3.y * vec3.y < (double) 0.03F && tartuosteus.getXRot() != 0.0F) {
            tartuosteus.setXRot(Mth.rotLerp(0.2F, tartuosteus.getXRot(), 0.0F));
        } else if (vec3.length() > (double) 1.0E-5F) {
            double d0 = vec3.horizontalDistance();
            double d1 = Math.atan2(-vec3.y, d0) * (double) (180F / (float) Math.PI);
            tartuosteus.setXRot((float) d1);
        }

        Vec3 movement = new Vec3(tartuosteus.getMotionDirection().getStepX(), 0, tartuosteus.getMotionDirection().getStepZ()).normalize().scale(0.53F);
        Vec3 glide = new Vec3(movement.x, vec3.y, movement.z);
        tartuosteus.setDeltaMovement(glide);
        tartuosteus.setYRot(((float) Mth.atan2(tartuosteus.getMotionDirection().getStepZ(), tartuosteus.getMotionDirection().getStepX())) * Mth.RAD_TO_DEG - 90F);
    }
}