package net.venturecraft.gliders.neoforge;


import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.VCGlidersClient;
import net.venturecraft.gliders.compat.trinket.CuriosUtil;
import net.venturecraft.gliders.data.neoforge.VCAttachments;
import net.venturecraft.gliders.neoforge.data.*;

@Mod(VCGliders.MOD_ID)
@EventBusSubscriber(modid = VCGliders.MOD_ID)
public class VCGlidersNeoForge {

    public VCGlidersNeoForge(IEventBus eventBus, ModContainer container) {
        VCGliders.init("neoforge");
        VCAttachments.register(eventBus);

        if (FMLEnvironment.dist.isClient()) {
            VCGlidersClient.init();
        }
        if (ModList.get().isLoaded("curios")) {
            CuriosUtil.init(eventBus);
        }
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent e) {
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();
        DataGenerator generator = e.getGenerator();

        var blockTagsProvider = generator.addProvider(e.includeServer(), new BlockTagsProvider(generator.getPackOutput(), e.getLookupProvider(), existingFileHelper));

//        generator.addProvider(e.includeServer(), new RecipeGeneration(generator));

        generator.addProvider(e.includeServer(), new ItemTagsProvider(generator.getPackOutput(), e.getLookupProvider(), blockTagsProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(e.includeClient(), new ItemModelGeneration(generator, existingFileHelper));
        generator.addProvider(e.includeClient(), new EnglishLangProvider(generator));
        generator.addProvider(e.includeClient(), new SoundProvider(generator, existingFileHelper));
        generator.addProvider(e.includeServer(), new DamageSourceGeneration(generator.getPackOutput(), e.getLookupProvider()));


    }
}
