package net.venturecraft.gliders.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.venturecraft.gliders.GliderEventsFabric;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.compat.trinket.TrinketsUtil;

public class VCGlidersFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VCGliders.init("fabric");
        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            CuriosTrinketsUtil.setInstance(new TrinketsUtil());
        }
        GliderEventsFabric.init();
    }

}
