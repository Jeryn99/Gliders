package net.venturecraft.gliders;

import net.minecraft.resources.ResourceLocation;
import net.venturecraft.gliders.common.item.ItemRegistry;
import net.venturecraft.gliders.network.GliderNetwork;

public class VCGliders {

    public static final String MOD_ID = "vc_gliders";

    public static void init() {
        ItemRegistry.ITEMS.register();
        GliderNetwork.init();
    }

    public static ResourceLocation id(String location) {
        return new ResourceLocation(MOD_ID, location);
    }
}
