package net.venturecraft.gliders.forge;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.venturecraft.gliders.VCGliders;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.threetag.palladiumcore.forge.PalladiumCoreForge;
import net.venturecraft.gliders.forge.data.ItemModelGeneration;
import net.venturecraft.gliders.forge.data.RecipeGeneration;

@Mod(VCGliders.MOD_ID)
@Mod.EventBusSubscriber(modid = VCGliders.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VCGlidersForge {

    public VCGlidersForge() {
        // Submit our event bus to let PalladiumCore register our content on the right time
        PalladiumCoreForge.registerModEventBus(VCGliders.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        VCGliders.init();
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();
        generator.addProvider(e.includeClient(), new ItemModelGeneration(generator, existingFileHelper));
        generator.addProvider(e.includeServer(), new RecipeGeneration(generator));
    }
}
