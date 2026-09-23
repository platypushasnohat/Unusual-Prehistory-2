package com.barl_inc.unusual_prehistory.entity.cliff_fossil;

import com.barl_inc.unusual_prehistory.entity.base.AquaticPrehistoricMob;
import com.platypushasnohat.sinew.entity.ai.control.SwimmingMoveControl;
import com.platypushasnohat.sinew.entity.ai.goal.SwimWanderGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Ammonite extends AquaticPrehistoricMob {

    public final static int VARIANT_CRIOCERATITES = 0;
    public final static int VARIANT_HOPLITES = 1;
    public final static int VARIANT_NOSTOCERAS = 2;
    public final static int VARIANT_PINACOCERAS = 3;
    public final static int VARIANT_TROPITES = 4;

    public Ammonite(EntityType<? extends Ammonite> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new SwimmingMoveControl(this, 85, 10, 0.02F);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.25D)
                .add(Attributes.ARMOR, 10.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimWanderGoal(this, 1.0D, 60, 10, 7, 3, 100));
    }

    @Override
    public void setupAnimationStates() {

    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ItemTags.FISHES);
    }

    @Override
    @Nullable
    public Ammonite getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }
}
