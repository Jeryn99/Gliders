package net.venturecraft.gliders.neoforge;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.item.ItemRegistry;

@EventBusSubscriber(modid = VCGliders.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class GliderEventsNeoForge {
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
                var data = left.copy();
                GliderItem.setCopper(data, true);
                event.setOutput(data);
            }

            if (right.getItem() == ItemRegistry.NETHER_UPGRADE.get()) {
                event.setCost(10);
                var data = left.copy();
                GliderItem.setNether(data, true);
                event.setOutput(data);
            }
        }
    }
}
