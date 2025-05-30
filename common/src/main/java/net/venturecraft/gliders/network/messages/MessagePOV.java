package net.venturecraft.gliders.network.messages;

import net.minecraft.network.FriendlyByteBuf;
import net.venturecraft.gliders.network.GliderNetwork;
import net.venturecraft.gliders.network.MessageS2C;
import net.venturecraft.gliders.network.MessageType;
import net.venturecraft.gliders.util.ClientUtil;
import org.jetbrains.annotations.NotNull;

public class MessagePOV extends MessageS2C {
    private final String pointOfView;

    public MessagePOV(String pointOfView) {
        this.pointOfView = pointOfView;
    }

    public MessagePOV(FriendlyByteBuf buffer) {
        pointOfView = buffer.readUtf(32767);
    }

    @NotNull
    @Override
    public MessageType getType() {
        return GliderNetwork.POV;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.pointOfView);
    }

    @Override
    public void handle() {
        ClientUtil.setPlayerPerspective(this.pointOfView);
    }

}
