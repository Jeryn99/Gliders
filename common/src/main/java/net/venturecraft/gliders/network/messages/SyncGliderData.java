package net.venturecraft.gliders.network.messages;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.network.GliderNetwork;
import net.venturecraft.gliders.network.MessageS2C;
import net.venturecraft.gliders.network.MessageType;
import org.jetbrains.annotations.NotNull;

public class SyncGliderData extends MessageS2C {

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

    @NotNull
    @Override
    public MessageType getType() {
        return GliderNetwork.SYNC_DATA;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeNbt(this.nbt);
    }

    @Override
    public void handle() {
        this.handleClient();
    }

    @Environment(EnvType.CLIENT)
    public void handleClient() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        Entity entity = level.getEntity(this.entityID);

        if (entity instanceof Player player)
            GliderData.get(player).ifPresent((c) -> c.deserializeNBT(this.nbt));
    }
}