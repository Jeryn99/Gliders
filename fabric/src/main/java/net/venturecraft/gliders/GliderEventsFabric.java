package net.venturecraft.gliders;

import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.util.GliderUtil;

public class GliderEventsFabric {

    public static void init() {

        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (GliderUtil.isGlidingWithActiveGlider(player)) {
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (GliderUtil.isGlidingWithActiveGlider(player)) {
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                GliderData.syncTo(player);
            }
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if(player instanceof ServerPlayer serverPlayer) {
                GliderData.syncTo(serverPlayer);
            }
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (GliderUtil.isGlidingWithActiveGlider(player)) {
                return InteractionResult.FAIL;
            }
            return InteractionResult.PASS;
        });


    }
}
