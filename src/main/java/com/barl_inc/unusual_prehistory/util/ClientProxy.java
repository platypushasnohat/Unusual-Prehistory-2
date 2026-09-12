package com.barl_inc.unusual_prehistory.util;

import com.barl_inc.unusual_prehistory.block.entity.TransmogrifierBlockEntity;
import com.barl_inc.unusual_prehistory.client.sound.TransmogrifierProcessingSound;
import com.platypushasnohat.sinew.events.custom.ScreenShakeEvent;
import com.platypushasnohat.sinew.mixins.client.SoundEngineAccessor;
import com.platypushasnohat.sinew.mixins.client.SoundManagerAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap<>();
    public static final Map<BlockEntity, AbstractTickableSoundInstance> BLOCK_ENTITY_SOUND_INSTANCE_MAP = new HashMap<>();

    public static List<UUID> blockedEntityRenders = new ArrayList<>();

    public static int shaderLoadAttemptCooldown = 0;

    @Override
    public void clientInit() {
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
    @Nullable
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
    }

    @Override
    public void playWorldSound(@Nullable Object soundEmitter, byte type) {
        if (soundEmitter instanceof Entity entity && !entity.level().isClientSide) {
            return;
        }
        if (type == 0) {
            if (soundEmitter instanceof TransmogrifierBlockEntity blockEntity) {
                TransmogrifierProcessingSound sound;
                AbstractTickableSoundInstance oldSound = BLOCK_ENTITY_SOUND_INSTANCE_MAP.get(blockEntity);
                if (oldSound == null || !(oldSound instanceof TransmogrifierProcessingSound sound1 && sound1.isSameBlockEntity(blockEntity)) || oldSound.isStopped()) {
                    sound = new TransmogrifierProcessingSound(blockEntity);
                    BLOCK_ENTITY_SOUND_INSTANCE_MAP.put(blockEntity, sound);
                } else {
                    sound = sound1;
                }
                if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                    Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                }
            }
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isSoundPlaying(AbstractTickableSoundInstance sound) {
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        SoundEngine soundEngine = ((SoundManagerAccessor) soundManager).getSoundEngine();
        SoundEngineAccessor engineAccessor = (SoundEngineAccessor) soundEngine;
        return engineAccessor.getQueuedTickableSounds().contains(sound) || engineAccessor.getTickingSounds().contains(sound);
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
