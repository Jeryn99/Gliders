package net.venturecraft.gliders.common.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.UpgradeRecipe;

public class GliderUpgradingRecipe extends UpgradeRecipe {

    private final Ingredient base;
    private final Ingredient addition;
    private final String upgrade;

    public GliderUpgradingRecipe(ResourceLocation resourceLocation, Ingredient base, Ingredient addition, String upgrade) {
        super(resourceLocation, base, addition, ItemStack.EMPTY);
        this.base = base;
        this.addition = addition;
        this.upgrade = upgrade;
    }

    public static ItemStack makeResult(ItemStack base, String upgrade) {
        var result = base.copy();
        result.getOrCreateTag().putBoolean(upgrade + "_upgrade", true);
        return result;
    }

    @Override
    public ItemStack assemble(Container container) {
        return makeResult(container.getItem(0), this.upgrade);
    }

    @Override
    public ItemStack getResultItem() {
        return makeResult(this.base.getItems()[0], this.upgrade);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.GLIDER_UPGRADING.get();
    }

    public static class Serializer implements RecipeSerializer<GliderUpgradingRecipe> {

        @Override
        public GliderUpgradingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient base = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "base"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));
            String upgrade = GsonHelper.getAsString(json, "upgrade");
            return new GliderUpgradingRecipe(recipeId, base, addition, upgrade);
        }

        @Override
        public GliderUpgradingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient base = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            String upgrade = buffer.readUtf();
            return new GliderUpgradingRecipe(recipeId, base, addition, upgrade);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, GliderUpgradingRecipe recipe) {
            recipe.base.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeUtf(recipe.upgrade);
        }
    }

}
