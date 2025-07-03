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
package net.venturecraft.gliders.registry.neoforge;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.venturecraft.gliders.VCGliders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes"})
@EventBusSubscriber(modid = VCGliders.MOD_ID, value = Dist.CLIENT)
public class EntityRendererRegistryImpl {

    private static final Map<Supplier<EntityType<?>>, EntityRendererProvider<?>> RENDERERS = new HashMap<>();
    private static final Map<ModelLayerLocation, Supplier<LayerDefinition>> MODEL_LAYERS = new ConcurrentHashMap<>();
    private static final List<Pair<Predicate<EntityType<?>>, Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>>>> RENDER_LAYERS = new ArrayList<>();

    public static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererProvider<T> factory) {
        RENDERERS.put((Supplier<EntityType<?>>) (Supplier<? extends EntityType<?>>) type, factory);
    }

    public static void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        MODEL_LAYERS.put(location, definitionSupplier);
    }

    public static void addRenderLayer(Predicate<EntityType<?>> entityType, Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>> renderLayer) {
        RENDER_LAYERS.add(Pair.of(entityType, renderLayer));
    }

    @SubscribeEvent
    public static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        for (Map.Entry<Supplier<EntityType<?>>, EntityRendererProvider<?>> entry : RENDERERS.entrySet()) {
            event.registerEntityRenderer(entry.getKey().get(), (EntityRendererProvider<Entity>) entry.getValue());
        }
    }

    @SubscribeEvent
    public static void modelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (Map.Entry<ModelLayerLocation, Supplier<LayerDefinition>> entry : MODEL_LAYERS.entrySet()) {
            event.registerLayerDefinition(entry.getKey(), entry.getValue());
        }
    }

    @SubscribeEvent
    public static void renderLayers(EntityRenderersEvent.AddLayers e) {
        for (EntityType entityType : BuiltInRegistries.ENTITY_TYPE) {
            try {
                for (Pair<Predicate<EntityType<?>>, Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>>> pair : RENDER_LAYERS) {
                    if (pair.getFirst().test(entityType)) {
                        if (entityType == EntityType.PLAYER) {
                            e.getSkins().forEach(skin -> {
                                LivingEntityRenderer renderer = e.getSkin(skin);
                                if (renderer != null) {
                                    renderer.addLayer(pair.getSecond().apply(renderer));
                                }
                            });
                        } else {
                            EntityRenderer renderer = e.getRenderer(entityType);

                            if (renderer instanceof LivingEntityRenderer livingRenderer) {
                                livingRenderer.addLayer(pair.getSecond().apply(livingRenderer));
                            }
                        }
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }

}