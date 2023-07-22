package net.venturecraft.gliders.forge.data;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GliderUpgradingRecipeBuilder {

    private final Ingredient base;
    private final Ingredient addition;
    private final String upgrade;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public GliderUpgradingRecipeBuilder(Ingredient base, Ingredient addition, String upgrade) {
        this.base = base;
        this.addition = addition;
        this.upgrade = upgrade;
    }

    public GliderUpgradingRecipeBuilder unlocks(String name, CriterionTriggerInstance criterion) {
        this.advancement.addCriterion(name, criterion);
        return this;
    }

    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, String id) {
        this.save(finishedRecipeConsumer, new ResourceLocation(id));
    }

    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation id) {
        this.ensureValid(id);
        this.advancement
                .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);
        finishedRecipeConsumer.accept(
                new GliderUpgradingRecipeBuilder.Result(
                        id,
                        this.base,
                        this.addition,
                        this.upgrade,
                        this.advancement,
                        new ResourceLocation(id.getNamespace(), "recipes/glider_upgrading/" + id.getPath())
                )
        );
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final Ingredient base;
        private final Ingredient addition;
        private final String upgrade;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(
                ResourceLocation resourceLocation,
                Ingredient ingredient,
                Ingredient ingredient2,
                String upgrade,
                Advancement.Builder builder,
                ResourceLocation resourceLocation2
        ) {
            this.id = resourceLocation;
            this.base = ingredient;
            this.addition = ingredient2;
            this.upgrade = upgrade;
            this.advancement = builder;
            this.advancementId = resourceLocation2;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("base", this.base.toJson());
            json.add("addition", this.addition.toJson());
            json.addProperty("upgrade", this.upgrade);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.GLIDER_UPGRADING.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
