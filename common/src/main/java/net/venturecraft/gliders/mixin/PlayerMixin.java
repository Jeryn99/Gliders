package net.venturecraft.gliders.mixin;

import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.client.animation.PlayerAnimData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "tick()V", at = @At(value = "TAIL"))
    private void tick(CallbackInfo info) {
        Player player = (Player) (Object) this;
        PlayerAnimData.getOrAdd(player).tick(player);
    }

}
