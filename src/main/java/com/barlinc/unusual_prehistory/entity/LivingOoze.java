package com.barlinc.unusual_prehistory.entity;

import com.barlinc.unusual_prehistory.entity.base.PrehistoricMob;
import com.barlinc.unusual_prehistory.items.EmbryoItem;
import com.barlinc.unusual_prehistory.registry.tags.UP2ItemTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class LivingOoze extends PathfinderMob {

    private static final EntityDataAccessor<Integer> SPIT_TIME = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_CONTAINED_ENTITY = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> CONTAINED_ENTITY_TYPE = SynchedEntityData.defineId(LivingOoze.class, EntityDataSerializers.STRING);

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
        if (this.getSpitTime() > 0) this.setSpitTime(this.getSpitTime() - 1);

        if (this.getSpitTime() == 0 && !this.level().isClientSide && this.hasEntity()) {
            this.spawnMob();
        }
    }

    @Override
    protected float getEquipmentDropChance(@NotNull EquipmentSlot slot) {
        return 0;
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        ItemStack itemstack = this.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!itemstack.isEmpty()) {
            this.spawnAtLocation(itemstack);
            this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && !itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof EmbryoItem embryoItem) {
                final ResourceLocation mobType = ForgeRegistries.ENTITY_TYPES.getKey(embryoItem.toSpawn.get());
                if (mobType != null) {
                    this.setContainedEntityType(mobType.toString());
                    this.setHasEntity(true);
                    this.setSpitTime(100);
                }
            }
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (this.level().isClientSide) {
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    public void spawnMob() {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(this.getContainedEntityType()));
        if (type != null) {
            Entity entity = type.create(this.level());
            if (entity instanceof final Mob mob) {
                if (mob instanceof PrehistoricMob prehistoricMob) {
                    prehistoricMob.setAge(-24000);
                    prehistoricMob.setFromEgg(true);
                }
                mob.setYRot(random.nextFloat() * 360 - 180);
                mob.setPos(this.getMouthVec());
                if (this.level().addFreshEntity(mob)) {
                    ForgeEventFactory.onFinalizeSpawn(mob, (ServerLevel) this.level(), this.level().getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.NATURAL, null, null);
                    this.setHasEntity(false);
                    this.setContainedEntityType("minecraft:pig");
                    this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                }
            }
        }
    }

    private Vec3 getMouthVec() {
        final Vec3 vec3 = new Vec3(0, this.getBbHeight() * 0.5F, this.getBbWidth() * 0.8F).xRot(this.getXRot() * Mth.DEG_TO_RAD).yRot(-this.getYRot() * Mth.DEG_TO_RAD);
        return this.position().add(vec3);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CONTAINED_ENTITY_TYPE, "minecraft:pig");
        this.entityData.define(HAS_CONTAINED_ENTITY, false);
        this.entityData.define(SPIT_TIME, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpitTime", this.getSpitTime());
        compoundTag.putString("ContainedEntityType", this.getContainedEntityType());
        compoundTag.putBoolean("HasContainedEntity", this.hasEntity());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSpitTime(compoundTag.getInt("SpitTime"));
        this.setContainedEntityType(compoundTag.getString("ContainedEntityType"));
        this.setHasEntity(compoundTag.getBoolean("HasContainedEntity"));
    }

    public String getContainedEntityType() {
        return this.entityData.get(CONTAINED_ENTITY_TYPE);
    }

    public void setContainedEntityType(String containedEntityType) {
        this.entityData.set(CONTAINED_ENTITY_TYPE, containedEntityType);
    }

    public boolean hasEntity() {
        return this.entityData.get(HAS_CONTAINED_ENTITY);
    }

    public void setHasEntity(boolean hasEntity) {
        this.entityData.set(HAS_CONTAINED_ENTITY, hasEntity);
    }

    public int getSpitTime() {
        return this.entityData.get(SPIT_TIME);
    }

    public void setSpitTime(int time) {
        this.entityData.set(SPIT_TIME, time);
    }
}
