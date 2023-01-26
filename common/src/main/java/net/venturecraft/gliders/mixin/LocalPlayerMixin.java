package net.venturecraft.gliders.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.venturecraft.gliders.client.animation.PlayerAnimData;
import net.venturecraft.gliders.network.MessageToggleGlide;
import net.venturecraft.gliders.util.GliderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    // Logically, I could just write logic for checking if the player press space while falling, but if the logic already exists in the vanilla game...it does make sense to use it
    @Inject(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"))
    private void aiStep(CallbackInfo info) {
        LocalPlayer localPlayer = (LocalPlayer) (Object) this;

        if (GliderUtil.hasGliderEquipped(localPlayer) && !localPlayer.isOnGround()) {
            new MessageToggleGlide().send();
        }
    }


    @Inject(method = "tick()V", at = @At(value = "TAIL"))
    private void tick(CallbackInfo info) {
        LocalPlayer localPlayer = (LocalPlayer) (Object) this;
        PlayerAnimData.getOrAdd(localPlayer).tick(localPlayer);
    }


}