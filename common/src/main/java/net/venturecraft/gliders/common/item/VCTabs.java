package net.venturecraft.gliders.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.threetag.palladiumcore.registry.CreativeModeTabRegistry;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistryHolder;
import net.venturecraft.gliders.VCGliders;

public class VCTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(VCGliders.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistryHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("technology",
            () -> CreativeModeTabRegistry.create(Component.translatable("itemGroup.vc_gliders.main"),
                    () -> new ItemStack(ItemRegistry.PARAGLIDER_WOOD.get())));

}
