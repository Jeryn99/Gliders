package net.venturecraft.gliders.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.threetag.palladiumcore.network.MessageC2S;
import net.threetag.palladiumcore.network.MessageContext;
import net.threetag.palladiumcore.network.MessageType;
import net.venturecraft.gliders.common.item.GliderItem;
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
        if (GliderUtil.hasParagliderEquipped(sender)) {
            ItemStack chestItem = sender.getItemBySlot(EquipmentSlot.CHEST);
            GliderItem.setGlide(chestItem, !GliderItem.glidingEnabled(chestItem));
            if (GliderItem.glidingEnabled(chestItem)) {
                //TODO
                /*                sender.level.playSound(null, sender.getX(), sender.getY(), sender.getZ(), GliderItem.isSpaceGlider(chestItem) ? ModSounds.SPACE_DEPLOY.get() : ModSounds.GLIDER_OPEN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                new MessagePlaySound(ParagliderItem.isSpaceGlider(chestItem) ? ModSounds.SPACE_GLIDE.get().getLocation() : SoundEvents.ELYTRA_FLYING.getLocation(), sender.getUUID()).sendToTracking(sender);*/

                // Damage Glider as used
                chestItem.hurtAndBreak(1, sender, e -> e.broadcastBreakEvent(EquipmentSlot.CHEST));
            }
        }
    }

}