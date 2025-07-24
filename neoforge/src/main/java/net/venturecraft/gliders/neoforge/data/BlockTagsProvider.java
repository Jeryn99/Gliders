package net.venturecraft.gliders.neoforge.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.util.VCGliderTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagsProvider extends net.neoforged.neoforge.common.data.BlockTagsProvider {


    public BlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VCGliders.MOD_ID, existingFileHelper);
    }

    public void add(TagKey<Block> branch, Block block) {
        this.tag(branch).add(block);
    }

    public void add(TagKey<Block> branch, Block... blocks) {
        this.tag(branch).add(blocks);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        add(VCGliderTags.UPDRAFT_BLOCKS, BuiltInRegistries.BLOCK.stream().filter(block -> block instanceof BaseFireBlock || block instanceof CampfireBlock || block == Blocks.MAGMA_BLOCK).toList().toArray(new Block[0]));
    }
}
