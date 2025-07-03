/*
MIT License

Copyright (c) 2022 ThreeTAG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package net.venturecraft.gliders.mixin.fabric;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.mixin.client.rendering.LivingEntityRendererAccessor;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.registry.fabric.EntityRendererRegistryImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Shadow
    private Map<EntityType<?>, EntityRenderer<?>> renderers;

    @Shadow
    private Map<String, EntityRenderer<? extends Player>> playerRenderers;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(at = @At("RETURN"), method = "onResourceManagerReload")
    private void onResourceManagerReload(ResourceManager resourceManager, CallbackInfo ci) {
        for (EntityType entityType : BuiltInRegistries.ENTITY_TYPE) {
            try {
                for (Pair<Predicate<EntityType<?>>, Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>>> pair : EntityRendererRegistryImpl.RENDER_LAYERS) {
                    if (pair.getFirst().test(entityType)) {
                        if (entityType == EntityType.PLAYER) {
                            this.playerRenderers.values().forEach(renderer -> {
                                if (renderer instanceof LivingEntityRendererAccessor accessor) {
                                    accessor.callAddFeature(pair.getSecond().apply((RenderLayerParent<?, ?>) accessor));
                                }
                            });
                        } else {
                            EntityRenderer renderer = this.renderers.get(entityType);

                            if (renderer instanceof LivingEntityRendererAccessor accessor) {
                                accessor.callAddFeature(pair.getSecond().apply((RenderLayerParent<?, ?>) accessor));
                            }
                        }
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }

}