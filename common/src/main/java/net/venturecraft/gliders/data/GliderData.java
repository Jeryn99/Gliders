package net.venturecraft.gliders.data;

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
    private boolean isGliding = false;

    public enum AnimationStates {
        FALLING, GLIDING, GLIDER_OPENING
    }

    public AnimationState glideAnimation = new AnimationState();
    public AnimationState fallingAnimation = new AnimationState();
    public AnimationState gliderOpeningAnimation = new AnimationState();

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

        if(livingEntity.level().isClientSide) return;
        setGliding(GliderUtil.isGlidingWithActiveGlider(livingEntity));

        if(livingEntity.tickCount % 40 == 0){
            sync();
        }
    }

    private void setGliding(boolean glidingWithActiveGlider) {
        isGliding = glidingWithActiveGlider;
    }

    private boolean isGliding() {
        return isGliding;
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
        new SyncGliderData(this.player.getId(), serializeNBT()).sendToTracking(this.player);
    }

    public void syncTo(ServerPlayer receiver) {
        if (this.player.level().isClientSide) {
            throw new IllegalStateException("Don't sync client -> server");
        }
        new SyncGliderData(this.player.getId(), serializeNBT()).send(receiver);
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
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag nbt) {
        setGliding(nbt.getBoolean("is_gliding"));
    }

}