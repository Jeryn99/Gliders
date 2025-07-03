package net.venturecraft.gliders.registry.fabric;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.common.item.ItemRegistry;
import net.venturecraft.gliders.registry.CreativeTabRegistry;
import net.venturecraft.gliders.registry.RegistrySupplier;

import java.util.function.Supplier;

public class CreativeTabRegistryImpl {

    public static CreativeModeTab create(MutableComponent id, Supplier<ItemStack> icon) {
        return FabricItemGroup.builder().title(id).icon(icon).displayItems(CreativeTabRegistry::addEntries).build();
    }
}
