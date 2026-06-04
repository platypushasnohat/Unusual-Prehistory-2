package com.barlinc.unusual_prehistory.entity.mob.update_6.giant_campanile;

import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class GiantCampanilePart extends PartEntity<GiantCampanile> {

	protected final EntityDimensions dimensions;

	public GiantCampanilePart(GiantCampanile parent, float width, float height) {
		super(parent);
		this.dimensions = EntityDimensions.scalable(width, height);
		this.refreshDimensions();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
	}

	@Override
	protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
	}

	@Override
	protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
	}

	@Override
	public void push(double x, double y, double z) {
		this.getParent().push(x, y, z);
	}

	public boolean interactable() {
		return true;
	}

	@Override
	public final @NotNull InteractionResult interact(@NotNull Player player, @NotNull InteractionHand hand) {
		if (this.interactable()) {
			return this.getParent().interact(player, hand);
		} else {
			return InteractionResult.PASS;
		}
	}

	@Override
	public boolean hurt(@NotNull DamageSource source, float amount) {
		GiantCampanile parent = this.getParent();
		if (!this.level().isClientSide) {
			this.level().playSound(null, parent.blockPosition(), UP2SoundEvents.GIANT_CAMPANILE_BLOCK.get(), SoundSource.NEUTRAL, 1.0F, parent.getVoicePitch());
		}
		return false;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Nullable
	@Override
	public ItemStack getPickResult() {
		return this.getParent().getPickResult();
	}

	@Override
	public @NotNull Entity getRootVehicle() {
		return this.getParent().getRootVehicle();
	}

	@Override
	public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
		return this.dimensions.scale(this.getParent().getAgeScale());
	}

	public void setPosCenteredY(Vec3 pos) {
		this.setPos(pos.x, pos.y - this.getBbHeight() * 0.5F, pos.z);
	}

	@Override
	public boolean shouldBeSaved() {
		return false;
	}

	@Override
	public boolean is(@NotNull Entity entity) {
		return this == entity || this.getParent() == entity;
	}
}