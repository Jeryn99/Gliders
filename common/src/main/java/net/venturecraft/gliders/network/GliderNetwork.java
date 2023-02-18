package net.venturecraft.gliders.network;

import net.threetag.palladiumcore.network.MessageType;
import net.threetag.palladiumcore.network.NetworkManager;
import net.venturecraft.gliders.VCGliders;

public class GliderNetwork {

    public static final NetworkManager INSTANCE = NetworkManager.create(VCGliders.id("main"));

    public static MessageType TOGGLE_GLIDER, PLAY_SOUND, POV;

    public static void init() {
        TOGGLE_GLIDER = INSTANCE.registerC2S("toggle_glider", MessageToggleGlide::new);
        PLAY_SOUND = INSTANCE.registerS2C("play_sound", MessagePlaySound::new);
        POV = INSTANCE.registerS2C("pov", MessagePOV::new);
    }

}
