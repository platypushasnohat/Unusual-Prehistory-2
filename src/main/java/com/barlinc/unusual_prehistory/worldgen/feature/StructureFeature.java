package com.barlinc.unusual_prehistory.worldgen.feature;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.mixins.StructureTemplateAccessor;
import com.barlinc.unusual_prehistory.utils.GeneralUtils;
import com.barlinc.unusual_prehistory.worldgen.feature.config.StructureFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class StructureFeature<T extends StructureFeatureConfig> extends Feature<T> {

    private static final ResourceLocation EMPTY = new ResourceLocation("minecraft", "empty");

    public StructureFeature(Codec<T> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean place(FeaturePlaceContext<T> context) {
        ResourceLocation entry = GeneralUtils.getRandomEntry(context.config().structureLocationAndWeights(), context.random());
        StructureTemplateManager structureManager = context.level().getLevel().getStructureManager();
        Optional<StructureTemplate> template = structureManager.get(entry);
        if (template.isEmpty()) {
            UnusualPrehistory2.LOGGER.error("Identifier to the specified nbt file was not found! : {}", entry);
            return false;
        }
        Rotation rotation = Rotation.getRandom(context.random());

        // For proper offsetting the feature so it rotate properly around position parameter.
        BlockPos halfLengths = new BlockPos(template.get().getSize().getX() / 2, template.get().getSize().getY() / 2, template.get().getSize().getZ() / 2);

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(context.origin());

        // offset the feature's position
        BlockPos position = context.origin().above(context.config().structureYOffset());

        StructurePlaceSettings placeSettings = (new StructurePlaceSettings()).setRotation(rotation).setRotationPivot(halfLengths).setIgnoreEntities(false);
        Registry<StructureProcessorList> processorLists = context.level().getLevel().getServer().registryAccess().registryOrThrow(Registries.PROCESSOR_LIST);
        StructureProcessorList emptyProcessor = processorLists.get(EMPTY);

        Optional<StructureProcessorList> processor = processorLists.getOptional(context.config().processor());
        processor.orElse(emptyProcessor).list().forEach(placeSettings::addProcessor); // add all processors
        mutable.set(position).move(-halfLengths.getX(), 0, -halfLengths.getZ()); // pivot
        template.get().placeInWorld(context.level(), mutable, mutable, placeSettings, context.random(), Block.UPDATE_ALL);

        // Post-processors
        // For all processors that are sensitive to neighboring blocks such as vines.
        // Post processors will place the blocks themselves so we will not do anything with the return of Structure.process
        placeSettings.clearProcessors();
        Optional<StructureProcessorList> postProcessor = processorLists.getOptional(context.config().postProcessor());
        postProcessor.orElse(emptyProcessor).list().forEach(placeSettings::addProcessor); // add all post processors
        List<StructureTemplate.StructureBlockInfo> list = placeSettings.getRandomPalette(((StructureTemplateAccessor) template.get()).getBlocks(), mutable).blocks();
        StructureTemplate.processBlockInfos(context.level(), mutable, mutable, placeSettings, list);

        return true;
    }
}