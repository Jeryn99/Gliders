package net.venturecraft.gliders.client.animation;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.util.GliderUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class AnimationHandler {


    public static void setupAnimPre(HumanoidModel<?> humanoidModel, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {

    }

    public static void setupAnimPost(HumanoidModel<?> humanoidModel, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo callbackInfo) {
        if (livingEntity instanceof Player && GliderUtil.isGlidingWithActiveGlider(livingEntity)) {
            resetPoseAll(humanoidModel);
            GliderData animData = GliderData.get(livingEntity).get();
            resetPose(humanoidModel.body, humanoidModel.leftArm, humanoidModel.rightArm, humanoidModel.leftLeg, humanoidModel.rightLeg);
            AnimationUtil.animate(humanoidModel, animData.getAnimation(GliderData.AnimationStates.GLIDING), PlayerAnimations.GLIDING, ageInTicks, 1);
            fixLayers(humanoidModel);
        }
    }


    public static void resetPose(ModelPart... modelParts) {
        for (ModelPart part : modelParts) {
            part.resetPose();
        }
    }

    private static void resetPoseAll(HumanoidModel<?> bipedModel) {
        bipedModel.head.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.body.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.leftArm.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.rightArm.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.leftLeg.getAllParts().forEach(ModelPart::resetPose);
        bipedModel.rightLeg.getAllParts().forEach(ModelPart::resetPose);
    }

    private static void fixLayers(HumanoidModel<?> bipedModel) {
        if (bipedModel instanceof PlayerModel<?> playerModel) {
            playerModel.jacket.copyFrom(bipedModel.body);
            playerModel.leftPants.copyFrom(bipedModel.leftLeg);
            playerModel.rightPants.copyFrom(bipedModel.rightLeg);
            playerModel.leftSleeve.copyFrom(bipedModel.leftArm);
            playerModel.rightSleeve.copyFrom(bipedModel.rightArm);
            playerModel.hat.copyFrom(bipedModel.head);
        }
    }
}