package net.venturecraft.gliders.common.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistryHolder;
import net.venturecraft.gliders.VCGliders;

import static net.venturecraft.gliders.VCGliders.MOD_ID;

public class SoundRegistry {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(MOD_ID, Registries.SOUND_EVENT);

    public static final RegistryHolder<SoundEvent, SoundEvent> GLIDER_OPEN = SOUNDS.register("glider_open", () -> setUpSound("glider_open"));
    public static final RegistryHolder<SoundEvent, SoundEvent> SPACE_GLIDE = SOUNDS.register("space_glide", () -> setUpSound("space_glide"));
    public static final RegistryHolder<SoundEvent, SoundEvent> SPACE_DEPLOY = SOUNDS.register("space_deploy", () -> setUpSound("space_deploy"));
    public static final RegistryHolder<SoundEvent, SoundEvent> INCOMING_LIGHTNING = SOUNDS.register("incoming_lightning", () -> setUpSound("incoming_lightning"));

    private static SoundEvent setUpSound(String soundName) {
        return SoundEvent.createFixedRangeEvent(VCGliders.id(soundName), 1);
    }


}
