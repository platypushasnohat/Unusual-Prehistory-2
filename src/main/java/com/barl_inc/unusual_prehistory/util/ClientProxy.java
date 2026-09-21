package com.barl_inc.unusual_prehistory.util;

import com.barl_inc.unusual_prehistory.block.entity.TransmogrifierBlockEntity;
import com.barl_inc.unusual_prehistory.client.sound.TransmogrifierProcessingSound;
import com.platypushasnohat.sinew.mixins.client.SoundEngineAccessor;
import com.platypushasnohat.sinew.mixins.client.SoundManagerAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap<>();
    public static final Map<BlockEntity, AbstractTickableSoundInstance> BLOCK_ENTITY_SOUND_INSTANCE_MAP = new HashMap<>();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isSoundPlaying(AbstractTickableSoundInstance sound) {
        SoundManager soundManager = Minecraft.getInstance().getSoundManager();
        SoundEngine soundEngine = ((SoundManagerAccessor) soundManager).getSoundEngine();
        SoundEngineAccessor engineAccessor = (SoundEngineAccessor) soundEngine;
        return engineAccessor.getQueuedTickableSounds().contains(sound) || engineAccessor.getTickingSounds().contains(sound);
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

    @Override
    public void clearSoundCacheFor(Entity entity) {
        ENTITY_SOUND_INSTANCE_MAP.remove(entity.getId());
    }

    @Override
    public void clearSoundCacheFor(BlockEntity entity) {
        BLOCK_ENTITY_SOUND_INSTANCE_MAP.remove(entity);
    }
}
