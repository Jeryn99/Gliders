package net.venturecraft.gliders.forge.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.util.VCGliderTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {

    public ItemTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Block>> completableFuture2, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, completableFuture2, VCGliders.MOD_ID, existingFileHelper);
    }

    public ItemTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, CompletableFuture<TagLookup<Item>> completableFuture2, CompletableFuture<TagLookup<Block>> completableFuture3, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, completableFuture2, completableFuture3, VCGliders.MOD_ID, existingFileHelper);
    }

    public void add(TagKey<Item> branch, Item item) {
        this.tag(branch).add(item);
    }

    public void add(TagKey<Item> branch, Item... item) {
        this.tag(branch).add(item);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        for (Item itemsValue : ForgeRegistries.ITEMS.getValues()) {
            if (itemsValue instanceof GliderItem) {
                add(VCGliderTags.TRINKETS_BACK, itemsValue);
                add(VCGliderTags.CURIOS_CHEST, itemsValue);
            }
        }
    }
}
