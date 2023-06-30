package net.venturecraft.gliders.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.venturecraft.gliders.client.animation.AnimationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class BipedBodyMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V")
    private void setupAnimHead(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        HumanoidModel<?> bipedModel = (HumanoidModel<?>) (Object) this;
      //  AnimationHandler.setupAnimPre(bipedModel, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, callbackInfo);
    }


    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V")
    private void setupAnimTail(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        HumanoidModel<LivingEntity> bipedModel = (HumanoidModel) (Object) this;
        AnimationHandler.setupAnimPost(bipedModel, livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, callbackInfo);
    }


}