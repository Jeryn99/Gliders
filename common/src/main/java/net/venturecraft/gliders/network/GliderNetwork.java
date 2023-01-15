package net.venturecraft.gliders.network;

import net.threetag.palladiumcore.network.MessageType;
import net.threetag.palladiumcore.network.NetworkManager;
import net.venturecraft.gliders.VCGliders;

public class GliderNetwork {

    public static final NetworkManager INSTANCE = NetworkManager.create(VCGliders.id("main"));

    public static MessageType TOGGLE_GLIDER;

    public static void init() {
        TOGGLE_GLIDER = INSTANCE.registerC2S("toggle_glider", MessageToggleGlide::new);
    }

}
