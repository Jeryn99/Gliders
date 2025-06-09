package net.venturecraft.gliders.data.neoforge;


import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.data.GliderData;

import java.util.Objects;
import java.util.Optional;

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