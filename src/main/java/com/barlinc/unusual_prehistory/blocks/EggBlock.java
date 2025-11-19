package com.barlinc.unusual_prehistory.blocks;

import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.function.Supplier;

public class EggBlock extends Block {

    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    private final VoxelShape shape;
    private final Supplier<EntityType<?>> hatchedEntity;
    private final boolean canTrample;

    public EggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int widthPx, int heightPx, boolean canTrample) {
        super(properties);
        this.canTrample = canTrample;
        this.hatchedEntity = hatchedEntity;
        int px = (16 - widthPx) / 2;
        this.shape = Block.box(px, 0, px, 16 - px, heightPx, 16 - px);
        this.registerDefaultState(this.defaultBlockState().setValue(HATCH, 0));
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        this.tryTrample(level, pos, entity, 100);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!(entity instanceof Zombie)) {
            this.tryTrample(level, pos, entity, 3);
        }
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return shape;
    }

    private void tryTrample(Level level, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(level, trampler)) {
            if (!level.isClientSide && level.random.nextInt(chances) == 0) {
                AABB boundingBox = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).inflate(25, 25, 25);
                if (trampler instanceof LivingEntity && !(trampler instanceof Player player && player.isCreative())) {
                    List<Mob> list = level.getEntitiesOfClass(Mob.class, boundingBox, living -> living.isAlive() && living.getType() == hatchedEntity.get());
                    for (Mob living : list) {
                        if (!(living instanceof TamableAnimal) || !((TamableAnimal) living).isTame() || !((TamableAnimal) living).isOwnedBy((LivingEntity) trampler)) {
                            living.setTarget((LivingEntity) trampler);
                        }
                    }
                }
                level.destroyBlock(pos, false);
            }
        }
    }

    public int getHatchLevel(BlockState state) {
        return state.getValue(HATCH);
    }

    protected boolean canHatch(BlockGetter blockGetter, BlockPos pos) {
        return !blockGetter.getBlockState(pos.below()).is(UP2BlockTags.PREVENTS_EGG_HATCHING);
    }

    public static boolean hatchBoost(BlockGetter blockGetter, BlockPos pos) {
        return blockGetter.getBlockState(pos.below()).is(UP2BlockTags.ACCELERATES_EGG_HATCHING);
    }

    private boolean isReadyToHatch(BlockState state) {
        return this.getHatchLevel(state) == 2;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (this.canHatch(level, pos)) {
            if (!this.isReadyToHatch(state)) {
                level.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                level.setBlock(pos, state.setValue(HATCH, Integer.valueOf(this.getHatchLevel(state) + 1)), 2);
            } else {
                spawnEntity(level, pos, state, random);
            }
        }
    }

    public void spawnEntity(ServerLevel level, BlockPos pos, BlockState state, RandomSource random){
        level.playSound(null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
        level.destroyBlock(pos, false);
        int i = 1;
        if (random.nextInt(5) == 0) {
            i = 2;
        }
        for (int j = 0; j < i; j++) {
            Vec3 vec3 = pos.getCenter();
            Entity entity = hatchedEntity.get().create(level);
            if (entity instanceof Animal animal) {
                animal.setBaby(true);
            }
            entity.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
            level.addFreshEntity(entity);
        }
    }

    private boolean canTrample(Level level, Entity trampler) {
        if (!(trampler instanceof LivingEntity)) {
            return false;
        } else {
            return (trampler instanceof Player || ForgeEventFactory.getMobGriefingEvent(level, trampler)) && this.canTrample;
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState blockState, boolean b) {
        boolean flag = hatchBoost(level, pos);
        boolean flag1 = !canHatch(level, pos);
        if (!level.isClientSide()) {
            if (flag) {
                level.levelEvent(3009, pos, 0);
            }
            if (flag1) {
                for(int i = 0; i < 20; ++i) {
                    double d3 = level.getRandom().nextGaussian() * 0.02D;
                    double d1 = level.getRandom().nextGaussian() * 0.02D;
                    double d2 = level.getRandom().nextGaussian() * 0.02D;
                    ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, (double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, 1, d3, d1, d2, 0.15F);
                }
            }
        }

        int i = flag ? 12000 : 24000;
        int j = i / 3;
        level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
        level.scheduleTick(pos, this, j + level.random.nextInt(300));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter blockGetter, BlockPos pos, PathComputationType computationType) {
        return false;
    }
}
