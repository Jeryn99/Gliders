package net.venturecraft.gliders.network;

import net.minecraft.network.FriendlyByteBuf;
import net.threetag.palladiumcore.network.MessageContext;
import net.threetag.palladiumcore.network.MessageS2C;
import net.threetag.palladiumcore.network.MessageType;
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
    public void handle(MessageContext context) {
        ClientUtil.setPlayerPerspective(this.pointOfView);
    }

}
