package net.venturecraft.gliders.forge.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.client.layer.PlayerGliderLayer;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.util.GliderUtil;

import static net.venturecraft.gliders.VCGlidersClient.lightLevel;

@Mod.EventBusSubscriber(modid = VCGliders.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderBuffers bufferSource = Minecraft.getInstance().renderBuffers();
        LocalPlayer living = Minecraft.getInstance().player;
        ItemStack stack = CuriosTrinketsUtil.getInstance().getFirstGliderInSlot(living, CuriosTrinketsUtil.BACK.identifier());
        PoseStack posestack = event.getPoseStack();
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON && stack.getItem() instanceof GliderItem && GliderUtil.isGlidingWithActiveGlider(living)) {
            posestack.pushPose();
            posestack.mulPose(Vector3f.XP.rotationDegrees(180));
            posestack.mulPose(Vector3f.YP.rotationDegrees(living.getViewYRot(1F)));
            posestack.translate(0, -2.4, -0.5);
            posestack.scale(1.5F, 1.5F, 1.5F);
            if (GliderItem.isSpaceGlider(stack)) {
                posestack.translate(0, -0.2, 0);
                PlayerGliderLayer.xWingModel.setupAnim(living, 0, 0, living.tickCount, 0, 0);
                PlayerGliderLayer.xWingModel.renderToBuffer(posestack, bufferSource.bufferSource().getBuffer(RenderType.entityCutoutNoCull(PlayerGliderLayer.getGliderTexture(stack))), lightLevel, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            } else {
                PlayerGliderLayer.gliderModel.setupAnim(living, 0, 0, living.tickCount, 0, 0);
                PlayerGliderLayer.gliderModel.renderToBuffer(posestack, bufferSource.bufferSource().getBuffer(RenderType.entityCutoutNoCull(PlayerGliderLayer.getGliderTexture(stack))), lightLevel, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            }
            posestack.popPose();
        }
    }

    @SubscribeEvent
    public static void onMovement(MovementInputUpdateEvent event) {
        if (GliderUtil.isGlidingWithActiveGlider(Minecraft.getInstance().player)) {
            event.getInput().shiftKeyDown = false;
        }
    }

}
