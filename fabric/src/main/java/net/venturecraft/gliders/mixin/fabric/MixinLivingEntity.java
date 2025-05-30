package net.venturecraft.gliders.mixin.fabric;


import net.minecraft.world.entity.LivingEntity;
import net.venturecraft.gliders.util.GliderUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "getUseItemRemainingTicks()I", at = @At("HEAD"), cancellable = true)
    private void gliders$preventItemUse(CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (GliderUtil.isGlidingWithActiveGlider(entity)) {
            cir.setReturnValue(0);
        }
    }
}
