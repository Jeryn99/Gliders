package net.venturecraft.gliders;

import net.minecraft.resources.ResourceLocation;
import net.venturecraft.gliders.common.item.ItemComponentRegistry;
import net.venturecraft.gliders.common.item.ItemRegistry;
import net.venturecraft.gliders.common.item.VCTabs;
import net.venturecraft.gliders.common.sound.SoundRegistry;
import net.venturecraft.gliders.network.GliderNetwork;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VCGliders {

    public static final String MOD_ID = "vc_gliders";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static String PLATFORM;

    public static void init(String platform) {
        PLATFORM = platform;
        ItemRegistry.ITEMS.register();
        ItemComponentRegistry.COMPONENT_TYPES.register();
        SoundRegistry.SOUNDS.register();
        VCTabs.TABS.register();
        GliderNetwork.init();
    }

    public static ResourceLocation id(String location) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, location);
    }
}
