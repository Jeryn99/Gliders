package net.venturecraft.gliders.forge;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.threetag.palladiumcore.forge.PalladiumCoreForge;
import net.threetag.palladiumcore.util.Platform;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.VCGlidersClient;
import net.venturecraft.gliders.compat.trinket.CuriosUtil;
import net.venturecraft.gliders.forge.data.*;

@Mod(VCGliders.MOD_ID)
@Mod.EventBusSubscriber(modid = VCGliders.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VCGlidersForge {

    public VCGlidersForge() {
        // Submit our event bus to let PalladiumCore register our content on the right time
        PalladiumCoreForge.registerModEventBus(VCGliders.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        VCGliders.init();

        if (Platform.isModLoaded("curios")) {
            PalladiumCoreForge.registerModEventBus("curios", FMLJavaModLoadingContext.get().getModEventBus());
        }

        if (Platform.isClient()) {
            VCGlidersClient.init();
        }

        if (Platform.isModLoaded("curios")) {
            CuriosUtil.init();
        }
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent e) {
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();
        DataGenerator generator = e.getGenerator();

        var blockTagsProvider = generator.addProvider(e.includeServer(), new BlockTagsProvider(generator.getPackOutput(), e.getLookupProvider(), existingFileHelper));

        generator.addProvider(e.includeServer(), new RecipeGeneration(generator));

        generator.addProvider(e.includeServer(), new ItemTagsProvider(generator.getPackOutput(), e.getLookupProvider(), blockTagsProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(e.includeClient(), new ItemModelGeneration(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new EnglishLangProvider(generator));
        generator.addProvider(e.includeClient(), new SoundProvider(generator, existingFileHelper));
        generator.addProvider(e.includeServer(), new DamageSourceGeneration(generator.getPackOutput(), e.getLookupProvider()));


    }
}
