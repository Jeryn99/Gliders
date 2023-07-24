package net.venturecraft.gliders.forge.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.threetag.palladiumcore.registry.RegistrySupplier;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.item.ItemRegistry;

public class ItemModelGeneration extends ItemModelProvider {

    public ItemModelGeneration(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), VCGliders.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        for (RegistrySupplier<Item> entry : ItemRegistry.ITEMS.getEntries()) {
            if (entry.get() instanceof GliderItem) {
                ResourceLocation gliderId = ForgeRegistries.ITEMS.getKey(entry.get());
                layeredItem(new ResourceLocation(gliderId.getNamespace(), gliderId.getPath() + "_copper_upgrade"), gliderId, new ResourceLocation(VCGliders.MOD_ID, "copper_upgrade"));
                layeredItem(new ResourceLocation(gliderId.getNamespace(), gliderId.getPath() + "_nether_upgrade"), gliderId, new ResourceLocation(VCGliders.MOD_ID, "nether_upgrade"));
                layeredItem(new ResourceLocation(gliderId.getNamespace(), gliderId.getPath() + "_combined_upgrades"), gliderId, new ResourceLocation(VCGliders.MOD_ID, "combined_upgrades"));
                continue;
            }

            basicItem(entry.get());
        }

        basicItem(new ResourceLocation(VCGliders.MOD_ID, "damaged_glider"));
    }

    public ItemModelBuilder layeredItem(ResourceLocation destination, ResourceLocation item, ResourceLocation resourceLocation) {
        return getBuilder(destination.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()))
                .texture("layer1", new ResourceLocation(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath()));
    }

    public ItemModelBuilder basicItem(ResourceLocation destination) {
        return getBuilder(destination.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(destination.getNamespace(), "item/" + destination.getPath()));
    }

    public ItemModelBuilder layeredItem(ResourceLocation item, ResourceLocation resourceLocation) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()))
                .texture("layer1", new ResourceLocation(resourceLocation.getNamespace(), "item/" + resourceLocation.getPath()));
    }
}
