package net.venturecraft.gliders.network;

import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.network.messages.MessagePOV;
import net.venturecraft.gliders.network.messages.MessagePlaySound;
import net.venturecraft.gliders.network.messages.MessageToggleGlide;
import net.venturecraft.gliders.network.messages.SyncGliderData;

public class GliderNetwork {

    public static final NetworkManager INSTANCE = NetworkManager.create(VCGliders.id("main"));

    public static MessageType TOGGLE_GLIDER, PLAY_SOUND, POV, SYNC_DATA;

    public static void init() {
        TOGGLE_GLIDER = INSTANCE.registerC2S("toggle_glider", MessageToggleGlide::new);
        PLAY_SOUND = INSTANCE.registerS2C("play_sound", MessagePlaySound::new);
        POV = INSTANCE.registerS2C("pov", MessagePOV::new);
        SYNC_DATA = INSTANCE.registerS2C("sync_data", SyncGliderData::new);
    }

}
