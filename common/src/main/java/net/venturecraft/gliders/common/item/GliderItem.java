package net.venturecraft.gliders.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.threetag.palladiumcore.item.IPalladiumItem;
import net.venturecraft.gliders.util.ModConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GliderItem extends Item implements IPalladiumItem {

    private final Supplier<ItemStack> repair;

    public GliderItem(Properties itemProperties, Supplier<ItemStack> stackSupplier) {
        super(itemProperties);
        this.repair = stackSupplier;
    }

    public static boolean isSpaceGlider(ItemStack stack) {
        return stack.getDisplayName().getString().contains("xwing");
    }

    public static ItemStack setCopper(ItemStack itemStack, boolean copper) {
        CompoundTag compound = itemStack.getOrCreateTag();
        compound.putBoolean("copper_upgrade", copper);
        return itemStack;
    }

    public static boolean hasCopperUpgrade(ItemStack itemStack) {
        CompoundTag compound = itemStack.getOrCreateTag();
        if (!compound.contains("copper_upgrade")) return false;
        return compound.getBoolean("copper_upgrade");
    }

    public static ItemStack setNether(ItemStack itemStack, boolean copper) {
        CompoundTag compound = itemStack.getOrCreateTag();
        compound.putBoolean("nether_upgrade", copper);
        return itemStack;
    }

    public static boolean hasNetherUpgrade(ItemStack itemStack) {
        CompoundTag compound = itemStack.getOrCreateTag();
        if (!compound.contains("nether_upgrade")) return false;
        return compound.getBoolean("nether_upgrade");
    }

    public static boolean isGlidingEnabled(ItemStack itemStack) {
        CompoundTag compound = itemStack.getOrCreateTag();
        if (!compound.contains("glide")) return false;
        return compound.getBoolean("glide") && !isBroken(itemStack);
    }

    public static boolean isTooBroken(ItemStack itemStack) {
        return !(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1);
    }

    public static void setGlide(ItemStack itemStack, boolean canGlide) {
        CompoundTag compound = itemStack.getOrCreateTag();
        compound.putBoolean("glide", canGlide);
    }

    public static void setBroken(ItemStack itemStack, boolean broken) {
        CompoundTag compound = itemStack.getOrCreateTag();
        compound.putBoolean("broken", broken);
    }

    public static boolean isBroken(ItemStack itemStack) {
        CompoundTag compound = itemStack.getOrCreateTag();
        if (!compound.contains("broken")) return false;
        return compound.getBoolean("broken");
    }

    public static void setStruck(ItemStack itemStack, boolean isStruck) {
        CompoundTag compound = itemStack.getOrCreateTag();
        compound.putBoolean("struck", isStruck);
    }

    public static boolean hasBeenStruck(ItemStack itemStack) {
        CompoundTag compound = itemStack.getOrCreateTag();
        if (!compound.contains("struck")) return false;
        return compound.getBoolean("struck");
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.getItem() == this.repair.get().getItem();
    }

    @Override
    public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
        ItemStack equipmentSlotStack = player.getItemBySlot(equipmentslot);
        if (equipmentSlotStack.isEmpty()) {
            player.setItemSlot(equipmentslot, itemstack.copy());
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            itemstack.setCount(0);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }


}