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
package net.venturecraft.gliders.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class EntityRendererRegistry {

    @ExpectPlatform
    public static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> typeSupplier, EntityRendererProvider<T> provider) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> definitionSupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void addRenderLayer(Predicate<EntityType<?>> entityType, Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>> renderLayer) {
        throw new AssertionError();
    }

    public static void addRenderLayerToPlayer(Function<RenderLayerParent<?, ?>, RenderLayer<?, ?>> renderLayer) {
        addRenderLayer(type -> type == EntityType.PLAYER, renderLayer);
    }

}
