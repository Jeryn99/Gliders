package net.venturecraft.gliders.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.venturecraft.gliders.VCGlidersClient;

public class VCGlidersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VCGlidersClient.init();
    }
}
