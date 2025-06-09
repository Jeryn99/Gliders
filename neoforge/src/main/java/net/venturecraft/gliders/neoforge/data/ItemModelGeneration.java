package net.venturecraft.gliders.neoforge.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.threetag.palladiumcore.registry.RegistryHolder;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.item.ItemRegistry;

public class ItemModelGeneration extends ItemModelProvider {

    public ItemModelGeneration(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), VCGliders.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for (RegistryHolder<Item, ? extends Item> entry : ItemRegistry.ITEMS.getEntries()) {
            if (entry.get() instanceof GliderItem) {
                ResourceLocation gliderId = BuiltInRegistries.ITEM.getKey(entry.get());
                layeredItem(ResourceLocation.fromNamespaceAndPath(gliderId.getNamespace(), gliderId.getPath() + "_copper_upgrade"), gliderId, VCGliders.id("copper_upgrade"));
                layeredItem(ResourceLocation.fromNamespaceAndPath(gliderId.getNamespace(), gliderId.getPath() + "_nether_upgrade"), gliderId, VCGliders.id("nether_upgrade"));
                layeredItem(ResourceLocation.fromNamespaceAndPath(gliderId.getNamespace(), gliderId.getPath() + "_combined_upgrades"), gliderId, VCGliders.id("combined_upgrades"));
                continue;
            }

            basicItem(entry.get());
        }

        basicItem(VCGliders.id("damaged_glider"));
    }

    public ItemModelBuilder layeredItem(ResourceLocation destination, ResourceLocation item, ResourceLocation resourceLocation) {
        return getBuilder(destination.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath()));
    }

    public ItemModelBuilder basicItem(ResourceLocation destination) {
        return getBuilder(destination.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(destination.getNamespace(), "item/" + destination.getPath()));
    }

    public ItemModelBuilder layeredItem(ResourceLocation item, ResourceLocation resourceLocation) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + item.getPath()))
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath()));
    }
}
