package net.venturecraft.gliders.compat.trinket;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import dev.emi.trinkets.TrinketSlot;
import dev.emi.trinkets.api.*;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsSlotInv;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;


public class TrinketsUtil extends CuriosTrinketsUtil {

    @Override
    public boolean isTrinkets() {
        return true;
    }


    @Override
    public CuriosTrinketsSlotInv getSlot(LivingEntity entity, String slotKey) {
        final CuriosTrinketsSlotInv[] container = {CuriosTrinketsSlotInv.EMPTY};
        var slot = parseSlot(slotKey);

        TrinketsApi.getTrinketComponent(entity).ifPresent(comp -> {
            var group = comp.getInventory().get(slot.getFirst());

            if (group != null) {
                var trinketSlot = group.get(slot.getSecond());

                if (trinketSlot != null) {
                    container[0] = new SlotInv(trinketSlot);
                }
            }
        });

        return container[0];
    }

    public Pair<String, String> parseSlot(String slot) {
        var split = slot.split("/");

        if (split.length != 2) {
            return Pair.of("", "");
        } else {
            return Pair.of(split[0], split[1]);
        }
    }

    public static class SlotInv implements CuriosTrinketsSlotInv {

        private final TrinketInventory inventory;

        public SlotInv(TrinketInventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public int getSlots() {
            return this.inventory.getContainerSize();
        }

        @Override
        public ItemStack getStackInSlot(int index) {
            return this.inventory.getItem(index);
        }

        @Override
        public void setStackInSlot(int index, ItemStack stack) {
            this.inventory.setItem(index, stack);
        }
    }
}