package com.barlinc.unusual_prehistory.client.renderer;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.entity.UP2Boat;
import com.barlinc.unusual_prehistory.entity.utils.UP2BoatType;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

public class UP2BoatRenderer extends BoatRenderer {

	private final Map<UP2BoatType.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

	public UP2BoatRenderer(EntityRendererProvider.Context renderContext, boolean isChestBoat) {
		super(renderContext, isChestBoat);
		boatResources = Stream.of(UP2BoatType.Type.values()).collect(ImmutableMap.toImmutableMap((boatType) -> boatType, (boatType) -> Pair.of(
                UnusualPrehistory2.modPrefix("textures/entity/boat/" + boatType.getName() + ".png"),
                new BoatModel(renderContext.bakeLayer(new ModelLayerLocation(new ResourceLocation("boat/oak"), "main")))
        )));
	}

	public UP2BoatRenderer(EntityRendererProvider.Context renderContext) {
		this(renderContext, false);
	}

	@Override
	public @NotNull Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(@NotNull Boat boat) {
		UP2Boat up2Boat = (UP2Boat) boat;
		return boatResources.get(up2Boat.getUP2BoatType());
	}
}

