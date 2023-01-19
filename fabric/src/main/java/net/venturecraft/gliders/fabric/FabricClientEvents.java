package net.venturecraft.gliders.fabric;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.venturecraft.gliders.client.animation.PlayerAnimData;

public class FabricClientEvents {

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level == null) {
                PlayerAnimData.clear();
            }
        });
    }

}
