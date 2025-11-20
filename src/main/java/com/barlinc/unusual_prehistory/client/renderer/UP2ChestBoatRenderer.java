package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.UP2ChestBoat;
import com.barlinc.unusual_prehistory.entity.utils.UP2BoatType;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

public class UP2ChestBoatRenderer extends BoatRenderer {

	private final Map<UP2BoatType.Type, Pair<ResourceLocation, ListModel<Boat>>> chestBoatResources;

	public UP2ChestBoatRenderer(EntityRendererProvider.Context renderContext, boolean isChestBoot) {
		super(renderContext, isChestBoot);
		chestBoatResources = Stream.of(UP2BoatType.Type.values()).collect(ImmutableMap.toImmutableMap((boatType) -> boatType, (boatType) -> Pair.of(
                UnusualPrehistory2.modPrefix("textures/entity/chest_boat/" + boatType.getName() + ".png"),
                new ChestBoatModel(renderContext.bakeLayer(new ModelLayerLocation(new ResourceLocation("chest_boat/oak"), "main")))
		)));
	}

	public UP2ChestBoatRenderer(EntityRendererProvider.Context renderContext) {
		this(renderContext, true);
	}

	@Override
	public @NotNull Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(@NotNull Boat boat) {
		UP2ChestBoat up2ChestBoat = (UP2ChestBoat) boat;
		return chestBoatResources.get(up2ChestBoat.getUP2BoatType());
	}
}
