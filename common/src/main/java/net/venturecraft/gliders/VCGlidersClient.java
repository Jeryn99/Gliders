package net.venturecraft.gliders;

import net.minecraft.client.OptionInstance;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.threetag.palladiumcore.event.LifecycleEvents;
import net.threetag.palladiumcore.registry.CreativeModeTabRegistry;
import net.threetag.palladiumcore.registry.RegistryHolder;
import net.threetag.palladiumcore.registry.client.EntityRendererRegistry;
import net.venturecraft.gliders.client.layer.PlayerGliderLayer;
import net.venturecraft.gliders.client.model.ModelRegistry;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.item.ItemRegistry;
import net.venturecraft.gliders.common.item.VCTabs;
import net.venturecraft.gliders.util.ClientUtil;

public class VCGlidersClient {

    public static int lightLevel = 0;
    public static OptionInstance<Boolean> autoPerspective;


    public static void init() {
        autoPerspective = OptionInstance.createBoolean("options.glider_perspective", false);
        ModelRegistry.init();
        EntityRendererRegistry.addRenderLayerToPlayer(renderLayerParent -> new PlayerGliderLayer(renderLayerParent));

        CreativeModeTabRegistry.addToTab((Holder<CreativeModeTab>) VCTabs.MAIN, entries -> {

            for (RegistryHolder<Item, ? extends Item> entry : ItemRegistry.ITEMS.getEntries()) {
                entries.add(entry.get());
            }
        });

    /*    ClientTickEvents.CLIENT_POST.register(new ClientTickEvents.ClientTick() {
            @Override
            public void clientTick(Minecraft minecraft) {
                LocalPlayer player = minecraft.player;
                if ((GliderKeybinds.ACTIVATE_GLIDER.consumeClick() || Minecraft.getInstance().options.keyJump.consumeClick())) {
                    if (GliderUtil.hasGliderEquipped(player) *//*&& GliderUtil.canDeployHere(player)*//*) {
                        new MessageToggleGlide().send();
                    }
                }
            }
        });*/

        LifecycleEvents.CLIENT_SETUP.register(() -> {
            // Item Predicates
            for (RegistryHolder<Item, ? extends Item> supplier : ItemRegistry.ITEMS) {
                if (supplier.get() instanceof GliderItem paragliderItem) {
                    ClientUtil.addPredicate(paragliderItem, ResourceLocation.withDefaultNamespace("upgrade_level"), (itemStack, clientLevel, livingEntity, i) -> {

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
