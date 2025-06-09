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


    @NotNull
    private final Player player;
    public AnimationState glideAnimation = new AnimationState();
    public AnimationState fallingAnimation = new AnimationState();
    public AnimationState gliderOpeningAnimation = new AnimationState();
    private boolean isGliding = false;
    private int lightningTimer = 0;

    public GliderData(@NotNull Player player) {
        this.player = player;
    }

    @ExpectPlatform
    public static Optional<GliderData> get(LivingEntity player) {
        throw new AssertionError();
    }

    public void tick(LivingEntity livingEntity) {
        glideAndFallLogic(livingEntity);
        GliderUtil.onTickPlayerGlide(livingEntity.level(), livingEntity);

        if(!GliderUtil.isGlidingWithActiveGlider(livingEntity)){
            setLightningTimer(0);
        }

        if (livingEntity.level().isClientSide) return;
        setGliding(GliderUtil.isGlidingWithActiveGlider(livingEntity));

        if (livingEntity.tickCount % 40 == 0) {
            sync();
        }
    }

    private boolean isGliding() {
        return isGliding;
    }

    private void setGliding(boolean glidingWithActiveGlider) {
        isGliding = glidingWithActiveGlider;
    }

    private void glideAndFallLogic(LivingEntity livingEntity) {
        if (isGliding() || GliderUtil.isGlidingWithActiveGlider(livingEntity)) {
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

    public void sync() {
        if (this.player.level().isClientSide) {
            throw new IllegalStateException("Don't sync client -> server");
        }
        SyncGliderData syncGliderData = new SyncGliderData(this.player.getId(), serializeNBT());
        Network.getNetworkHandler().sendToClient(syncGliderData, (ServerPlayer) this.player);
    }

    public void syncTo(ServerPlayer receiver) {
        if (this.player.level().isClientSide) {
            throw new IllegalStateException("Don't sync client -> server");
        }
        Network.getNetworkHandler().sendToClient(new SyncGliderData(this.player.getId(), serializeNBT()), receiver);
    }

    public AnimationState getAnimation(AnimationStates animationStates) {
        return switch (animationStates) {
            case FALLING -> fallingAnimation;
            case GLIDING -> glideAnimation;
            case GLIDER_OPENING -> gliderOpeningAnimation;
        };
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putBoolean("is_gliding", isGliding);
        compoundTag.putInt("lightningTimer", lightningTimer);
        return compoundTag;
    }

    public int lightningTimer() {
        return lightningTimer;
    }

    public GliderData setLightningTimer(int lightningTimer) {
        this.lightningTimer = lightningTimer;
        return this;
    }

    public void deserializeNBT(CompoundTag nbt) {
        setGliding(nbt.getBoolean("is_gliding"));
        setLightningTimer(nbt.getInt("lightningTimer"));
    }

    public enum AnimationStates {
        FALLING, GLIDING, GLIDER_OPENING
    }

}