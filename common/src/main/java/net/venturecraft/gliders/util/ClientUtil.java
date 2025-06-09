package net.venturecraft.gliders.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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
        if (!level.isClientSide || random.nextInt(5) != 0) return;

        Player player = Minecraft.getInstance().player;
        if (player == null || !GliderUtil.isGlidingWithActiveGlider(player)) return;
        if (!state.is(VCGliderTags.UPDRAFT_BLOCKS)) return;
        if (player.distanceToSqr(Vec3.atCenterOf(pos)) > 64) return;

        level.addAlwaysVisibleParticle(
                ParticleTypes.SNOWFLAKE, true,
                pos.getX() + 0.5 + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
                pos.getY() + random.nextDouble() * 2,
                pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
                0.0, 1, 0.0
        );
    }

    public static void playGliderSound(Player player, ResourceLocation soundName, SoundSource category, boolean repeat, Supplier<Boolean> stopCondition, float volume, RandomSource randomSource) {
        Minecraft.getInstance().getSoundManager().play(new MovingSound(player, SoundEvent.createFixedRangeEvent(soundName, 1), category, repeat, stopCondition, volume, randomSource));
    }

    public static void createToast(MutableComponent title, MutableComponent subtitle) {
        Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastId.NARRATOR_TOGGLE, title, subtitle));
    }

    public static void povButton(ControlsScreen controlsScreen) {
        int i = controlsScreen.width / 2 - 155;
        int j = i + 160;
        int startingY = controlsScreen.height / 6 - 12 + 48;
        int k = startingY;

        for (int attempt = 0; attempt < 10; attempt++) {
            boolean overlaps = false;

            for (var widget : controlsScreen.renderables) {
                if (widget instanceof AbstractWidget existing) {
                    int existingX = existing.getX();
                    int existingY = existing.getY();
                    int existingW = existing.getWidth();
                    int existingH = existing.getHeight();

                    if (existingX == j &&
                            k < existingY + existingH &&
                            k + 20 > existingY) {
                        overlaps = true;
                        break;
                    }
                }
            }

            if (!overlaps) break;
            k += 24;
        }

        AbstractWidget customButton = VCGlidersClient.autoPerspective.createButton(Minecraft.getInstance().options, j, k, 150);
        controlsScreen.addRenderableWidget(customButton);

        for (var widget : controlsScreen.renderables) {
            if (widget instanceof AbstractWidget existing) {
                if (existing.getMessage().equals(CommonComponents.GUI_DONE)) {
                    int desiredY = k + 24;
                    if (existing.getY() < desiredY) {
                        existing.setY(desiredY);
                    }
                    break;
                }
            }
        }
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
