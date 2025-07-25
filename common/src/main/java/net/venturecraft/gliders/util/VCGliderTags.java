package net.venturecraft.gliders.util;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.venturecraft.gliders.VCGliders;

public class VCGliderTags {

    public static TagKey<Item> GLIDERS = makeItem(VCGliders.MOD_ID, "gliders");
    public static TagKey<Item> TRINKETS_BACK = makeItem("trinkets", "chest/back");
    public static TagKey<Item> TRINKETS_CAPE = makeItem("trinkets", "chest/cape");
    public static TagKey<Item> CURIOS_CHEST = makeItem("curios", "glider");
    public static TagKey<Item> CURIOS_BACK = makeItem("curios", "back");
    public static TagKey<Item> CURIOS_CAPE = makeItem("curios", "cape");
    public static TagKey<Block> UPDRAFT_BLOCKS = makeBlock(VCGliders.MOD_ID, "updraft");


    public static TagKey<Item> makeItem(String domain, String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(domain, path));
    }

    public static TagKey<Block> makeBlock(String domain, String path) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(domain, path));
    }

}
