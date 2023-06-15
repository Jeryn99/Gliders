package net.venturecraft.gliders.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.venturecraft.gliders.VCGliders;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnimationUtil {

    public static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    // Due to Mojank, this has to be created, I don't like it, you don't like it, no-one likes it...
    public static Optional<ModelPart> getAnyDescendantWithName(HumanoidModel<?> model, String partName) {
        return switch (partName) {
            case "RightLeg" -> Optional.of(model.rightLeg);
            case "LeftLeg" -> Optional.of(model.leftLeg);
            case "LeftArm" -> Optional.of(model.leftArm);
            case "RightArm" -> Optional.of(model.rightArm);
            case "Body" -> Optional.of(model.body);
            case "Head" -> Optional.of(model.head);
            default -> Optional.empty();
        };

    }

    public static void animate(HumanoidModel<?> humanoidModel, AnimationDefinition animationDefinition, long timeElapsed, float p_232323_, Vector3f vector3f) {
        float elapsedSeconds = getElapsedSeconds(animationDefinition, timeElapsed);

        for (Map.Entry<String, List<AnimationChannel>> entry : animationDefinition.boneAnimations().entrySet()) {
            getAnyDescendantWithName(humanoidModel, entry.getKey()).ifPresentOrElse(modelPart -> {
                List<AnimationChannel> list = entry.getValue();
                list.forEach((animationChannel) -> {
                    Keyframe[] keyframes = animationChannel.keyframes();
                    int i = Math.max(0, Mth.binarySearch(0, keyframes.length, (keyframe) -> elapsedSeconds <= keyframes[keyframe].timestamp()) - 1);
                    int j = Math.min(keyframes.length - 1, i + 1);
                    Keyframe currentKeyframe = keyframes[i];
                    Keyframe nextKeyframe = keyframes[j];
                    float f1 = elapsedSeconds - currentKeyframe.timestamp();
                    float f2 = Mth.clamp(f1 / (nextKeyframe.timestamp() - currentKeyframe.timestamp()), 0.0F, 1.0F);
                    nextKeyframe.interpolation().apply(vector3f, f2, keyframes, i, j, p_232323_);
                    animationChannel.target().apply(modelPart, vector3f);
                });
            }, () -> VCGliders.LOGGER.debug("Could not find part: {}", entry.getKey()));
        }
    }

    private static float getElapsedSeconds(AnimationDefinition animationDefinition, long timeElapsed) {
        float f = (float) timeElapsed / 1000.0F;
        return animationDefinition.looping() ? f % animationDefinition.lengthInSeconds() : f;
    }

    public static void animate(HumanoidModel<?> model, AnimationState animationState, AnimationDefinition animationDefinition, float p_233388_, float p_233389_) {
        animationState.updateTime(p_233388_, p_233389_);
        animationState.ifStarted((p_233392_) -> {
            animate(model, animationDefinition, animationState.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE);
        });
    }
}