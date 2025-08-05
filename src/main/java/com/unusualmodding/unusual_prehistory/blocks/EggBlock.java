package com.unusualmodding.unusual_prehistory.blocks;

import com.unusualmodding.unusual_prehistory.registry.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class EggBlock extends Block {

    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;

    private final VoxelShape shape;

    private final RegistryObject<EntityType> hatchedEntity;

    public EggBlock(Properties properties, RegistryObject hatchedEntity, int widthPx, int heightPx) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HATCH, 0));
        this.hatchedEntity = hatchedEntity;
        int px = (16 - widthPx) / 2;
        this.shape = Block.box(px, 0, px, 16 - px, heightPx, 16 - px);
    }

    public boolean isProperHabitat(BlockGetter reader, BlockPos pos) {
        BlockState state = reader.getBlockState(pos.below());
        return state.isSolid() && !state.is(UP2BlockTags.PREVENTS_EGG_HATCHING);
    }

    public boolean canHatchAt(BlockGetter reader, BlockPos pos){
        return isProperHabitat(reader, pos);
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
                AABB bb = new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).inflate(25, 25, 25);
                if (trampler instanceof LivingEntity && !(trampler instanceof Player player && player.isCreative())) {
                    List<Mob> list = level.getEntitiesOfClass(Mob.class, bb, living -> living.isAlive() && living.getType() == hatchedEntity.get());
                    for (Mob living : list) {
                        if (!(living instanceof TamableAnimal) || !((TamableAnimal) living).isTame() || !((TamableAnimal) living).isOwnedBy((LivingEntity) trampler)) {
                            living.setTarget((LivingEntity) trampler);
                        }
                    }
                }
                BlockState blockstate = level.getBlockState(pos);
                this.removeEgg(level, pos, blockstate);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (this.canGrow(level, level.getBlockState(pos.below())) && canHatchAt(level, pos)) {
            int i = state.getValue(HATCH);
            if (i < 2) {
                level.playSound(null, pos, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
                level.setBlock(pos, state.setValue(HATCH, i + 1), 2);
            } else {
                spawnEntity(level, pos, state);
            }
        }
    }

    public void spawnEntity(Level level, BlockPos pos, BlockState state){
        level.playSound(null, pos, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
        level.removeBlock(pos, false);
        for (int j = 0; j < getEntitiesBornFrom(state); ++j) {
            level.levelEvent(2001, pos, Block.getId(state));
            Entity entity = hatchedEntity.get().create(level);
            if (entity instanceof Animal animal) {
                animal.setAge(-24000);
            }
            entity.moveTo((double) pos.getX() + 0.3D + (double) j * 0.2D, pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
            if (!level.isClientSide) {
                level.addFreshEntity(entity);
            }
        }
    }

    protected boolean canGrow(Level level, BlockState stateBelow) {
        return !stateBelow.is(UP2BlockTags.PREVENTS_EGG_HATCHING) && level.random.nextInt(stateBelow.is(UP2BlockTags.ACCELERATES_EGG_HATCHING) ? 8 : 16) == 0;
    }

    protected int getEntitiesBornFrom(BlockState state) {
        return 1;
    }

    protected void removeEgg(Level level, BlockPos pos, BlockState state) {
        level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
        level.destroyBlock(pos, false);
    }

    private boolean canTrample(Level level, Entity trampler) {
        if (!(trampler instanceof LivingEntity)) {
            return false;
        } else {
            return trampler instanceof Player || ForgeEventFactory.getMobGriefingEvent(level, trampler);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (canHatchAt(level, pos) && !level.isClientSide) {
            level.levelEvent(2005, pos, 0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(level, player, pos, state, te, stack);
        this.removeEgg(level, pos, state);
    }
}
