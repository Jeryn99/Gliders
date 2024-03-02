package net.venturecraft.gliders.mixin.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.client.layer.PlayerGliderLayer;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.util.GliderUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleEngine;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;F)V"))
    public void renderLevel(PoseStack posestack, float f, long l, boolean bl, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix4f, CallbackInfo ci) {

        RenderBuffers bufferSource = Minecraft.getInstance().renderBuffers();

        LocalPlayer living = Minecraft.getInstance().player;
        ItemStack stack = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(living);

        if (Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON && stack.getItem() instanceof GliderItem && GliderUtil.isGlidingWithActiveGlider(living)) {
            posestack.pushPose();
            posestack.mulPose(Axis.XP.rotationDegrees(180));
            posestack.mulPose(Axis.YP.rotationDegrees(living.getViewYRot(1F)));
            posestack.translate(0, -2.4, -0.5);
            posestack.scale(1.5F, 1.5F, 1.5F);

            if (GliderItem.isSpaceGlider(stack)) {
                posestack.mulPose(Axis.YP.rotationDegrees(180));
                posestack.translate(0, -0.2, 0);
                PlayerGliderLayer.xWingModel.setupAnim(living, 0, 0, living.tickCount, 0, 0);
                PlayerGliderLayer.xWingModel.renderToBuffer(posestack, bufferSource.bufferSource().getBuffer(RenderType.entityCutoutNoCull(PlayerGliderLayer.getGliderTexture(stack))), LevelRenderer.getLightColor(living.level(), living.blockPosition()), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            } else {
                PlayerGliderLayer.gliderModel.setupAnim(living, 0, 0, living.tickCount, 0, 0);
                PlayerGliderLayer.gliderModel.renderToBuffer(posestack, bufferSource.bufferSource().getBuffer(RenderType.entityCutoutNoCull(PlayerGliderLayer.getGliderTexture(stack))), LevelRenderer.getLightColor(living.level(), living.blockPosition()), OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
            posestack.popPose();
        }

    }


}
