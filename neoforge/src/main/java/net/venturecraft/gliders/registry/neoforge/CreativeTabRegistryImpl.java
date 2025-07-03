package net.venturecraft.gliders.registry.neoforge;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.registry.CreativeTabRegistry;

import java.util.function.Supplier;

public class CreativeTabRegistryImpl {

    public static CreativeModeTab create(MutableComponent id, Supplier<ItemStack> icon) {
        return CreativeModeTab.builder().title(id).icon(icon).displayItems(CreativeTabRegistry::addEntries).build();
    }
}
