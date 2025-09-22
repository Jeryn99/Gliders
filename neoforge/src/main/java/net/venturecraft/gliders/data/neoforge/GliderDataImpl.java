package net.venturecraft.gliders.data.neoforge;


import net.minecraft.world.entity.LivingEntity;

public class GliderDataImpl {

    public static boolean getIsGliding(LivingEntity player) {
        return player.getData(VCAttachments.IS_GLIDING);
    }

    public static void setIsGliding(LivingEntity player, boolean isGliding) {
        player.setData(VCAttachments.IS_GLIDING, isGliding);
    }

    public static int getLightningTimer(LivingEntity player) {
        return player.getData(VCAttachments.LIGHTNING_TIMER);
    }

    public static void setLightningTimer(LivingEntity player, int lightningTimer) {
        player.setData(VCAttachments.LIGHTNING_TIMER, lightningTimer);
    }
}