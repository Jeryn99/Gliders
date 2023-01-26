package net.venturecraft.gliders.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.common.item.GliderItem;

public class GliderUtil {
    public static boolean hasGliderEquipped(LivingEntity livingEntity) {
        return livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GliderItem;
    }

    public static boolean isGliderActive(LivingEntity livingEntity) {
            ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
            if (stack.getItem() instanceof GliderItem) {
                return GliderItem.isGlidingEnabled(stack);
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
