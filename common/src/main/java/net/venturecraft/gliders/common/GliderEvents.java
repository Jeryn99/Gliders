package net.venturecraft.gliders.common;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.threetag.palladiumcore.event.EntityEvents;
import net.threetag.palladiumcore.event.EventResult;
import net.threetag.palladiumcore.event.LivingEntityEvents;
import net.threetag.palladiumcore.event.PlayerEvents;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.util.GliderUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class GliderEvents implements LivingEntityEvents.Attack, PlayerEvents.Tracking, LivingEntityEvents.Tick, EntityEvents.LightningStrike, LivingEntityEvents.Hurt, PlayerEvents.AnvilUpdate {

    public static void initEvents() {
        GliderEvents instance = new GliderEvents();
        EntityEvents.LIGHTNING_STRIKE.register(instance);
        LivingEntityEvents.HURT.register(instance);
       /* LivingEntityEvents.ITEM_USE_START.register(instance);
        LivingEntityEvents.ITEM_USE_TICK.register(instance);
        LivingEntityEvents.ITEM_USE_STOP.register(instance);*/
        LivingEntityEvents.HURT.register(instance);
        PlayerEvents.ANVIL_UPDATE.register(instance);
        PlayerEvents.START_TRACKING.register(instance);
        LivingEntityEvents.TICK.register(instance);
    }

    @Override
    public void lightningStrike(List<Entity> entities, LightningBolt lightningBolt) {
        for (Entity entity : entities) {
            if (entity instanceof ServerPlayer player) {
                ItemStack chestItem = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(player);

                boolean hasCopperMod = GliderItem.hasCopperUpgrade(chestItem);
                boolean isGliding = GliderUtil.isGlidingWithActiveGlider(player);

                if (!hasCopperMod && isGliding) {
                    GliderItem.setBroken(chestItem, true);
                    return;
                }

                if (hasCopperMod && isGliding) {
                    GliderItem.setStruck(chestItem, true);
                    if (GliderItem.hasBeenStruck(chestItem)) {
                        player.hurt(GliderDamageSource.BAD_LIGHTNING_EXPERIMENT, 2F);
                    }
                }

            }
        }
    }

/*
    @Override
    public EventResult livingEntityItemUse(LivingEntity entity, @NotNull ItemStack stack, AtomicInteger duration) {
        return GliderUtil.isGlidingWithActiveGlider(entity) ? EventResult.cancel() : EventResult.pass();
    }
*/


    @Override
    public EventResult anvilUpdate(Player player, ItemStack left, ItemStack right, String name, AtomicInteger cost, AtomicInteger materialCost, AtomicReference<ItemStack> output) {

        if (left.getItem() instanceof GliderItem gliderItem && gliderItem.isValidRepairItem(left, right)) {
            ItemStack data = left.copy();
            GliderItem.setBroken(data, false);
            data.setDamageValue(0);
            cost.set(5);
            output.set(data);
        }

        return EventResult.pass();
    }

    @Override
    public void livingEntityTick(LivingEntity entity) {
        if (entity instanceof Player player) {
            GliderData.get(player).ifPresent(data -> data.tick(player));
        }
    }

    @Override
    public void playerTracking(Player tracker, Entity trackedEntity) {
        // Don't sync to all, just sync to the tracker
        if (trackedEntity instanceof Player trackedPlayer && tracker instanceof ServerPlayer trackerPlayer) {
            GliderData.get(trackedPlayer).ifPresent(data -> {
                data.syncTo(trackerPlayer);
            });
        }
    }

    @Override
    public EventResult livingEntityHurt(LivingEntity entity, DamageSource damageSource, AtomicReference<Float> amount) {
        if (entity instanceof Player player) {
            ItemStack chestItem = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(player);
            boolean hasCopperMod = GliderItem.hasCopperUpgrade(chestItem);
            boolean isGliding = GliderUtil.isGlidingWithActiveGlider(player);
            boolean isLightning = damageSource == DamageSource.LIGHTNING_BOLT;
            if (hasCopperMod && isGliding && isLightning) {
                return EventResult.cancel();
            }
        }
        return EventResult.pass();
    }

    @Override
    public EventResult livingEntityAttack(LivingEntity entity, DamageSource damageSource, float amount) {
        if(damageSource.getDirectEntity() instanceof LivingEntity livingEntity) {
            return GliderUtil.isGlidingWithActiveGlider(livingEntity) ? EventResult.cancel() : EventResult.pass();
        }
        return EventResult.pass();
    }

}
