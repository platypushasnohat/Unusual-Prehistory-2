package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.items.EmbryoItem;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LivingOoze extends PathfinderMob {

    public static final EntityDataAccessor<ItemStack> HELD_ITEM = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<Integer> MOB_TIMER = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.INT);

    public Supplier<? extends EntityType<?>> hatchedEntity;

    public LivingOoze(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.KENTROSAURUS_FOOD), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getMobTimer() > 0) this.setMobTimer(this.getMobTimer() - 1);

        if (this.getMobTimer() == 0 && !this.level().isClientSide) {
            this.spawnMob(this.getMob());
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!(itemstack.getItem() instanceof EmbryoItem)) {
            if (this.getHeldItem().isEmpty()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.setHeldItem(itemstack);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
            if (this.level().isClientSide) {
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    public void spawnMob(EntityType<?> entityType) {
        if (this.hatchedEntity == null) return;
        int i = 1;
        if (this.random.nextInt(200) == 0) {
            i = 4;
        }
        Entity entity = entityType.create(this.level());
        if (entity instanceof PrehistoricMob mob) {
            for (int j = 0; j < i; j++) {
                mob.setAge(-24000);
                mob.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(mob);
                ForgeEventFactory.onFinalizeSpawn(mob, (ServerLevel) this.level(), this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null, null);
                this.hatchedEntity = null;
            }
        }
    }

    private EntityType<?> getMob() {
        if (this.hatchedEntity == null) return null;
        else return this.hatchedEntity.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOB_TIMER, 0);
        this.entityData.define(HELD_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("MobTimer", this.getMobTimer());
        if (!this.getHeldItem().isEmpty()) {
            CompoundTag stackTag = new CompoundTag();
            this.getHeldItem().save(stackTag);
            compoundTag.put("HeldItem", stackTag);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setMobTimer(compoundTag.getInt("MobTimer"));
        if (compoundTag.contains("HeldItem")) {
            this.setHeldItem(ItemStack.of(compoundTag.getCompound("HeldItem")));
        }
    }

    public int getMobTimer() {
        return this.entityData.get(MOB_TIMER);
    }

    public void setMobTimer(int mobTimer) {
        this.entityData.set(MOB_TIMER, mobTimer);
    }

    public ItemStack getHeldItem() {
        return this.entityData.get(HELD_ITEM);
    }

    public void setHeldItem(ItemStack heldItem) {
        this.entityData.set(HELD_ITEM, heldItem);
    }
}
