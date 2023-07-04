package net.venturecraft.gliders.forge.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.util.VCGliderTags;
import org.jetbrains.annotations.Nullable;

public class BlockTagsProvider extends net.minecraft.data.tags.BlockTagsProvider {

    public BlockTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, VCGliders.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        add(VCGliderTags.UPDRAFT_BLOCKS, Registry.BLOCK.stream().filter(block -> block instanceof FireBlock || block instanceof CampfireBlock || block == Blocks.MAGMA_BLOCK).toList().toArray(new Block[0]));
    }

    public void add(TagKey<Block> branch, Block block) {
        this.tag(branch).add(block);
    }

    public void add(TagKey<Block> branch, Block... blocks) {
        this.tag(branch).add(blocks);
    }
}
