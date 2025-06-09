package net.venturecraft.gliders.data;

import commonnetwork.api.Network;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.network.SyncGliderData;
import net.venturecraft.gliders.util.GliderUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GliderData {

    public static AnimationState glideAnimation = new AnimationState();
    public static AnimationState fallingAnimation = new AnimationState();
    public static AnimationState gliderOpeningAnimation = new AnimationState();

    @ExpectPlatform
    public static boolean getIsGliding(LivingEntity player) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int getLightningTimer(LivingEntity player) {
        throw new AssertionError();
    }

    public static void tick(LivingEntity livingEntity) {
        glideAndFallLogic(livingEntity);
        GliderUtil.onTickPlayerGlide(livingEntity.level(), livingEntity);

        if(!GliderUtil.isGlidingWithActiveGlider(livingEntity)){
            setLightningTimer(livingEntity, 0);
        }

        if (livingEntity.level().isClientSide) return;
        setIsGliding(livingEntity, GliderUtil.isGlidingWithActiveGlider(livingEntity));

        if (livingEntity.tickCount % 40 == 0) {
            sync(livingEntity);
        }
    }

    private static void glideAndFallLogic(LivingEntity livingEntity) {
        if (getIsGliding(livingEntity) || GliderUtil.isGlidingWithActiveGlider(livingEntity)) {
            if (!glideAnimation.isStarted()) {
                glideAnimation.start(livingEntity.tickCount);
            }

            if (!gliderOpeningAnimation.isStarted()) {
                gliderOpeningAnimation.start(livingEntity.tickCount);
            }
        } else {
            glideAnimation.stop();
            gliderOpeningAnimation.stop();
        }
    }

    public static void sync(LivingEntity livingEntity) {
        if (livingEntity.level().isClientSide) {
            throw new IllegalStateException("Don't sync client -> server");
        }
        SyncGliderData syncGliderData = new SyncGliderData(livingEntity.getId(), serializeNBT());
        Network.getNetworkHandler().sendToClient(syncGliderData, (ServerPlayer) livingEntity);
    }

    public static void syncTo(ServerPlayer receiver) {
        if (receiver.level().isClientSide) {
            throw new IllegalStateException("Don't sync client -> server");
        }
        Network.getNetworkHandler().sendToClient(new SyncGliderData(receiver.getId(), serializeNBT()), receiver);
    }

    public static AnimationState getAnimation(AnimationStates animationStates) {
        return switch (animationStates) {
            case FALLING -> fallingAnimation;
            case GLIDING -> glideAnimation;
            case GLIDER_OPENING -> gliderOpeningAnimation;
        };
    }

    public static CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        return compoundTag;
    }

    @ExpectPlatform
    public static void setLightningTimer(LivingEntity player, int lightningTimer) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static void setIsGliding(LivingEntity player, boolean isGliding) {
        throw new AssertionError();
    }

    public static void deserializeNBT(Player player, CompoundTag nbt) {
        setIsGliding(player, nbt.getBoolean("is_gliding"));
        setLightningTimer(player, nbt.getInt("lightningTimer"));
    }

    public enum AnimationStates {
        FALLING, GLIDING, GLIDER_OPENING
    }

}