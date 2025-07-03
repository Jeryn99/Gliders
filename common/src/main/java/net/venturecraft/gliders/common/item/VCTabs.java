package net.venturecraft.gliders.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.registry.CreativeTabRegistry;
import net.venturecraft.gliders.registry.DeferredRegistry;
import net.venturecraft.gliders.registry.RegistrySupplier;

public class VCTabs {

    public static final DeferredRegistry<CreativeModeTab> TABS = DeferredRegistry.create(VCGliders.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MAIN = TABS.register("technology",
            () -> CreativeTabRegistry.create(Component.translatable("itemGroup.vc_gliders.main"),
                    () -> new ItemStack(ItemRegistry.PARAGLIDER_WOOD.get())));

}
