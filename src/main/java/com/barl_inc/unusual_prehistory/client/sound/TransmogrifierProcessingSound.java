package com.barl_inc.unusual_prehistory.client.sound;

import com.barl_inc.unusual_prehistory.block.TransmogrifierBlock;
import com.barl_inc.unusual_prehistory.block.entity.TransmogrifierBlockEntity;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import com.platypushasnohat.sinew.client.sound.BlockEntityTickableSound;
import net.minecraft.util.Mth;

public class TransmogrifierProcessingSound extends BlockEntityTickableSound<TransmogrifierBlockEntity> {

    private int fadeTimer = 0;

    public TransmogrifierProcessingSound(TransmogrifierBlockEntity blockEntity) {
        super(UP2SoundEvents.TRANSMOGRIFIER_PROCESSING.get(), blockEntity);
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
        if (this.blockEntity.getBlockState().getValue(TransmogrifierBlock.LIT)) {
            this.x = this.blockEntity.getBlockPos().getX() + 0.5D;
            this.y = this.blockEntity.getBlockPos().getY() + 0.5D;
            this.z = this.blockEntity.getBlockPos().getZ() + 0.5D;
            this.pitch = 1.0F;
            if (this.fadeTimer > 0) {
                this.fadeTimer--;
            }
        } else {
            this.fadeTimer++;
        }
        this.volume = Mth.clamp(1.0F - this.fadeTimer / 40.0F, 0.0F, 1.0F);
        if (this.fadeTimer > 40) {
            this.stop();
        }
    }
}