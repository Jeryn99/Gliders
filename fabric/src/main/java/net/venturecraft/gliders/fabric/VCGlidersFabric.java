package net.venturecraft.gliders.fabric;

import net.venturecraft.gliders.VCGliders;
import net.fabricmc.api.ModInitializer;

public class VCGlidersFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VCGliders.init();
    }

}
