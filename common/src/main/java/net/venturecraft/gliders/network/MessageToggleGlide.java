package net.venturecraft.gliders.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.threetag.palladiumcore.network.MessageC2S;
import net.threetag.palladiumcore.network.MessageContext;
import net.threetag.palladiumcore.network.MessageType;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.sound.SoundRegistry;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.util.GliderUtil;
import org.jetbrains.annotations.NotNull;

public class MessageToggleGlide extends MessageC2S {

    public MessageToggleGlide() {
    }

    public MessageToggleGlide(FriendlyByteBuf buffer) {
    }

    @NotNull
    @Override
    public MessageType getType() {
        return GliderNetwork.TOGGLE_GLIDER;
    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(MessageContext context) {
        var sender = context.getPlayer();
        if (GliderUtil.hasGliderEquipped(sender)) {
            ItemStack chestItem =  CuriosTrinketsUtil.getInstance().getFirstFoundGlider(sender);
            GliderItem.setGlide(chestItem, !GliderItem.isGlidingEnabled(chestItem));
            if (GliderItem.isGlidingEnabled(chestItem)) {
                sender.level.playSound(null, sender.getX(), sender.getY(), sender.getZ(), GliderItem.isSpaceGlider(chestItem) ? SoundRegistry.SPACE_DEPLOY.get() : SoundRegistry.GLIDER_OPEN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                new MessagePlaySound(GliderItem.isSpaceGlider(chestItem) ? SoundRegistry.SPACE_GLIDE.get().getLocation() : SoundEvents.ELYTRA_FLYING.getLocation(), sender.getUUID()).sendToTracking(sender);
            } else {
                GliderItem.setLightningCounter(chestItem, 0);
            }
            new MessagePOV(GliderItem.isGlidingEnabled(chestItem) ? "THIRD_PERSON_BACK" : "").send(sender);
        }
        GliderData.get(sender).get().sync();

    }

}