package net.venturecraft.gliders.client.animation;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.venturecraft.gliders.util.GliderUtil;

import java.util.HashMap;
import java.util.UUID;

public class PlayerAnimData {

    public static final HashMap<UUID, PlayerAnimData> ANIM_DATA = new HashMap<>();



    public static void clear(){
        ANIM_DATA.clear();
    }

    public static PlayerAnimData getOrAdd(LivingEntity livingEntity){
        UUID uuid = livingEntity.getUUID();
        PlayerAnimData animData = new PlayerAnimData();
        if(!ANIM_DATA.containsKey(uuid)){
            ANIM_DATA.put(uuid, animData);
        } else {
            return ANIM_DATA.get(livingEntity.getUUID());
        }
        return animData;
    }

    private final AnimationState GLIDER_OPEN = new AnimationState();
    private final AnimationState GLIDER_CLOSE = new AnimationState();
    private final AnimationState PLAYER_GLIDING = new AnimationState();

    public void tick(LivingEntity livingEntity){
        PlayerAnimData data = ANIM_DATA.get(livingEntity.getUUID());
        AnimationState gliderOpen = gliderOpen();
        AnimationState gliderClose = gliderClose();
        AnimationState playerGlides = playerGliding();

        if(GliderUtil.isGlidingWithActiveGlider(livingEntity)){
            playGlidingAnimations(livingEntity, gliderOpen, playerGlides);
            return;
        }

        stopAnimation(gliderOpen, playerGlides);
    }

    private void playGlidingAnimations(LivingEntity livingEntity, AnimationState gliderOpen, AnimationState playerGlides) {
        if(!gliderOpen.isStarted()){
            gliderOpen.start(livingEntity.tickCount);
        }

        if(!playerGlides.isStarted()){
            playerGlides.start(livingEntity.tickCount);
        }

        stopAnimation(gliderClose());
    }

    private void stopAnimation(AnimationState... animationStates){
        for (AnimationState animationState : animationStates) {
            animationState.stop();
        }
    }

    public AnimationState gliderOpen() {
        return GLIDER_OPEN;
    }

    public AnimationState gliderClose() {
        return GLIDER_CLOSE;
    }

    public AnimationState playerGliding() {
        return PLAYER_GLIDING;
    }
}
