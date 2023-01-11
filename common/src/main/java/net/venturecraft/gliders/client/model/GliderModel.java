package net.venturecraft.gliders.client.model;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.venturecraft.gliders.util.GliderUtil;

public class GliderModel extends HierarchicalModel {

    public static final AnimationDefinition CLOSING = AnimationDefinition.Builder.withLength(0.25f).addAnimation("CentreBrace", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.041666666666666664f, KeyframeAnimations.posVec(0f, -3.05f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.08333333333333333f, KeyframeAnimations.posVec(0f, -7.37f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -11.63f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.16666666666666666f, KeyframeAnimations.posVec(0f, -14.54f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -17f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -85f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LBrace", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -87.5f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 85f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RBrace", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 87.5f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LMain", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 60f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RMain", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -60f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LStrut", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -132.5f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RStrut", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 132.5f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("Glider", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.04166666666666666f, KeyframeAnimations.posVec(0f, 1.7799999999999998f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 6.1f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.16666666666666669f, KeyframeAnimations.posVec(0f, 7.8f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.20833333333333334f, KeyframeAnimations.posVec(0f, 8.5f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 9f, 0f), AnimationChannel.Interpolations.CATMULLROM))).build();
    public static final AnimationDefinition OPENING = AnimationDefinition.Builder.withLength(30f).addAnimation("CentreBrace", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.125f, KeyframeAnimations.posVec(0f, -17f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.16666666666666666f, KeyframeAnimations.posVec(0f, -15.35f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.20833333333333334f, KeyframeAnimations.posVec(0f, -10.459999999999994f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -3.21f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, 2.4f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.3333333333333333f, KeyframeAnimations.posVec(0f, 3.9999999999999996f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 3.6599999999999984f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4166666666666667f, KeyframeAnimations.posVec(0f, 2.29f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4583333333333333f, KeyframeAnimations.posVec(0f, 0.76f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.10000000000000017f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, -85f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, -5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LBrace", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, -87.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, 20f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 85f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, 5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RBrace", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 87.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, -20f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LMain", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 60f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RMain", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, -60f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LStrut", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, -132.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, 12.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RStrut", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 132.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.degreeVec(0f, 0f, -12.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("Glider", new AnimationChannel(AnimationChannel.Targets.POSITION, new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 9f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.2916666666666667f, KeyframeAnimations.posVec(0f, 1.7799999999999998f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 1.7799999999999998f, 0f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("Glider", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0.125f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.5f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(1f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(2.2916666666666665f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(5.25f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(9.583333333333334f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(13.666666666666666f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(18f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(22.75f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(27.083333333333332f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(30f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(34.333333333333336f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM))).build();

    private final ModelPart CentreBrace;
    private final ModelPart RMain;
    private final ModelPart LMain;
    private final ModelPart root;

    public GliderModel(ModelPart root) {
        this.root = root;
        this.CentreBrace = root.getChild("CentreBrace");
        this.RMain = root.getChild("RMain");
        this.LMain = root.getChild("LMain");
    }

    public static LayerDefinition getModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition CentreBrace = partdefinition.addOrReplaceChild("CentreBrace", CubeListBuilder.create().texOffs(25, 45).addBox(-1.0F, -0.1F, -8.0F, 2.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));

        PartDefinition RMain = partdefinition.addOrReplaceChild("RMain", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition RMain_r1 = RMain.addOrReplaceChild("RMain_r1", CubeListBuilder.create().texOffs(34, 0).addBox(-13.0F, 0.0F, -0.25F, 13.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.75F, 0.0F, 0.0F, -0.3927F));

        PartDefinition RStrut = RMain.addOrReplaceChild("RStrut", CubeListBuilder.create(), PartPose.offset(-12.0F, 5.0F, 0.0F));

        PartDefinition RStrut_r1 = RStrut.addOrReplaceChild("RStrut_r1", CubeListBuilder.create().texOffs(2, 27).addBox(-0.1166F, -0.5788F, -0.25F, 12.0F, 1.0F, 16.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.75F, 0.0F, 0.0F, 0.2182F));

        PartDefinition RArm = RMain.addOrReplaceChild("RArm", CubeListBuilder.create(), PartPose.offset(-12.0F, 5.0F, 0.0F));

        PartDefinition RPole_r1 = RArm.addOrReplaceChild("RPole_r1", CubeListBuilder.create().texOffs(43, 16).addBox(-13.5F, -0.5F, -0.25F, 1.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -5.0F, -7.75F, 0.0F, 0.0F, -0.3927F));

        PartDefinition RMain_r2 = RArm.addOrReplaceChild("RMain_r2", CubeListBuilder.create().texOffs(5, 0).addBox(-1.9134F, 4.5922F, -0.25F, 1.0F, 13.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, -7.75F, 0.0F, 0.0F, -0.3927F));

        PartDefinition RBrace = RArm.addOrReplaceChild("RBrace", CubeListBuilder.create(), PartPose.offset(2.0F, 2.4F, 0.0F));

        PartDefinition RBrace_r1 = RBrace.addOrReplaceChild("RBrace_r1", CubeListBuilder.create().texOffs(0, 46).mirror().addBox(-0.2687F, -0.5127F, -16.0F, 4.0F, 1.0F, 16.0F, new CubeDeformation(-0.02F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition LMain = partdefinition.addOrReplaceChild("LMain", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition LMain_r1 = LMain.addOrReplaceChild("LMain_r1", CubeListBuilder.create().texOffs(8, 0).addBox(0.0F, 0.0F, -0.25F, 13.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.75F, 0.0F, 0.0F, 0.3927F));

        PartDefinition LStrut = LMain.addOrReplaceChild("LStrut", CubeListBuilder.create(), PartPose.offset(12.0F, 5.0F, -7.75F));

        PartDefinition LStrut_r1 = LStrut.addOrReplaceChild("LStrut_r1", CubeListBuilder.create().texOffs(9, 44).addBox(-11.8834F, -0.5788F, -0.25F, 12.0F, 1.0F, 16.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition LArm = LMain.addOrReplaceChild("LArm", CubeListBuilder.create(), PartPose.offset(12.0F, 5.0F, 0.0F));

        PartDefinition LPole_r1 = LArm.addOrReplaceChild("LPole_r1", CubeListBuilder.create().texOffs(43, 16).addBox(12.5F, -0.5F, -0.25F, 1.0F, 1.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -5.0F, -7.75F, 0.0F, 0.0F, 0.3927F));

        PartDefinition LMain_r2 = LArm.addOrReplaceChild("LMain_r2", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, -0.0272F, -0.25F, 1.0F, 13.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -7.75F, 0.0F, 0.0F, 0.3927F));

        PartDefinition LBrace = LArm.addOrReplaceChild("LBrace", CubeListBuilder.create(), PartPose.offset(-2.0F, 2.4F, 0.0F));

        PartDefinition LBrace_r1 = LBrace.addOrReplaceChild("LBrace_r1", CubeListBuilder.create().texOffs(0, 46).addBox(-3.7313F, -0.5127F, -16.0F, 4.0F, 1.0F, 16.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 0.3927F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof LivingEntity livingEntity) {
            VenturePlayerData.get(livingEntity).ifPresent(iCap -> {
                this.root().getAllParts().forEach(ModelPart::resetPose);
                if (GliderUtil.isGlidingWithActiveGlider(livingEntity)) {
                    this.animate(iCap.getAnimation(VenturePlayerData.AnimationStates.GLIDER_OPENING), OPENING, ageInTicks);
                }
            });
        }
    }

    @Override
    public ModelPart root() {
        return root;
    }
}