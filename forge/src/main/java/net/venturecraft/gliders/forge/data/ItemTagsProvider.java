package net.venturecraft.gliders.forge.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.util.VCGliderTags;
import org.jetbrains.annotations.Nullable;

public class ItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {

    public ItemTagsProvider(DataGenerator arg, BlockTagsProvider arg2, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, arg2, VCGliders.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        for (Item itemsValue : ForgeRegistries.ITEMS.getValues()) {
            if (itemsValue instanceof GliderItem) {
                add(VCGliderTags.TRINKETS_BACK, itemsValue);
                add(VCGliderTags.TRINKETS_CAPE, itemsValue);
                add(VCGliderTags.CURIOS_CHEST, itemsValue);
            }
        }

    }

    public void add(TagKey<Item> branch, Item item) {
        this.tag(branch).add(item);
    }

    public void add(TagKey<Item> branch, Item... item) {
        this.tag(branch).add(item);
    }
}
