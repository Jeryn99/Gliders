package net.venturecraft.gliders.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class VCGliderTags {

    public static TagKey<Item> TRINKETS_BACK = makeItem("trinkets", "chest/back");
    public static TagKey<Item> CURIOS_CHEST = makeItem("curios", "glider");


    public static TagKey<Item> makeItem(String domain, String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(domain, path));
    }

}
