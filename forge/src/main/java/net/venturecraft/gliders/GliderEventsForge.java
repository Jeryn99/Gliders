package net.venturecraft.gliders;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.venturecraft.gliders.common.GliderDamageSource;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.item.ItemRegistry;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.util.GliderUtil;

@Mod.EventBusSubscriber(modid = VCGliders.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GliderEventsForge {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            ItemStack chestItem = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(player);
            if (GliderItem.hasCopperUpgrade(chestItem) &&
                    GliderUtil.isGlidingWithActiveGlider(player) &&
                    event.getSource().is(DamageTypes.LIGHTNING_BOLT)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker &&
                GliderUtil.isGlidingWithActiveGlider(attacker)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onUseItem(PlayerInteractEvent.RightClickItem event) {
        if (GliderUtil.isGlidingWithActiveGlider(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityStruckByLightning(EntityStruckByLightningEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ServerPlayer player) {
            ItemStack chestItem = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(player);
            if (GliderUtil.isGlidingWithActiveGlider(player)) {
                if (!GliderItem.hasCopperUpgrade(chestItem)) {
                    GliderItem.setBroken(chestItem, true);
                } else {
                    GliderItem.setStruck(chestItem, true);
                    if (GliderItem.hasBeenStruck(chestItem)) {
                        player.hurt(GliderDamageSource.getSource((ServerLevel) player.level(), GliderDamageSource.ZAP_EXPERIMENT), 2.0F);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.getItem() instanceof GliderItem gliderItem) {
            if (gliderItem.isValidRepairItem(left, right)) {
                ItemStack result = left.copy();
                GliderItem.setBroken(result, false);
                result.setDamageValue(0);
                event.setCost(5);
                event.setOutput(result);
            }

            if (right.getItem() == ItemRegistry.COPPER_UPGRADE.get()) {
                event.setCost(10);
                event.setOutput(GliderEvents.makeResult(left.copy(), "copper"));
            }

            if (right.getItem() == ItemRegistry.NETHER_UPGRADE.get()) {
                event.setCost(10);
                event.setOutput(GliderEvents.makeResult(left.copy(), "nether"));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTracking(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player trackedPlayer &&
                event.getEntity() instanceof ServerPlayer trackerPlayer) {
            GliderData.get(trackedPlayer).ifPresent(data -> data.syncTo(trackerPlayer));
        }
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            GliderData.get(player).ifPresent(data -> data.tick(player));
        }
    }
}
