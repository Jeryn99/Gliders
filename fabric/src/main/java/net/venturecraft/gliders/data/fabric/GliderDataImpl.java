package net.venturecraft.gliders.data.fabric;


import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.data.GliderData;
import org.ladysnake.cca.api.v3.component.ComponentV3;

import java.util.Objects;
import java.util.Optional;

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