package net.venturecraft.gliders.common.recipe;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistrySupplier;

import static net.venturecraft.gliders.VCGliders.MOD_ID;

public class RecipeSerializerRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY);

    public static final RegistrySupplier<RecipeSerializer<?>> GLIDER_UPGRADING = RECIPE_SERIALIZERS.register("glider_upgrading", GliderUpgradingRecipe.Serializer::new);

}
