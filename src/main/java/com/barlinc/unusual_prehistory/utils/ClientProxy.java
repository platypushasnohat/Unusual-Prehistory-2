package com.barlinc.unusual_prehistory.utils;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.blocks.blockentity.TransmogrifierBlockEntity;
import com.barlinc.unusual_prehistory.client.sounds.KimmeridgebrachypteraeschnidiumSound;
import com.barlinc.unusual_prehistory.client.sounds.TransmogrifierSound;
import com.barlinc.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import com.barlinc.unusual_prehistory.events.ClientForgeEvents;
import com.barlinc.unusual_prehistory.events.ScreenShakeEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = UnusualPrehistory2.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap<>();
    public static final Map<BlockEntity, AbstractTickableSoundInstance> BLOCK_ENTITY_SOUND_INSTANCE_MAP = new HashMap<>();

    public static List<UUID> blockedEntityRenders = new ArrayList<>();

    @Override
    public void commonInit() {
    }

    @Override
    public void clientInit() {
        MinecraftForge.EVENT_BUS.register(new ClientForgeEvents());
    }

    @Override
    public boolean isKeyDown(int keyType) {
        if (keyType == 0) {
            return Minecraft.getInstance().options.keyJump.isDown();
        }
        if (keyType == 1) {
            return Minecraft.getInstance().options.keySprint.isDown();
        }
        if (keyType == 3) {
            return Minecraft.getInstance().options.keyAttack.isDown();
        }
        if (keyType == 4) {
            return Minecraft.getInstance().options.keyShift.isDown();
        }
        return false;
    }

    @Override
    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public boolean isFirstPersonPlayer(Entity entity) {
        return entity.equals(Minecraft.getInstance().cameraEntity) && Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

    @Override
    public void blockRenderingEntity(UUID id) {
        blockedEntityRenders.add(id);
    }

    @Override
    public void releaseRenderingEntity(UUID id) {
        blockedEntityRenders.remove(id);
    }

    @Override
    public void screenShake(ScreenShakeEvent event) {
        ClientForgeEvents.SCREEN_SHAKE_EVENTS.add(event);
    }

    @Override
    public void playWorldSound(@Nullable Object soundEmitter, byte type) {
        if (soundEmitter instanceof Entity entity && !entity.level().isClientSide) {
            return;
        }
        switch (type) {
            case 0:
                if (soundEmitter instanceof TransmogrifierBlockEntity blockEntity) {
                    TransmogrifierSound sound;
                    AbstractTickableSoundInstance oldSound = BLOCK_ENTITY_SOUND_INSTANCE_MAP.get(blockEntity);
                    if (oldSound == null || !(oldSound instanceof TransmogrifierSound sound1 && sound1.isSameBlockEntity(blockEntity)) || oldSound.isStopped()) {
                        sound = new TransmogrifierSound(blockEntity);
                        BLOCK_ENTITY_SOUND_INSTANCE_MAP.put(blockEntity, sound);
                    } else {
                        sound = (TransmogrifierSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 1:
                if (soundEmitter instanceof Kimmeridgebrachypteraeschnidium entity) {
                    KimmeridgebrachypteraeschnidiumSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof KimmeridgebrachypteraeschnidiumSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new KimmeridgebrachypteraeschnidiumSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (KimmeridgebrachypteraeschnidiumSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
        }
    }

    private boolean isSoundPlaying(AbstractTickableSoundInstance sound) {
        return Minecraft.getInstance().getSoundManager().soundEngine.queuedTickableSounds.contains(sound) || Minecraft.getInstance().getSoundManager().soundEngine.tickingSounds.contains(sound);
    }

    @Override
    public void clearSoundCacheFor(Entity entity) {
        ENTITY_SOUND_INSTANCE_MAP.remove(entity.getId());
    }

    @Override
    public void clearSoundCacheFor(BlockEntity entity) {
        BLOCK_ENTITY_SOUND_INSTANCE_MAP.remove(entity);
    }
}
