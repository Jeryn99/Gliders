package net.venturecraft.gliders.common.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsSlotInv;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
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
        InteractionResultHolder<ItemStack> capeResult = equipInCape(level, player, usedHand);
        if (capeResult != null) {
            return capeResult;
        }
        return this.swapWithEquipmentSlot(this, level, player, usedHand);
    }

    @Nullable
    private InteractionResultHolder<ItemStack> equipInCape(Level level, Player player, InteractionHand hand) {
        if (!CuriosTrinketsUtil.getInstance().isCurios() && !CuriosTrinketsUtil.getInstance().isTrinkets()) {
            return null;
        }
        ItemStack held = player.getItemInHand(hand);
        CuriosTrinketsSlotInv cape = CuriosTrinketsUtil.getInstance().getSlot(player, CuriosTrinketsUtil.CAPE);
        if (cape.getSlots() == 0) {
            // The Accessories compat layer exposes the cape as "body" instead.
            cape = CuriosTrinketsUtil.getInstance().getSlot(player, "body");
        }
        if (cape.getSlots() == 0) {
            return null;
        }
        if (level.isClientSide()) {
            return InteractionResultHolder.sidedSuccess(held, true);
        }

        for (int i = 0; i < cape.getSlots(); i++) {
            ItemStack occupant = cape.getStackInSlot(i);
            if (!occupant.isEmpty() && EnchantmentHelper.has(occupant, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE) && !player.isCreative()) {
                continue;
            }
            if (!occupant.isEmpty() && ItemStack.matches(occupant, held)) {
                return InteractionResultHolder.fail(held);
            }
            cape.setStackInSlot(i, player.isCreative() ? held.copy() : held);
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_ELYTRA.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            // The swap returns the displaced item, the game mode puts it back in the hand.
            return InteractionResultHolder.sidedSuccess(occupant.isEmpty() && player.isCreative() ? held : occupant, false);
        }
        return null;
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