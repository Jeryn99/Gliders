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
        DataGenerator generator = e.getGenerator();
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();
        generator.addProvider(e.includeClient(), new ItemModelGeneration(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new EnglishLangProvider(generator));
        generator.addProvider(e.includeClient(), new SoundProvider(generator, existingFileHelper));
        BlockTagsProvider blocktags = new BlockTagsProvider(generator.getPackOutput(), e.getLookupProvider(), existingFileHelper);
        generator.addProvider(e.includeClient(), new ItemTagsProvider(generator, blocktags, existingFileHelper));
        generator.addProvider(e.includeClient(), blocktags);
        generator.addProvider(e.includeServer(), new RecipeGeneration(generator));
    }
}
