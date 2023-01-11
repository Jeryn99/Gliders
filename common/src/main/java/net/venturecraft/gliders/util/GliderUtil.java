package net.venturecraft.gliders.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;

public class GliderUtil {
    public static boolean hasParagliderEquipped(LivingEntity livingEntity) {
        return livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ParagliderItem;
    }

    public static boolean isGliderActive(LivingEntity livingEntity) {
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        VenturePlayerData.get(livingEntity).ifPresent(iCap -> {
            ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
            if (stack.getItem() instanceof ParagliderItem) {
                atomicBoolean.set(ParagliderItem.glidingEnabled(stack) && iCap.getStamina() > 0);
            }
        });
        return atomicBoolean.get();
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

        return hasParagliderEquipped(livingEntity) && isGliderActive(livingEntity) && !livingEntity.isOnGround() && !livingEntity.isInWater();
    }
}
