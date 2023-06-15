package net.venturecraft.gliders.forge.events;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.venturecraft.gliders.VCGliders;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = VCGliders.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents {

    @SubscribeEvent
    public static void texStitch(TextureStitchEvent textureStitchEvent){
        //TODO textureStitchEvent.addSprite(new ResourceLocation(VCGliders.MOD_ID, "item/glider_slot"));
    }

}
