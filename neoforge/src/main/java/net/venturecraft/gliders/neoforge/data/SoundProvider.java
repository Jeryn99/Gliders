package net.venturecraft.gliders.neoforge.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.sound.SoundRegistry;

public class SoundProvider extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param generator The data generator instance provided by the event you are initializing this provider in.
     * @param helper    The existing file helper provided by the event you are initializing this provider in.
     */
    public SoundProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator.getPackOutput(), VCGliders.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        createDefinitionAndAdd(SoundRegistry.GLIDER_OPEN.get(), SoundDefinition.SoundType.SOUND, "glider_open", "glider/glider_open_0", "glider/glider_open_1");
        createDefinitionAndAdd(SoundRegistry.SPACE_GLIDE.get(), SoundDefinition.SoundType.SOUND, "space_glide", "glider/space_glide");
        createDefinitionAndAdd(SoundRegistry.SPACE_DEPLOY.get(), SoundDefinition.SoundType.SOUND, "space_deploy", "glider/space_deploy");
        createDefinitionAndAdd(SoundRegistry.INCOMING_LIGHTNING.get(), SoundDefinition.SoundType.SOUND, "incoming_lightning", "environment/incoming_lightning");
    }

    public void createDefinitionAndAdd(SoundEvent mainSound, SoundDefinition.SoundType soundType, String subtitle, String... soundEvent) {
        SoundDefinition def = SoundDefinition.definition().subtitle("subtitle." + VCGliders.MOD_ID + "." + subtitle);
        for (String event : soundEvent) {
            def.with(SoundDefinition.Sound.sound(VCGliders.id(event), soundType));
        }
        add(mainSound, def);
    }

}