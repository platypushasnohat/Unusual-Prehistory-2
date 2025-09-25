package com.unusualmodding.unusual_prehistory.items;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.SuspiciousStoneBlockEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.atomic.AtomicBoolean;

public class ChiselItem extends Item {

    private static final double MAX_CHISEL_DISTANCE = Math.sqrt(ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) - 1.0D;

    public ChiselItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && this.calculateHitResult(player).getType() == HitResult.Type.BLOCK) {
            player.startUsingItem(context.getHand());
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BRUSH;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int tickCount) {
        if (tickCount >= 0 && entity instanceof Player player) {
            HitResult hitresult = this.calculateHitResult(entity);
            if (hitresult instanceof BlockHitResult blockhitresult) {
                if (hitresult.getType() == HitResult.Type.BLOCK) {
                    int i = this.getUseDuration(stack) - tickCount + 1;
                    boolean flag = i % 10 == 5;
                    BlockPos blockpos = blockhitresult.getBlockPos();
                    BlockState blockstate = level.getBlockState(blockpos);
                    if (flag) {
                        HumanoidArm humanoidarm = entity.getUsedItemHand() == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
                        this.spawnDustParticles(level, blockhitresult, blockstate, entity.getViewVector(0.0F), humanoidarm);
                        Block block = blockstate.getBlock();
                        SoundType soundtype = block.getSoundType(blockstate, level, blockpos, entity);
                        level.playSound(player, blockpos, soundtype.getHitSound(), SoundSource.BLOCKS);
                        if (!level.isClientSide()) {
                            BlockEntity blockentity = level.getBlockEntity(blockpos);
                            if (blockentity instanceof SuspiciousStoneBlockEntity suspiciousStoneBlockEntity) {
                                boolean flag1 = suspiciousStoneBlockEntity.chisel(level.getGameTime(), player, blockhitresult.getDirection());
                                if (flag1) {
                                    EquipmentSlot equipmentslot = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                                    stack.hurtAndBreak(1, entity, (livingEntity) -> livingEntity.broadcastBreakEvent(equipmentslot));
                                }
                            }
                        }
                    }
                    if (i % 20 == 15) {
                        if (!level.isClientSide()) {
                            Block block = blockstate.getBlock();
                            AtomicBoolean flag1 = new AtomicBoolean(false);
                            level.getRecipeManager().getAllRecipesFor(UP2RecipeTypes.CHISELING.get()).forEach(recipe -> {
                                if (flag1.get()) return;
                                if (recipe.getInput().is(block) && !flag1.get()) {
                                    level.setBlock(blockpos, recipe.getOutput().getBlock().withPropertiesOf(blockstate), 3);
                                    level.levelEvent(2001, blockpos, Block.getId(blockstate));
                                    flag1.set(true);
                                }
                            });
                        }
                    }
                    return;
                }
            }
            entity.releaseUsingItem();
        }

        else {
            entity.releaseUsingItem();
        }
    }

    private HitResult calculateHitResult(LivingEntity entity) {
        return ProjectileUtil.getHitResultOnViewVector(entity, (entity1) -> !entity1.isSpectator() && entity1.isPickable(), MAX_CHISEL_DISTANCE);
    }

    public void spawnDustParticles(Level level, BlockHitResult hitResult, BlockState state, Vec3 vec3, HumanoidArm arm) {
        int i = arm == HumanoidArm.RIGHT ? 1 : -1;
        int j = level.getRandom().nextInt(7, 12);
        BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.BLOCK, state);
        Direction direction = hitResult.getDirection();
        DustParticlesDelta dustParticlesDelta = DustParticlesDelta.fromDirection(vec3, direction);
        Vec3 location = hitResult.getLocation();
        for (int k = 0; k < j; ++k) {
            level.addParticle(blockparticleoption, location.x - (double)(direction == Direction.WEST ? 1.0E-6F : 0.0F), location.y, location.z - (double)(direction == Direction.NORTH ? 1.0E-6F : 0.0F), dustParticlesDelta.xd() * (double)i * 3.0D * level.getRandom().nextDouble(), 0.0D, dustParticlesDelta.zd() * (double)i * 3.0D * level.getRandom().nextDouble());
        }
    }

    record DustParticlesDelta(double xd, double yd, double zd) {
        public static DustParticlesDelta fromDirection(Vec3 vec3, Direction direction) {
            return switch (direction) {
                 case DOWN, UP -> new DustParticlesDelta(vec3.z(), 0.0D, -vec3.x());
                 case NORTH -> new DustParticlesDelta(1.0D, 0.0D, -0.1D);
                 case SOUTH -> new DustParticlesDelta(-1.0D, 0.0D, 0.1D);
                 case WEST -> new DustParticlesDelta(-0.1D, 0.0D, -1.0D);
                 case EAST -> new DustParticlesDelta(0.1D, 0.0D, 1.0D);
            };
        }
    }
}