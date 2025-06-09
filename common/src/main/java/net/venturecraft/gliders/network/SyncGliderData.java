package net.venturecraft.gliders.network;

import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.data.GliderData;

public class SyncGliderData {
    public static final ResourceLocation CHANNEL = VCGliders.id("sync");
    public static final StreamCodec<FriendlyByteBuf, SyncGliderData> STREAM_CODEC = StreamCodec.ofMember(SyncGliderData::encode, SyncGliderData::new);

    public int entityID;
    public CompoundTag nbt;

    public SyncGliderData(int entityID, CompoundTag nbt) {
        this.entityID = entityID;
        this.nbt = nbt;
    }

    public SyncGliderData(FriendlyByteBuf buf) {
        this.entityID = buf.readInt();
        this.nbt = buf.readNbt();
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeNbt(this.nbt);
    }

    public static void handle(PacketContext<SyncGliderData> context) {
        if (Side.CLIENT.equals(context.side())) {
            handleClient(context);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void handleClient(PacketContext<SyncGliderData> context) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        Entity entity = level.getEntity(context.message().entityID);

        if (entity instanceof Player player)
            GliderData.get(player).ifPresent((c) -> c.deserializeNBT(context.message().nbt));
    }
}