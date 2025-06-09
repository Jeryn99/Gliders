package net.venturecraft.gliders.network;

import commonnetwork.networking.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.util.ClientUtil;

public class MessagePOV {
    public static final ResourceLocation CHANNEL = VCGliders.id("pov");
    public static final StreamCodec<FriendlyByteBuf, MessagePOV> STREAM_CODEC = StreamCodec.ofMember(MessagePOV::encode, MessagePOV::new);

    private final String pointOfView;

    public MessagePOV(String pointOfView) {
        this.pointOfView = pointOfView;
    }

    public MessagePOV(FriendlyByteBuf buffer) {
        pointOfView = buffer.readUtf(32767);
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.pointOfView);
    }

    public static void handle(PacketContext<MessagePOV> context) {
        ClientUtil.setPlayerPerspective(context.message().pointOfView);
    }

}
