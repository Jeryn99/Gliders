package net.venturecraft.gliders.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.common.item.ItemRegistry;

import java.util.function.Supplier;

public class CreativeTabRegistry {

    @ExpectPlatform
    public static CreativeModeTab create(MutableComponent id, Supplier<ItemStack> icon) {
        return null;
    }

    public static void addEntries(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        for (RegistrySupplier<Item> entry : ItemRegistry.ITEMS.getEntries()) {
            output.accept(entry.get());
        }
    }
}
