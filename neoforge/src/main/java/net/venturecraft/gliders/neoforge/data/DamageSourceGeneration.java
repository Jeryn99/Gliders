package net.venturecraft.gliders.neoforge.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.GliderDamageSource;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DamageSourceGeneration extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, arg -> {
                arg.register(GliderDamageSource.ZAP_EXPERIMENT, new DamageType("zap_experiment", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 1));
            });

    public DamageSourceGeneration(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Set.of(VCGliders.MOD_ID));
    }

//    public static HolderLookup.Provider createLookup() {
//        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), VanillaRegistries.createLookup());
//    }


}