package com.barlinc.unusual_prehistory.events;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.inventory.TransmogrifierScreen;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.diplocaulus.DiplocaulusDwarfModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.diplocaulus.DiplocaulusMuddyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.diplocaulus.DiplocaulusSwampyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.diplocaulus.DiplocaulusTigerModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.dunkelosteus.DunkleosteusLargeModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.dunkelosteus.DunkleosteusMediumModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.dunkelosteus.DunkleosteusSmallModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.jawless_fish.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.kimmeridgebrachypteraeschnidium.KimmeridgebrachypteraeschnidiumModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.kimmeridgebrachypteraeschnidium.KimmeridgebrachypteraeschnidiumNymphModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.unicorn.UnicornModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_1.unicorn.UnicornSkeletonModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_2.OnchopristisModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_3.LivingOozeModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_3.MetriorhynchusModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_3.TartuosteusModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.brachiosaurus.BrachiosaurusBabyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.brachiosaurus.BrachiosaurusModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_4.lobe_finned_fish.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.DesmatosuchusModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.GrugModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.PsilopterusModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.aegirocassis.AegirocassisBabyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.aegirocassis.AegirocassisModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.ambient.DelitzschalaModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_5.ambient.ZhangsolvaModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ambient.AmpyxModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ambient.SetapeditesModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.ammonite.*;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraBodyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraHeadModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.arthropleura.ArthropleuraTailModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.gastric_brooding_frog.GastricBroodingFrogModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.gastric_brooding_frog.GastricBroodingFrogletModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.leedsichthys.LeedsichthysBabyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.leedsichthys.LeedsichthysModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.lingcod.KingLingcodModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.lingcod.LingcodModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.therizinosaurus.TherizinosaurusBabyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.update_6.therizinosaurus.TherizinosaurusModel;
import com.barlinc.unusual_prehistory.client.particles.*;
import com.barlinc.unusual_prehistory.client.renderer.block.PlushieBlockRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.layers.UP2PotionEffectLayer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_1.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_2.OnchopristisRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_3.LivingOozeRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_3.MetriorhynchusRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_3.TartuosteusRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_4.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.AegirocassisRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.DesmatosuchusRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.GrugRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.PsilopterusRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.ambient.DelitzschalaRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_5.ambient.ZhangsolvaRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.*;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.ambient.AmpyxRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.ambient.SetapeditesRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura.ArthropleuraPartRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.arthropleura.ArthropleuraRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.lingcod.KingLingcodRenderer;
import com.barlinc.unusual_prehistory.client.renderer.entity.mob.update_6.lingcod.LingcodRenderer;
import com.barlinc.unusual_prehistory.registry.*;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings({"unchecked", "rawtypes"})
@EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(UP2ItemProperties::registerItemProperties);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(UP2MenuTypes.TRANSMOGRIFIER.get(), TransmogrifierScreen::new);
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(UP2Particles.GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_GINKGO_LEAVES.get(), FallingLeafParticle.GinkgoProvider::new);
        event.registerSpriteSet(UP2Particles.EEPY.get(), EepyParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.OOZE_BUBBLE.get(), OutOfWaterBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.TAR_BUBBLE.get(), OutOfWaterBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.GOLDEN_HEART.get(), GrowingHeartParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.IMPACT_STUN.get(), ImpactStunParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.OUT_OF_WATER_BUBBLE.get(), OutOfWaterBubbleParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.SWEET_GROG.get(), SpellParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.FOUL_GROG.get(), SpellParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.SAND_SNORT.get(), SandSnortParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.TUSOTEUTHIS_FLASH.get(), FlashParticle.TusoteuthisProvider::new);
        event.registerSpriteSet(UP2Particles.STUN.get(), StunParticle.Provider::new);
        event.registerSpriteSet(UP2Particles.DAZZLE.get(), LitSpellParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(UP2BlockEntities.PLUSHIE_BLOCK_ENTITY.get(), PlushieBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Update 1
        event.registerEntityRenderer(UP2Entities.CARNOTAURUS.get(), CarnotaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DIPLOCAULUS.get(), DiplocaulusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DROMAEOSAURUS.get(), DromaeosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.DUNKLEOSTEUS.get(), DunkleosteusRenderer::new);
        event.registerEntityRenderer(UP2Entities.JAWLESS_FISH.get(), JawlessFishRenderer::new);
        event.registerEntityRenderer(UP2Entities.KENTROSAURUS.get(), KentrosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KIMMERIDGEBRACHYPTERAESCHNIDIUM.get(), KimmeridgebrachypteraeschnidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.MAJUNGASAURUS.get(), MajungasaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.MEGALANIA.get(), MegalaniaRenderer::new);
        event.registerEntityRenderer(UP2Entities.STETHACANTHUS.get(), StethacanthusRenderer::new);
        event.registerEntityRenderer(UP2Entities.TALPANAS.get(), TalpanasRenderer::new);
        event.registerEntityRenderer(UP2Entities.TELECREX.get(), TelecrexRenderer::new);
        event.registerEntityRenderer(UP2Entities.UNICORN.get(), UnicornRenderer::new);

        event.registerEntityRenderer(UP2Entities.DROMAEOSAURUS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.TALPANAS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.TELECREX_EGG.get(), ThrownItemRenderer::new);

        // Update 2
        event.registerEntityRenderer(UP2Entities.ONCHOPRISTIS.get(), OnchopristisRenderer::new);

        // Update 3
        event.registerEntityRenderer(UP2Entities.LIVING_OOZE.get(), LivingOozeRenderer::new);
        event.registerEntityRenderer(UP2Entities.METRIORHYNCHUS.get(), MetriorhynchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.TARTUOSTEUS.get(), TartuosteusRenderer::new);

        // Update 4
        event.registerEntityRenderer(UP2Entities.BRACHIOSAURUS.get(), BrachiosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.COELACANTHUS.get(), CoelacanthusRenderer::new);
        event.registerEntityRenderer(UP2Entities.HIBBERTOPTERUS.get(), HibbertopterusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KAPROSUCHUS.get(), KaprosuchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.LEPTICTIDIUM.get(), LeptictidiumRenderer::new);
        event.registerEntityRenderer(UP2Entities.LOBE_FINNED_FISH.get(), LobeFinnedFishRenderer::new);
        event.registerEntityRenderer(UP2Entities.LYSTROSAURUS.get(), LystrosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PACHYCEPHALOSAURUS.get(), PachycephalosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PRAEPUSA.get(), PraepusaRenderer::new);
        event.registerEntityRenderer(UP2Entities.PSILOPTERUS.get(), PsilopterusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PTERODACTYLUS.get(), PterodactylusRenderer::new);
        event.registerEntityRenderer(UP2Entities.ULUGHBEGSAURUS.get(), UlughbegsaurusRenderer::new);

        event.registerEntityRenderer(UP2Entities.PSILOPTERUS_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(UP2Entities.PTERODACTYLUS_EGG.get(), ThrownItemRenderer::new);

        // Update 5
        event.registerEntityRenderer(UP2Entities.AEGIROCASSIS.get(), AegirocassisRenderer::new);
        event.registerEntityRenderer(UP2Entities.DELITZSCHALA.get(), DelitzschalaRenderer::new);
        event.registerEntityRenderer(UP2Entities.DESMATOSUCHUS.get(), DesmatosuchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.ZHANGSOLVA.get(), ZhangsolvaRenderer::new);

        event.registerEntityRenderer(UP2Entities.GRUG.get(), GrugRenderer::new);

        // Update 6
        event.registerEntityRenderer(UP2Entities.AMMONITE.get(), AmmoniteRenderer::new);
        event.registerEntityRenderer(UP2Entities.AMPYX.get(), AmpyxRenderer::new);
        event.registerEntityRenderer(UP2Entities.ANTARCTOPELTA.get(), AntarctopeltaRenderer::new);
        event.registerEntityRenderer(UP2Entities.ANUROGNATHUS.get(), AnurognathusRenderer::new);
        event.registerEntityRenderer(UP2Entities.ARTHROPLEURA.get(), ArthropleuraRenderer::new);
        event.registerEntityRenderer(UP2Entities.ARTHROPLEURA_PART.get(), ArthropleuraPartRenderer::new);
        event.registerEntityRenderer(UP2Entities.AUSTRORAPTOR.get(), AustroraptorRenderer::new);
        event.registerEntityRenderer(UP2Entities.BRONTOSCORPIO.get(), BrontoscorpioRenderer::new);
        event.registerEntityRenderer(UP2Entities.CONCAVENATOR.get(), ConcavenatorRenderer::new);
        event.registerEntityRenderer(UP2Entities.COTYLORHYNCHUS.get(), CotylorhynchusRenderer::new);
        event.registerEntityRenderer(UP2Entities.CRYPTOCLIDUS.get(), CryptoclidusRenderer::new);
        event.registerEntityRenderer(UP2Entities.GASTRIC_BROODING_FROG.get(), GastricBroodingFrogRenderer::new);
        event.registerEntityRenderer(UP2Entities.GIANT_CAMPANILE.get(), GiantCampanileRenderer::new);
        event.registerEntityRenderer(UP2Entities.HYNERPETON.get(), HynerpetonRenderer::new);
        event.registerEntityRenderer(UP2Entities.ICHTHYOSAURUS.get(), IchthyosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.KING_LINGCOD.get(), KingLingcodRenderer::new);
        event.registerEntityRenderer(UP2Entities.LEEDSICHTHYS.get(), LeedsichthysRenderer::new);
        event.registerEntityRenderer(UP2Entities.LINGCOD.get(), LingcodRenderer::new);
        event.registerEntityRenderer(UP2Entities.LORRAINOSAURUS.get(), LorrainosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.PROGNATHODON.get(), PrognathodonRenderer::new);
        event.registerEntityRenderer(UP2Entities.RHIZODUS.get(), RhizodusRenderer::new);
        event.registerEntityRenderer(UP2Entities.SETAPEDITES.get(), SetapeditesRenderer::new);
        event.registerEntityRenderer(UP2Entities.SHRINGASAURUS.get(), ShringasaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.SPIKE_TOOTHED_SALMON.get(), SpikeToothedSalmonRenderer::new);
        event.registerEntityRenderer(UP2Entities.THERIZINOSAURUS.get(), TherizinosaurusRenderer::new);
        event.registerEntityRenderer(UP2Entities.THYLACINE.get(), ThylacineRenderer::new);
        event.registerEntityRenderer(UP2Entities.TUSOTEUTHIS.get(), TusoteuthisRenderer::new);
        event.registerEntityRenderer(UP2Entities.WOOLLY_MAMMOTH.get(), WoollyMammothRenderer::new);
        event.registerEntityRenderer(UP2Entities.PACHYRHINOSAURUS.get(), PachyrhinosaurusRenderer::new);

        event.registerEntityRenderer(UP2Entities.ANUROGNATHUS_EGG.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // Update 1
        event.registerLayerDefinition(UP2ModelLayers.CARNOTAURUS, CarnotaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_TIGER, DiplocaulusTigerModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_SWAMPY, DiplocaulusSwampyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_MUDDY, DiplocaulusMuddyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DIPLOCAULUS_DWARF, DiplocaulusDwarfModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DROMAEOSAURUS, DromaeosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_LARGE, DunkleosteusLargeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_MEDIUM, DunkleosteusMediumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DUNKLEOSTEUS_SMALL, DunkleosteusSmallModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_ARANDASPIS, ArandaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_CEPHALASPIS, CephalaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_DORYASPIS, DoryaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_FURACACAUDA, FurcacaudaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.JAWLESS_FISH_SACABAMBASPIS, SacabambaspisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KENTROSAURUS, KentrosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM, KimmeridgebrachypteraeschnidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH, KimmeridgebrachypteraeschnidiumNymphModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MAJUNGASAURUS, MajungasaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.MEGALANIA, MegalaniaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.STETHACANTHUS, StethacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TALPANAS, TalpanasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TELECREX, TelecrexModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.UNICORN, UnicornModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.UNICORN_SKELETON, UnicornSkeletonModel::createBodyLayer);

        // Update 2
        event.registerLayerDefinition(UP2ModelLayers.ONCHOPRISTIS, OnchopristisModel::createBodyLayer);

        // Update 3
        event.registerLayerDefinition(UP2ModelLayers.LIVING_OOZE, LivingOozeModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.METRIORHYNCHUS, MetriorhynchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TARTUOSTEUS, TartuosteusModel::createBodyLayer);

        // Update 4
        event.registerLayerDefinition(UP2ModelLayers.BRACHIOSAURUS, BrachiosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.BRACHIOSAURUS_BABY, BrachiosaurusBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.COELACANTHUS, CoelacanthusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.HIBBERTOPTERUS, HibbertopterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KAPROSUCHUS, KaprosuchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LEPTICTIDIUM, LeptictidiumModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_ALLENYPTERUS, AllenypterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_EUSTHENOPTERON, EusthenopteronModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_GOOLOOGONGIA, GooloogongiaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_LACCOGNATHUS, LaccognathusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LOBE_FINNED_FISH_SCAUMENACIA, ScaumenaciaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LYSTROSAURUS, LystrosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PACHYCEPHALOSAURUS, PachycephalosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PRAEPUSA, PraepusaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PTERODACTYLUS, PterodactylusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ULUGHBEGSAURUS, UlughbegsaurusModel::createBodyLayer);

        // Update 5
        event.registerLayerDefinition(UP2ModelLayers.AEGIROCASSIS, AegirocassisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AEGIROCASSIS_BABY, AegirocassisBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DESMATOSUCHUS, DesmatosuchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.DELITZSCHALA, DelitzschalaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PSILOPTERUS, PsilopterusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ZHANGSOLVA, ZhangsolvaModel::createBodyLayer);

        event.registerLayerDefinition(UP2ModelLayers.GRUG, GrugModel::createBodyLayer);

        // Update 6
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_CRIOCERATITES, CrioceratitesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_HOPLITES, HoplitesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_NOSTOCERAS, NostocerasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_PINACOCERAS, PinacocerasModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMMONITE_TROPITES, TropitesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AMPYX, AmpyxModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ANTARCTOPELTA, AntarctopeltaModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ANUROGNATHUS, AnurognathusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ARTHROPLEURA_HEAD, ArthropleuraHeadModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ARTHROPLEURA_BODY, ArthropleuraBodyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ARTHROPLEURA_TAIL, ArthropleuraTailModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.AUSTRORAPTOR, AustroraptorModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.BRONTOSCORPIO, BrontoscorpioModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.CONCAVENATOR, ConcavenatorModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.COTYLORHYNCHUS, CotylorhynchusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.CRYPTOCLIDUS, CryptoclidusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.GASTRIC_BROODING_FROG, GastricBroodingFrogModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.GASTRIC_BROODING_FROGLET, GastricBroodingFrogletModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.GIANT_CAMPANILE, GiantCampanileModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.HYNERPETON, HynerpetonModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.ICHTHYOSAURUS, IchthyosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.KING_LINGCOD, KingLingcodModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LEEDSICHTHYS, LeedsichthysModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LEEDSICHTHYS_BABY, LeedsichthysBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LINGCOD, LingcodModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.LORRAINOSAURUS, LorrainosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.WOOLLY_MAMMOTH, WoollyMammothModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PROGNATHODON, PrognathodonModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.RHIZODUS, RhizodusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SETAPEDITES, SetapeditesModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SHRINGASAURUS, ShringasaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.SPIKE_TOOTHED_SALMON, SpikeToothedSalmonModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.THERIZINOSAURUS, TherizinosaurusModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.THERIZINOSAURUS_BABY, TherizinosaurusBabyModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.THYLACINE, ThylacineModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.TUSOTEUTHIS, TusoteuthisModel::createBodyLayer);
        event.registerLayerDefinition(UP2ModelLayers.PACHYRHINOSAURUS, PachyrhinosaurusModel::createBodyLayer);

    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, world, pos, tintIndex) -> {
                    if (world == null || pos == null) {
                        return FoliageColor.getDefaultColor();
                    }
                    return BiomeColors.getAverageFoliageColor(world, pos);
                },
                UP2Blocks.CLADOPHLEBIS.get(),
                UP2Blocks.POTTED_CLADOPHLEBIS.get()
        );
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
                    BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                    return event.getBlockColors().getColor(blockstate, null, null, tintIndex);
                },
                UP2Blocks.CLADOPHLEBIS.get()
        );
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(BuiltInRegistries.ENTITY_TYPE.stream().filter(DefaultAttributes::hasSupplier).map(entityType -> (EntityType<? extends LivingEntity>) entityType).collect(Collectors.toList()));
        entityTypes.forEach(entityType -> addLayerIfApplicable(entityType, event));
        for (PlayerSkin.Model modelType : event.getSkins()) {
            EntityRenderer<? extends Player> renderer = event.getSkin(modelType);
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new UP2PotionEffectLayer(livingRenderer));
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, EntityRenderersEvent.AddLayers event) {
        if (entityType == EntityType.ENDER_DRAGON) {
            return;
        }
        try {
            EntityRenderer<?> renderer = event.getRenderer(entityType);
            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new UP2PotionEffectLayer(livingRenderer));
            }
        } catch (Exception e) {
            UnusualPrehistory2.LOGGER.warn("Could not apply render layer to {}", BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
        }
    }
}