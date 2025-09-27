package com.unusualmodding.unusual_prehistory.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.unusualmodding.unusual_prehistory.UnusualPrehistory2;
import com.unusualmodding.unusual_prehistory.entity.UP2ChestBoatEntity;
import com.unusualmodding.unusual_prehistory.entity.utils.UP2BoatType;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import java.util.Map;
import java.util.stream.Stream;

public class UP2ChestBoatRenderer extends BoatRenderer {

	private final Map<UP2BoatType.Type, Pair<ResourceLocation, ListModel<Boat>>> chestBoatResources;

	public UP2ChestBoatRenderer(EntityRendererProvider.Context renderContext, boolean isChestBoot) {
		super(renderContext, isChestBoot);
		chestBoatResources = Stream.of(UP2BoatType.Type.values()).collect(ImmutableMap.toImmutableMap((boatType) -> boatType, (boatType) -> Pair.of(
                new ResourceLocation(UnusualPrehistory2.MOD_ID, "textures/entity/chest_boat/" + boatType.getName() + ".png"),
                new ChestBoatModel(renderContext.bakeLayer(new ModelLayerLocation(new ResourceLocation("chest_boat/oak"), "main")))
		)));
	}

	public UP2ChestBoatRenderer(EntityRendererProvider.Context renderContext) {
		this(renderContext, true);
	}

	@Override
	public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
		UP2ChestBoatEntity up2ChestBoat = (UP2ChestBoatEntity) boat;
		return chestBoatResources.get(up2ChestBoat.getUP2BoatType());
	}
}
