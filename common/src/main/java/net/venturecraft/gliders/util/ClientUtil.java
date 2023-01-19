package net.venturecraft.gliders.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.venturecraft.gliders.client.sound.MovingSound;

import java.util.function.Supplier;

public class ClientUtil {

    public static void playPositionedSoundRecord(SoundEvent sound, float pitch, float volume) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, pitch, volume));
    }

    public static void playGliderSound(Player player, ResourceLocation soundName, SoundSource category, boolean repeat, Supplier<Boolean> stopCondition, float volume, RandomSource randomSource) {
        Minecraft.getInstance().getSoundManager().play(new MovingSound(player, new SoundEvent(soundName), category, repeat, stopCondition, volume, randomSource));
    }

    public static void createToast(MutableComponent title, MutableComponent subtitle) {
        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT, title, subtitle));
    }


    @ExpectPlatform
    public static void addPredicate(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        throw new AssertionError();
    }

}
