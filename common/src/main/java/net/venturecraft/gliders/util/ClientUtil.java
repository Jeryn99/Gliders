package net.venturecraft.gliders.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.venturecraft.gliders.VCGlidersClient;
import net.venturecraft.gliders.client.sound.MovingSound;

import java.util.function.Supplier;

public class ClientUtil {

    public static boolean shouldChangePerspective = true;
    public static CameraType backupPerspective = Minecraft.getInstance().options.getCameraType();


    public static void playPositionedSoundRecord(SoundEvent sound, float pitch, float volume) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, pitch, volume));
    }

    public static void updraftParticles(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (GliderUtil.isGlidingWithActiveGlider(Minecraft.getInstance().player) && state.is(VCGliderTags.UPDRAFT_BLOCKS)) {
            level.addAlwaysVisibleParticle(ParticleTypes.SNOWFLAKE, true, (double) pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble(), (double) pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), 0.0, 1, 0.0);
        }
    }

    public static void playGliderSound(Player player, ResourceLocation soundName, SoundSource category, boolean repeat, Supplier<Boolean> stopCondition, float volume, RandomSource randomSource) {
        Minecraft.getInstance().getSoundManager().play(new MovingSound(player, SoundEvent.createFixedRangeEvent(soundName, 1), category, repeat, stopCondition, volume, randomSource));
    }

    public static void createToast(MutableComponent title, MutableComponent subtitle) {
        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastIds.TUTORIAL_HINT, title, subtitle));
    }

    public static void povButton(ControlsScreen controlsScreen) {
        int i = controlsScreen.width / 2 - 155;
        int j = i + 160;
        int k = controlsScreen.height / 6 - 12 + (48);

        controlsScreen.addRenderableWidget(VCGlidersClient.autoPerspective.createButton(Minecraft.getInstance().options, j, k, 150));
    }

    @ExpectPlatform
    public static void addPredicate(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        throw new AssertionError();
    }

    public static void setPlayerPerspective(String pointOfView) {
        if (VCGlidersClient.autoPerspective.get() && !pointOfView.isEmpty()) {
            backupPerspective = Minecraft.getInstance().options.getCameraType();
            Minecraft.getInstance().options.setCameraType(CameraType.valueOf(pointOfView));
            return;
        }

        if (backupPerspective != null) {
            Minecraft.getInstance().options.setCameraType(backupPerspective);
            backupPerspective = null;
        }
    }
}
