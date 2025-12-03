package net.venturecraft.gliders.common.item;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.venturecraft.gliders.util.ModConstants;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GliderItem extends Item {

    private final Supplier<ItemStack> repair;

    public GliderItem(Properties itemProperties, Supplier<ItemStack> stackSupplier) {
        super(itemProperties.equippable(EquipmentSlot.CHEST).repairable(stackSupplier.get().getItem()));
        this.repair = stackSupplier;
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
        return Boolean.TRUE.equals(compound.get(ItemComponentRegistry.COPPER_UPGRADE.get()));
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

    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.getItem() == this.repair.get().getItem();
    }

    @Override
    public void appendHoverText(
            ItemStack itemStack,
            TooltipContext tooltipContext,
            TooltipDisplay tooltipDisplay,
            Consumer<Component> consumer,
            TooltipFlag tooltipFlag
    ) {
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);

        if (hasCopperUpgrade(itemStack) || hasNetherUpgrade(itemStack)) {
            consumer.accept(Component.translatable(ModConstants.INSTALLED_UPGRADES));

            if (hasCopperUpgrade(itemStack)) {
                consumer.accept(
                        Component.literal("- ").append(Component.translatable(ModConstants.COPPER_UPGRADE))
                );
            }

            if (hasNetherUpgrade(itemStack)) {
                consumer.accept(
                        Component.literal("- ").append(Component.translatable(ModConstants.NETHER_UPGRADE))
                );
            }
        }
    }

}