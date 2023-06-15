package net.venturecraft.gliders.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.client.model.GliderModel;
import net.venturecraft.gliders.client.model.ModelRegistry;
import net.venturecraft.gliders.client.model.XWingModel;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.util.GliderUtil;

public class PlayerGliderLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public static GliderModel gliderModel;
    public static XWingModel<Entity> xWingModel;
    private static final ResourceLocation COPPER_EMBED = new ResourceLocation(VCGliders.MOD_ID, "textures/entity/glider/copper_overlay.png");
    private static final ResourceLocation COPPER_EMBED_CHARGED = new ResourceLocation(VCGliders.MOD_ID, "textures/entity/glider/copper_overlay_charged.png");
    private static final ResourceLocation XWING_TEXTURE = new ResourceLocation(VCGliders.MOD_ID, "textures/entity/glider/xwing.png");


    public PlayerGliderLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
        gliderModel = new GliderModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelRegistry.GLIDER));
        xWingModel = new XWingModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelRegistry.X_WING));
    }

    public static ResourceLocation getGliderTexture(ItemStack stack) {
        if (stack.getDisplayName().getString().contains("xwing")) return XWING_TEXTURE;
        ResourceLocation itemLoc = new ResourceLocation(""); //TODO Registries.ITEM.getKey(stack.getItem());
        return new ResourceLocation(itemLoc.getNamespace(), "textures/entity/glider/" + itemLoc.getPath() + ".png");
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int p_117351_, T living, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (living.isInvisibleTo(Minecraft.getInstance().player)) return;

        ItemStack stack =  CuriosTrinketsUtil.getInstance().getFirstGliderInSlot(living, CuriosTrinketsUtil.BACK.identifier());

        // Render above players when gliding
        if (GliderUtil.isGlidingWithActiveGlider(living)) {
            poseStack.pushPose();


            if (stack.getDisplayName().getString().contains("xwing")) {
                // Translate and render base glider
                poseStack.translate(0, -1.9, -0.5);
                xWingModel.setupAnim(living, 0, 0, living.tickCount, 0, 0);
                xWingModel.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(getGliderTexture(stack))), p_117351_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            } else {

                // Translate and render base glider
                poseStack.translate(0, -1.9, 0);
                gliderModel.setupAnim(living, 0, 0, living.tickCount, 0, 0);
                gliderModel.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(getGliderTexture(stack))), p_117351_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

                // Has Coppered Embedded
                if (GliderItem.hasCopperUpgrade(stack)) {
                    gliderModel.setupAnim(living, 0, 0, living.tickCount, 0, 0);
                    gliderModel.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.eyes(GliderItem.hasBeenStruck(stack) ? COPPER_EMBED_CHARGED : COPPER_EMBED)), p_117351_, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
                }
            }
            poseStack.popPose();
        }

    }

}