package net.venturecraft.gliders.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.threetag.palladiumcore.registry.client.EntityRendererRegistry;
import net.venturecraft.gliders.VCGliders;

public class ModelRegistry {

    public static ModelLayerLocation GLIDER = new ModelLayerLocation(VCGliders.id("model"), "glider");
    public static ModelLayerLocation X_WING = new ModelLayerLocation(VCGliders.id("model"), "x_wing");

    public static void init() {
        EntityRendererRegistry.registerModelLayer(GLIDER, GliderModel::getModelData);
        EntityRendererRegistry.registerModelLayer(X_WING, XWingModel::createBodyLayer);
    }


}
