package net.venturecraft.gliders.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.venturecraft.gliders.network.MessageToggleGlide;
import net.venturecraft.gliders.util.GliderUtil;
import net.venturecraft.gliders.util.VCGliderTags;
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

        if (GliderUtil.hasGliderEquipped(localPlayer) && canDeployHere(localPlayer)) {
            new MessageToggleGlide().send();
        }
    }

    public boolean canDeployHere(LocalPlayer localPlayer) {
        boolean isAir = localPlayer.level.getBlockState(localPlayer.blockPosition().below(2)).isAir() && localPlayer.level.getBlockState(localPlayer.blockPosition().below()).isAir();
        boolean updraftAround = localPlayer.level.getBlockStates(localPlayer.getBoundingBox().contract(2,0,2)).toList().stream().anyMatch(blockState -> blockState.is(VCGliderTags.UPDRAFT_BLOCKS));
        boolean isUpdraft = localPlayer.level.getBlockState(localPlayer.blockPosition().below()).is(VCGliderTags.UPDRAFT_BLOCKS);
        return isUpdraft || isAir || updraftAround;
    }

}