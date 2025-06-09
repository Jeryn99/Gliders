package net.venturecraft.gliders.network;

import commonnetwork.api.Network;

public class GliderNetwork {

    public static void init() {
        Network.registerPacket(MessageToggleGlide.type(), MessageToggleGlide.class, MessageToggleGlide.STREAM_CODEC, MessageToggleGlide::handle)
                .registerPacket(MessagePlaySound.type(), MessagePlaySound.class, MessagePlaySound.STREAM_CODEC, MessagePlaySound::handle)
                .registerPacket(MessagePOV.type(), MessagePOV.class, MessagePOV.STREAM_CODEC, MessagePOV::handle)
                .registerPacket(SyncGliderData.type(), SyncGliderData.class, SyncGliderData.STREAM_CODEC, SyncGliderData::handle);;
    }

}
