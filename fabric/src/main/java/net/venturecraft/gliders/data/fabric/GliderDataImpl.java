package net.venturecraft.gliders.data.fabric;


import net.minecraft.world.entity.LivingEntity;

public class GliderDataImpl {

    public static boolean getIsGliding(LivingEntity player) {
        return player.getAttachedOrSet(VCAttachments.IS_GLIDING, false);
    }

    public static void setIsGliding(LivingEntity player, boolean isGliding) {
        player.setAttached(VCAttachments.IS_GLIDING, isGliding);
    }

    public static int getLightningTimer(LivingEntity player) {
        return player.getAttachedOrSet(VCAttachments.LIGHTNING_TIMER, 0);
    }

    public static void setLightningTimer(LivingEntity player, int lightningTimer) {
        player.setAttached(VCAttachments.LIGHTNING_TIMER, lightningTimer);
    }

}