package net.venturecraft.gliders.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;

public class GliderUtil {
    public static boolean hasGliderEquipped(LivingEntity livingEntity) {
        return CuriosTrinketsUtil.getInstance().getFirstGliderInSlot(livingEntity, CuriosTrinketsUtil.BACK.identifier()) != null;
    }

    public static boolean isGliderActive(LivingEntity livingEntity) {
        ItemStack glider = CuriosTrinketsUtil.getInstance().getFirstGliderInSlot(livingEntity, CuriosTrinketsUtil.BACK.identifier());
        if (glider == null) return false;
        if (glider.getItem() instanceof GliderItem) {
            return GliderItem.isGlidingEnabled(glider);
        }
        return false;
    }

    public static boolean isPlayerOnGroundOrWater(LivingEntity livingEntity) {
        return livingEntity.isOnGround() || livingEntity.isInWater() || livingEntity.isUnderWater() || livingEntity.isSwimming();
    }

    public static boolean isGlidingWithActiveGlider(LivingEntity livingEntity) {

        if (livingEntity instanceof Player player) {
            if (player.getAbilities().flying) {
                return false;
            }
        }

        return hasGliderEquipped(livingEntity) && isGliderActive(livingEntity) && !livingEntity.isOnGround() && !livingEntity.isInWater();
    }
}
