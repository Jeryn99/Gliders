package net.venturecraft.gliders.client.sound;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MovingSound implements SoundInstance {
    public MovingSound(Player player, SoundEvent soundEvent, SoundSource category, boolean repeat, Supplier<Boolean> stopCondition, float volume, RandomSource randomSource) {
    }

    @Override
    public ResourceLocation getLocation() {
        return null;
    }

    @Nullable
    @Override
    public WeighedSoundEvents resolve(SoundManager manager) {
        return null;
    }

    @Override
    public Sound getSound() {
        return null;
    }

    @Override
    public SoundSource getSource() {
        return null;
    }

    @Override
    public boolean isLooping() {
        return false;
    }

    @Override
    public boolean isRelative() {
        return false;
    }

    @Override
    public int getDelay() {
        return 0;
    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public float getPitch() {
        return 0;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getZ() {
        return 0;
    }

    @Override
    public Attenuation getAttenuation() {
        return null;
    }
}
