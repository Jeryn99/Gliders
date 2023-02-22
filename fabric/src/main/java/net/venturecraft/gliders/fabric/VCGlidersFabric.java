package net.venturecraft.gliders.fabric;

import net.threetag.palladiumcore.util.Platform;
import net.venturecraft.gliders.VCGliders;
import net.fabricmc.api.ModInitializer;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.compat.trinket.TrinketsUtil;

public class VCGlidersFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VCGliders.init();
        if(Platform.isModLoaded("trinkets")){
            CuriosTrinketsUtil.setInstance(new TrinketsUtil());
        }
    }

}
