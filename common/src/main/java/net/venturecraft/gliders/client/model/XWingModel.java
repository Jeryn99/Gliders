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
import net.venturecraft.gliders.client.animation.PlayerAnimData;
import net.venturecraft.gliders.util.GliderUtil;

public class XWingModel<T extends Entity> extends HierarchicalModel<T> {

    public static final AnimationDefinition OPEN_XWING = AnimationDefinition.Builder.withLength(26f).addAnimation("LW1", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 2.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4166666666666667f, KeyframeAnimations.degreeVec(0f, 0f, -17.66f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083333333333334f, KeyframeAnimations.degreeVec(0f, 0f, -17.5f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("LW2", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4166666666666667f, KeyframeAnimations.degreeVec(0f, 0f, 17.66f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6666666666666666f, KeyframeAnimations.degreeVec(0f, 0f, 15f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RW1", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -2.5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4166666666666667f, KeyframeAnimations.degreeVec(0f, 0f, 18.27f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.6666666666666666f, KeyframeAnimations.degreeVec(0f, 0f, 15f), AnimationChannel.Interpolations.CATMULLROM))).addAnimation("RW2", new AnimationChannel(AnimationChannel.Targets.ROTATION, new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 5f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.4166666666666667f, KeyframeAnimations.degreeVec(0f, 0f, -17.66f), AnimationChannel.Interpolations.CATMULLROM), new Keyframe(0.7083333333333334f, KeyframeAnimations.degreeVec(0f, 0f, -15f), AnimationChannel.Interpolations.CATMULLROM))).build();

    private final ModelPart bone;
    private final ModelPart bone4;
    private final ModelPart bone8;
    private final ModelPart LW1;
    private final ModelPart LW2;
    private final ModelPart RW1;
    private final ModelPart RW2;
    private final ModelPart root;

    public XWingModel(ModelPart root) {
        this.root = root;
        this.bone = root.getChild("bone");
        this.bone4 = root.getChild("bone4");
        this.bone8 = root.getChild("bone8");
        this.LW1 = root.getChild("LW1");
        this.LW2 = root.getChild("LW2");
        this.RW1 = root.getChild("RW1");
        this.RW2 = root.getChild("RW2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone7 = bone.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(62, 34).addBox(-3.0F, -1.5F, -3.5F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(23, 10).addBox(-3.0F, 1.5F, -10.5F, 6.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(55, 22).addBox(1.75F, 0.5F, -10.5F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(55, 22).addBox(-2.75F, 0.5F, -10.5F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.5F, 5.5F));

        PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(38, 51).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(24, 39).addBox(-1.25F, 1.0F, -7.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(24, 39).addBox(-5.75F, 1.0F, -7.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(38, 51).addBox(-6.0F, 0.0F, -7.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(13, 58).addBox(-6.0F, 0.0F, -8.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 0).addBox(-5.0F, 0.25F, -7.0F, 4.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.5F, -3.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone6 = bone4.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(23, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-1.25F, 1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-5.75F, 1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(23, 0).addBox(-6.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(61, 61).addBox(-6.0F, 0.0F, 2.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-5.0F, 0.25F, 0.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.5F, -2.5F, -0.5236F, 0.0F, 0.0F));

        PartDefinition bone8 = partdefinition.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(13, 53).addBox(-3.5F, -7.0F, -6.0F, 7.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 34).addBox(-1.0F, -7.5F, -18.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(46, 34).addBox(-1.0F, -3.5F, -18.0F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(11, 11).addBox(-2.0F, -7.5F, -19.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(57, 17).addBox(-2.0F, -5.5F, -22.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-4.0F, -8.0F, 5.0F, 8.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(11, 0).addBox(-2.0F, -8.0F, 16.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -7.5F, 17.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(57, 8).addBox(4.0F, -10.0F, 6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(57, 8).addBox(-7.0F, -10.0F, 6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(57, 8).addBox(4.0F, -5.0F, 6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(57, 8).addBox(-7.0F, -5.0F, 6.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(5, 5).addBox(5.0F, -9.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 5).addBox(-6.0F, -9.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 5).addBox(5.0F, -4.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(5, 5).addBox(-6.0F, -4.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(4.5F, -9.5F, 5.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-6.5F, -9.5F, 5.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(4.5F, -4.5F, 5.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-6.5F, -4.5F, 5.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(4.5F, -9.5F, 12.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-6.5F, -9.5F, 12.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(4.5F, -4.5F, 12.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-6.5F, -4.5F, 12.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(28, 22).addBox(-4.0F, -9.0F, 6.0F, 8.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 53).addBox(2.5F, -9.5F, 6.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(1.0F, -9.5F, 7.0F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -3.5F, -5.0F, 1.0F, 1.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-2.0F, -9.5F, 7.0F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -3.5F, -5.0F, 1.0F, 1.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-1.0F, -9.25F, 10.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-1.0F, -9.25F, 7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 10).addBox(-1.0F, -9.25F, 13.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 53).addBox(-3.5F, -9.5F, 6.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(-3.5F, -5.0F, -5.0F, 7.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone2 = bone8.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(27, 34).addBox(-3.0F, -3.0F, -13.0F, 3.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(41, 61).addBox(-4.0F, -3.0F, -7.0F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -4.0F, -6.0F, 0.0F, 0.1571F, 0.0F));

        PartDefinition bone11 = bone8.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(27, 34).addBox(0.0F, -3.0F, -13.0F, 3.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(41, 61).addBox(3.0F, -3.0F, -7.0F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -4.0F, -6.0F, 0.0F, -0.1571F, 0.0F));

        PartDefinition bone3 = bone8.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(50, 61).addBox(-4.0F, -2.0F, 0.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.5F, -18.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition bone12 = bone3.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition bone15 = bone8.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(50, 61).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -7.5F, -18.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(50, 61).addBox(-4.0F, 0.0F, -3.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition bone9 = bone8.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(11, 5).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 5).addBox(-2.0F, -3.61F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 5).addBox(-2.0F, -3.11F, 1.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.5F, -22.0F, -0.9828F, 0.0F, 0.0F));

        PartDefinition bone10 = bone8.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(11, 5).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 5).addBox(-2.0F, 0.61F, 0.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 5).addBox(-2.0F, 0.11F, 1.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, -22.0F, 0.9828F, 0.0F, 0.0F));

        PartDefinition bone13 = bone8.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(0, 39).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 39).addBox(-4.0F, 0.0F, 0.4142F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 5.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone17 = bone8.addOrReplaceChild("bone17", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone14 = bone8.addOrReplaceChild("bone14", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, 8.0F));

        PartDefinition LW1 = partdefinition.addOrReplaceChild("LW1", CubeListBuilder.create().texOffs(23, 0).addBox(0.0F, -0.5F, -4.0F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(48, 48).addBox(11.0F, -1.0F, -5.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(11.0F, -1.0F, -14.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 10).addBox(11.5F, -0.5F, -16.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(13.0F, -0.5F, -4.0F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 18.0F, 11.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition LW2 = partdefinition.addOrReplaceChild("LW2", CubeListBuilder.create().texOffs(23, 0).addBox(0.0F, -0.5F, -4.0F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(48, 48).addBox(11.0F, -1.0F, -5.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(11.0F, -1.0F, -14.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 10).addBox(11.5F, -0.5F, -16.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(13.0F, -0.5F, -4.0F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 18.0F, 11.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition RW1 = partdefinition.addOrReplaceChild("RW1", CubeListBuilder.create().texOffs(23, 0).addBox(-11.0F, -0.5F, -4.0F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(48, 48).addBox(-13.0F, -1.0F, -5.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-13.0F, -1.0F, -14.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 10).addBox(-12.5F, -0.5F, -16.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-14.0F, -0.5F, -4.0F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 18.0F, 11.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition RW2 = partdefinition.addOrReplaceChild("RW2", CubeListBuilder.create().texOffs(23, 0).addBox(-11.0F, -0.5F, -4.0F, 11.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(48, 48).addBox(-13.0F, -1.0F, -5.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-13.0F, -1.0F, -14.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 10).addBox(-12.5F, -0.5F, -16.0F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-14.0F, -0.5F, -4.0F, 1.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 18.0F, 11.0F, 0.0F, 0.0F, -0.1745F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof LivingEntity livingEntity) {

            PlayerAnimData animData = PlayerAnimData.getOrAdd(livingEntity);
            this.root().getAllParts().forEach(ModelPart::resetPose);

            if (GliderUtil.isGlidingWithActiveGlider(livingEntity)) {
                this.animate(animData.gliderOpen(), OPEN_XWING, ageInTicks);
            }
        }
    }

    @Override
    public ModelPart root() {
        return root;
    }
}