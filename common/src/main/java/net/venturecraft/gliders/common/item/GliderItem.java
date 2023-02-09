package net.venturecraft.gliders.common.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.phys.Vec3;
import net.threetag.palladiumcore.item.IPalladiumItem;
import net.threetag.palladiumcore.util.Platform;
import net.venturecraft.gliders.util.GliderUtil;
import net.venturecraft.gliders.util.ModConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GliderItem extends Item implements Wearable, IPalladiumItem {

    private Supplier<ItemStack> repair;

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
        return compound.getBoolean("glide") && !isTooBroken(itemStack);
    }

    public static boolean isTooBroken(ItemStack itemStack){
        return !(itemStack.getDamageValue() < itemStack.getMaxDamage() - 1);
    }

    public static void setGlide(ItemStack itemStack, boolean canGlide) {
        CompoundTag compound = itemStack.getOrCreateTag();
        compound.putBoolean("glide", canGlide);
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
    public void armorTick(ItemStack stack, Level level, Player player) {

        boolean playerCanGlide = !GliderUtil.isPlayerOnGroundOrWater(player) && !player.getAbilities().flying;
        boolean gliderCanGlide = isGlidingEnabled(stack);

        if (playerCanGlide && gliderCanGlide) {

            player.resetFallDistance();

            player.getAbilities().mayfly = true; // Stop Servers kicking survival players

            // Handle Movement
            Vec3 m = player.getDeltaMovement();
            boolean hasSpeedMods = hasCopperUpgrade(stack) && hasBeenStruck(stack);

            if (player.tickCount % 200 == 0 && !player.isCreative()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    player.getItemBySlot(EquipmentSlot.CHEST).hurt(player.level.dimension() == Level.NETHER && !hasNetherUpgrade(stack) ? getMaxDamage() / 2 : 1, player.getRandom(), serverPlayer);
                }
            }

            if (level.getBlockState(player.blockPosition().below(2)).getBlock() instanceof FireBlock) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, 2, 0));
                return;
            }

            // Particles
            float horizonalSpeed = (float) player.getDeltaMovement().horizontalDistance();
            if (isSpaceGlider(stack) && horizonalSpeed >= 0.01F) {

                if (!level.isClientSide()) {
                    for (int i = 0; i < 2; ++i) {
                        for (ServerPlayer serverplayer : Platform.getCurrentServer().getPlayerList().getPlayers()) {
                            ((ServerLevel) serverplayer.level).sendParticles(ParticleTypes.DRAGON_BREATH, player.getRandomX(0.5D), player.getY() + 2.5, player.getRandomZ(0.5D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }

            // Speed Modifications
            if (hasSpeedMods) {

                if (!level.isClientSide() && horizonalSpeed >= 0.01F) {
                    for (int i = 0; i < 2; ++i) {
                        for (ServerPlayer serverplayer : Platform.getCurrentServer().getPlayerList().getPlayers()) {
                            ((ServerLevel) serverplayer.level).sendParticles(ParticleTypes.GLOW, player.getRandomX(0.5D), player.getY() + 2.5, player.getRandomZ(0.5D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }

            if (m.y < -0.05)
                player.setDeltaMovement(new Vec3(m.x, -0.05, m.z));
            return;
        }

        player.getAbilities().mayfly = player.isCreative();

        if (GliderUtil.isPlayerOnGroundOrWater(player)) {

            // Reset Gliding status when on Ground
            setGlide(stack, false);
            setStruck(stack, false);
        }
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
            if(hasCopperUpgrade(stack)) {
                tooltip.add(Component.literal("- ").append(Component.translatable(ModConstants.COPPER_UPGRADE)));
            }

            if(hasNetherUpgrade(stack)) {
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