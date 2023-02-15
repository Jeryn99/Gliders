package net.venturecraft.gliders;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.registry.RegistrySupplier;
import net.threetag.palladiumcore.registry.client.EntityRendererRegistry;
import net.venturecraft.gliders.client.layer.PlayerGliderLayer;
import net.venturecraft.gliders.client.model.ModelRegistry;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.item.ItemRegistry;
import net.venturecraft.gliders.util.ClientUtil;

public class VCGlidersClient {

    public static int lightLevel = 0;

    public static void init() {
        ModelRegistry.init();
        EntityRendererRegistry.addRenderLayerToPlayer(renderLayerParent -> new PlayerGliderLayer(renderLayerParent));

        LifecycleEvents.CLIENT_SETUP.register(() -> {
            // Item Predicates
            for (RegistrySupplier<Item> supplier : ItemRegistry.ITEMS) {
                if (supplier.get() instanceof GliderItem paragliderItem) {
                    ClientUtil.addPredicate(paragliderItem, new ResourceLocation("upgrade_level"), (itemStack, clientLevel, livingEntity, i) -> {

                        if (GliderItem.isBroken(itemStack)) {
                            return 0.4F;
                        }

                        if (GliderItem.hasCopperUpgrade(itemStack) && GliderItem.hasNetherUpgrade(itemStack)) {
                            return 0.1F;
                        }

                        if (GliderItem.hasCopperUpgrade(itemStack)) {
                            return 0.2F;
                        }

                        if (GliderItem.hasNetherUpgrade(itemStack)) {
                            return 0.3F;
                        }

                        return 0;
                    });
                }
            }
        });

    }

}
