package net.venturecraft.gliders.network.fabric;

import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.network.MessageC2S;
import net.venturecraft.gliders.network.MessageS2C;
import net.venturecraft.gliders.network.MessageType;
import net.venturecraft.gliders.network.NetworkManager;
import net.venturecraft.gliders.util.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class NetworkManagerImpl extends NetworkManager {

    public NetworkManagerImpl(ResourceLocation channelName) {
        super(channelName);
        ServerPlayNetworking.registerGlobalReceiver(channelName, (server, player, handler, buf, responseSender) -> {
            var msgId = buf.readUtf();

            if (!this.toServer.containsKey(msgId)) {
                VCGliders.LOGGER.warn("Unknown message id received on server: " + msgId);
                return;
            }

            MessageType type = this.toServer.get(msgId);
            MessageC2S message = (MessageC2S) type.getDecoder().decode(buf);
            server.execute(() -> message.handle(() -> player));
        });

        if (Platform.isClient()) {
            this.registerClient();
        }
    }

    public static NetworkManager create(ResourceLocation channelName) {
        return new NetworkManagerImpl(channelName);
    }

    public static Packet<?> spawnPacket(Entity livingEntity) {
        return new ClientboundAddEntityPacket(livingEntity);
    }

    @Environment(EnvType.CLIENT)
    private void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(channelName, (client, handler, buf, responseSender) -> {
            var msgId = buf.readUtf();

            if (!this.toClient.containsKey(msgId)) {
                VCGliders.LOGGER.warn("Unknown message id received on client: " + msgId);
                return;
            }

            MessageType type = this.toClient.get(msgId);
            MessageS2C message = (MessageS2C) type.getDecoder().decode(buf);
            client.execute(() -> message.handle(() -> null));
        });
    }

    @Override
    public void sendToServer(MessageC2S message) {
        if (!this.toServer.containsValue(message.getType())) {
            VCGliders.LOGGER.warn("Message type not registered: " + message.getType().getId());
            return;
        }

        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUtf(message.getType().getId());
        message.toBytes(buf);
        ClientPlayNetworking.send(this.channelName, buf);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, MessageS2C message) {
        if (!this.toClient.containsValue(message.getType())) {
            VCGliders.LOGGER.warn("Message type not registered: " + message.getType().getId());
            return;
        }

        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUtf(message.getType().getId());
        message.toBytes(buf);
        ServerPlayNetworking.send(player, this.channelName, buf);
    }
}
