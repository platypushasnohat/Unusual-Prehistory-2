package com.unusualmodding.unusual_prehistory.client.sounds;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.TransmogrifierBlockEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TransmogrifierSound extends BlockEntityTickableSound<TransmogrifierBlockEntity> {

    private int fade = 0;

    public TransmogrifierSound(TransmogrifierBlockEntity blockEntity) {
        super(UP2SoundEvents.TRANSMOGRIFIER_LOOP.get(), blockEntity);
        this.volume = 0.1F;
    }

    @Override
    public boolean canPlaySound() {
        return !this.blockEntity.isRemoved();
    }

    @Override
    public boolean isSameBlockEntity(TransmogrifierBlockEntity blockEntity) {
        return super.isSameBlockEntity(blockEntity);
    }

    @Override
    public void tick() {
        if (this.blockEntity.isTransmogrifying()) {
            this.x = this.blockEntity.getBlockPos().getX() + 0.5D;
            this.y = this.blockEntity.getBlockPos().getY() + 0.5D;
            this.z = this.blockEntity.getBlockPos().getZ() + 0.5D;
            this.pitch = 1.0F;
            if (fade > 0) {
                fade--;
            }
        } else {
            fade++;
        }
        this.volume = Mth.clamp(1F - fade / 40F, 0F, 1F);
        if (fade > 40) {
            this.stop();
        }
    }
}