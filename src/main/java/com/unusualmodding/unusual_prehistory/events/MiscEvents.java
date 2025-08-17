package com.unusualmodding.unusual_prehistory.events;

import com.unusualmodding.unusual_prehistory.blocks.UP2LecternBlock;
import com.unusualmodding.unusual_prehistory.blocks.blockentity.UP2LecternBlockEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2Blocks;
import com.unusualmodding.unusual_prehistory.registry.UP2Items;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiscEvents {
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().getBlockState(event.getPos()).is(Blocks.LECTERN) && event.getItemStack().is(UP2Items.PALEOPEDIA.get())) {
            event.getEntity().getCooldowns().addCooldown(event.getItemStack().getItem(), 1);
            BlockState oldLectern = event.getLevel().getBlockState(event.getPos());
            if (event.getLevel().getBlockEntity(event.getPos()) instanceof LecternBlockEntity oldBlockEntity && !oldBlockEntity.hasBook()) {
                BlockState newLectern = UP2Blocks.LECTERN.get().defaultBlockState().setValue(UP2LecternBlock.FACING, oldLectern.getValue(LecternBlock.FACING)).setValue(UP2LecternBlock.POWERED, oldLectern.getValue(LecternBlock.POWERED)).setValue(UP2LecternBlock.HAS_BOOK, true);
                event.getLevel().setBlockAndUpdate(event.getPos(), newLectern);
                UP2LecternBlockEntity newBlockEntity = new UP2LecternBlockEntity(event.getPos(), newLectern);
                ItemStack bookCopy = event.getItemStack().copy();
                bookCopy.setCount(1);
                newBlockEntity.setBook(bookCopy);
                if (!event.getEntity().isCreative()) {
                    event.getItemStack().shrink(1);
                }
                event.getLevel().setBlockEntity(newBlockEntity);
                event.getEntity().swing(event.getHand(), true);
                event.getLevel().playSound(null, event.getPos(), SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
        if (event.getLevel().getBlockState(event.getPos()).is(UP2Blocks.LECTERN.get()) && event.getEntity().isCrouching()) {
            BlockState oldLectern = event.getLevel().getBlockState(event.getPos());
            BlockState newLectern = Blocks.LECTERN.defaultBlockState().setValue(UP2LecternBlock.FACING, oldLectern.getValue(UP2LecternBlock.FACING)).setValue(LecternBlock.POWERED, oldLectern.getValue(UP2LecternBlock.POWERED)).setValue(LecternBlock.HAS_BOOK, false);
            event.getLevel().setBlockAndUpdate(event.getPos(), newLectern);
            LecternBlockEntity newBlockEntity = new LecternBlockEntity(event.getPos(), newLectern);
            event.getLevel().setBlockEntity(newBlockEntity);
            newBlockEntity.clearContent();
            event.getEntity().swing(event.getHand(), true);
        }
    }
}
