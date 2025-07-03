package net.venturecraft.gliders.mixin;

import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.venturecraft.gliders.client.animation.AnimatedPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin implements AnimatedPlayer {

    @Unique
    private final ModifierLayer<IAnimation> vc_gliders$gliderLayer = new ModifierLayer<>();

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(ClientLevel clientLevel, GameProfile gameProfile, CallbackInfo ci) {
        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayer) (Object) this).addAnimLayer(1000, vc_gliders$gliderLayer);
    }


    @Override
    public ModifierLayer<IAnimation> gliders_getModifierLayer() {
        return vc_gliders$gliderLayer;
    }
}