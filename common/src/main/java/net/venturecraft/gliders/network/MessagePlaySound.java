package net.venturecraft.gliders.network;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.util.ClientUtil;
import net.venturecraft.gliders.util.GliderUtil;

import java.util.UUID;

public class MessagePlaySound {
    public static final ResourceLocation CHANNEL = VCGliders.id("play_sound");
    public static final StreamCodec<FriendlyByteBuf, MessagePlaySound> STREAM_CODEC = StreamCodec.ofMember(MessagePlaySound::encode, MessagePlaySound::new);

    private final ResourceLocation sound;
    private final UUID playerUUID;

    public MessagePlaySound(ResourceLocation sound, UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.sound = sound;
    }

    public MessagePlaySound(FriendlyByteBuf buffer) {
        sound = buffer.readResourceLocation();
        playerUUID = buffer.readUUID();
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(this.sound);
        buffer.writeUUID(this.playerUUID);
    }

    public static void handle(PacketContext<MessagePlaySound> context) {
        if (Side.CLIENT.equals(context.side())) {
            handleClient(context);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void handleClient(PacketContext<MessagePlaySound> context) {
        if (Minecraft.getInstance().level != null) {
            Player player = Minecraft.getInstance().level.getPlayerByUUID(context.message().playerUUID);
            if (player != null) {
                ClientUtil.playGliderSound(player, context.message().sound, SoundSource.PLAYERS, true, () -> !GliderUtil.isGlidingWithActiveGlider(player), 0.1F, RandomSource.create());
            }
        }
    }

}