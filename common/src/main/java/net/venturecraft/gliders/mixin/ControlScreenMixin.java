package net.venturecraft.gliders.mixin;

import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.venturecraft.gliders.util.ClientUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public class ControlScreenMixin {

    @Inject(method = "init()V", at = @At(value = "RETURN"))
    private void init(CallbackInfo info) {
        ControlsScreen controlsScreen = (ControlsScreen) (Object) this;
        ClientUtil.povButton(controlsScreen);
    }


}
