package com.unusualmodding.unusual_prehistory.items;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.FossilBlockEntity;
import com.unusualmodding.unusual_prehistory.registry.UP2RecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class ChiselItem extends Item {

    private static final double MAX_CHISEL_DISTANCE = Math.sqrt(ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) - 1.0D;

    public ChiselItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && this.calculateHitResult(player, 0).getType() == HitResult.Type.BLOCK) {
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
            HitResult blockHitResult = this.calculateHitResult(entity, 0);
            HitResult entityHitResult = this.calculateHitResult(entity, -2);
            int i = this.getUseDuration(stack) - tickCount + 1;
            boolean flag = i % 10 == 5;
            EquipmentSlot arm = stack.equals(player.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
            if (blockHitResult instanceof BlockHitResult blockhitresult) {
                if (blockHitResult.getType() == HitResult.Type.BLOCK) {
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
                            if (blockentity instanceof FossilBlockEntity suspiciousStoneBlockEntity) {
                                boolean flag1 = suspiciousStoneBlockEntity.chisel(level.getGameTime(), player);
                                if (flag1) {
                                    stack.hurtAndBreak(1, entity, (livingEntity) -> livingEntity.broadcastBreakEvent(arm));
                                }
                            }
                        }
                    }
                    if (i % 10 == 5) {
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
            } else if (entityHitResult instanceof EntityHitResult entityhitresult && entityHitResult.getType() == HitResult.Type.ENTITY) {
                if (flag) {
                    Entity hitResultEntity = entityhitresult.getEntity();
                    boolean hurt = hitResultEntity.hurt(entity.damageSources().mobAttack(player), 1);
                    if (hurt) {
                        stack.hurtAndBreak(1, entity, (livingEntity) -> livingEntity.broadcastBreakEvent(arm));
                    } else {
                        return;
                    }
                }
                return;
            }
            entity.releaseUsingItem();
        } else {
            entity.releaseUsingItem();
        }
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity livingEntity, InteractionHand hand) {
        if (this.calculateHitResult(player, -2).getType() == HitResult.Type.ENTITY) {
            player.startUsingItem(hand);
        }
        return InteractionResult.CONSUME;
    }

    private HitResult calculateHitResult(LivingEntity livingEntity, double interactionDistance) {
        Vec3 vec3 = livingEntity.getViewVector(0.0F).scale(MAX_CHISEL_DISTANCE + interactionDistance);
        Level level = livingEntity.level();
        Vec3 vec31 = livingEntity.getEyePosition();
        Predicate<Entity> predicate = (entity) -> !entity.isSpectator() && entity.isPickable();
        return getChiselHitResult(vec31, livingEntity, predicate, vec3, level);
    }

    private HitResult getChiselHitResult(Vec3 eye, Entity viewer, Predicate<Entity> predicate, Vec3 view, Level level) {
        Vec3 vec3 = eye.add(view);
        HitResult hitresult = level.clip(new ClipContext(eye, vec3, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, viewer));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec3 = hitresult.getLocation();
        }
        HitResult hitresult1 = ProjectileUtil.getEntityHitResult(level, viewer, eye, vec3, viewer.getBoundingBox().expandTowards(view).inflate(1.0), predicate);
        if (hitresult1 != null) {
            hitresult = hitresult1;
        }
        return hitresult;
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