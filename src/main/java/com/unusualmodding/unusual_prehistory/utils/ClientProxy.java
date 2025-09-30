package com.unusualmodding.unusual_prehistory.utils;

import com.unusualmodding.unusual_prehistory.blocks.blockentity.TransmogrifierBlockEntity;
import com.unusualmodding.unusual_prehistory.client.sounds.KimmeridgebrachypteraeschnidiumSound;
import com.unusualmodding.unusual_prehistory.client.sounds.TransmogrifierSound;
import com.unusualmodding.unusual_prehistory.entity.Kimmeridgebrachypteraeschnidium;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap<>();
    public static final Map<BlockEntity, AbstractTickableSoundInstance> BLOCK_ENTITY_SOUND_INSTANCE_MAP = new HashMap<>();

    @Override
    public void commonInit() {
    }

    @Override
    public void clientInit() {
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
