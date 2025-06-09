package net.venturecraft.gliders.network;

import commonnetwork.api.Network;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import commonnetwork.networking.data.PacketContext;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.sound.SoundRegistry;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.util.GliderUtil;

public class MessageToggleGlide {
    public static final ResourceLocation CHANNEL = VCGliders.id("toggle_glide");
    public static final StreamCodec<FriendlyByteBuf, MessageToggleGlide> STREAM_CODEC = StreamCodec.ofMember(MessageToggleGlide::encode, MessageToggleGlide::new);


    public MessageToggleGlide() {
    }

    public MessageToggleGlide(FriendlyByteBuf buffer) {
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public void encode(FriendlyByteBuf buf) {

    }

    public static void handle(PacketContext<MessageToggleGlide> context) {
        var sender = context.sender();
        if (GliderUtil.hasGliderEquipped(sender)) {
            ItemStack chestItem = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(sender);
            GliderItem.setGlide(chestItem, !GliderItem.isGlidingEnabled(chestItem));
            if (GliderItem.isGlidingEnabled(chestItem)) {
                sender.level().playSound(null, sender.getX(), sender.getY(), sender.getZ(), GliderItem.isSpaceGlider(chestItem) ? SoundRegistry.SPACE_DEPLOY.get() : SoundRegistry.GLIDER_OPEN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                Network.getNetworkHandler().sendToClient(new MessagePlaySound(GliderItem.isSpaceGlider(chestItem) ? SoundRegistry.SPACE_GLIDE.get().getLocation() : SoundEvents.ELYTRA_FLYING.getLocation(), sender.getUUID()), sender);
            } else {
                GliderData.setLightningTimer(sender, 0);
            }
            Network.getNetworkHandler().sendToClient(new MessagePOV(GliderItem.isGlidingEnabled(chestItem) ? "THIRD_PERSON_BACK" : ""), sender);
        }
        GliderData.sync(sender);

    }

}