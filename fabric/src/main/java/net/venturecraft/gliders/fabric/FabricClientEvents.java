package net.venturecraft.gliders.fabric;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.venturecraft.gliders.VCGlidersClient;

public class FabricClientEvents {

    public static void init() {
        ClientLifecycleEvents.CLIENT_STARTED.register((minecraft -> VCGlidersClient.clientSetup()));
    }

}
