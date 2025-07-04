package net.venturecraft.gliders.common.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.venturecraft.gliders.util.ModConstants;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GliderItem extends Item implements Equipable {

    private final Supplier<ItemStack> repair;

    public GliderItem(Properties itemProperties, Supplier<ItemStack> stackSupplier) {
        super(itemProperties);
        this.repair = stackSupplier;
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static boolean isSpaceGlider(ItemStack stack) {
        return stack.getDisplayName().getString().contains("xwing");
    }

    public static ItemStack setCopper(ItemStack itemStack, boolean copper) {
        itemStack.set(ItemComponentRegistry.COPPER_UPGRADE.get(), copper);
        return itemStack;
    }

    public static boolean hasCopperUpgrade(ItemStack itemStack) {
        DataComponentMap compound = itemStack.getComponents();
        if (!compound.has(ItemComponentRegistry.COPPER_UPGRADE.get())) return false;
        return compound.get(ItemComponentRegistry.COPPER_UPGRADE.get());
    }

    public static ItemStack setNether(ItemStack itemStack, boolean copper) {
        itemStack.set(ItemComponentRegistry.NETHER_UPGRADE.get(), copper);
        return itemStack;
    }

    public static boolean hasNetherUpgrade(ItemStack itemStack) {
        DataComponentMap compound = itemStack.getComponents();
        if (!compound.has(ItemComponentRegistry.NETHER_UPGRADE.get())) return false;
        return compound.get(ItemComponentRegistry.NETHER_UPGRADE.get());
    }

    public static boolean isGlidingEnabled(ItemStack itemStack) {
        DataComponentMap compound = itemStack.getComponents();
        if (!compound.has(ItemComponentRegistry.GLIDE.get())) return false;
        return compound.get(ItemComponentRegistry.GLIDE.get()) && !isBroken(itemStack);
    }

    public static boolean isTooBroken(ItemStack itemStack) {
        return !(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1);
    }

    public static void setGlide(ItemStack itemStack, boolean canGlide) {
        itemStack.set(ItemComponentRegistry.GLIDE.get(), canGlide);
    }

    public static void setBroken(ItemStack itemStack, boolean broken) {
        itemStack.set(ItemComponentRegistry.BROKEN.get(), broken);
    }

    public static boolean isBroken(ItemStack itemStack) {
        DataComponentMap compound = itemStack.getComponents();
        if (!compound.has(ItemComponentRegistry.BROKEN.get())) return false;
        return compound.get(ItemComponentRegistry.BROKEN.get());
    }

    public static void setStruck(ItemStack itemStack, boolean isStruck) {
        itemStack.set(ItemComponentRegistry.STRUCK.get(), isStruck);
    }

    public static boolean hasBeenStruck(ItemStack itemStack) {
        DataComponentMap compound = itemStack.getComponents();
        if (!compound.has(ItemComponentRegistry.STRUCK.get())) return false;
        return compound.get(ItemComponentRegistry.STRUCK.get());
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.getItem() == this.repair.get().getItem();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltip, tooltipFlag);

        if (hasCopperUpgrade(stack) || hasNetherUpgrade(stack)) {
            tooltip.add(Component.translatable(ModConstants.INSTALLED_UPGRADES));
            if (hasCopperUpgrade(stack)) {
                tooltip.add(Component.literal("- ").append(Component.translatable(ModConstants.COPPER_UPGRADE)));
            }

            if (hasNetherUpgrade(stack)) {
                tooltip.add(Component.literal("- ").append(Component.translatable(ModConstants.NETHER_UPGRADE)));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return this.swapWithEquipmentSlot(this, level, player, usedHand);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public Holder<SoundEvent> getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_ELYTRA;
    }
}