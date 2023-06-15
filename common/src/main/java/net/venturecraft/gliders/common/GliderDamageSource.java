package net.venturecraft.gliders.common;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.venturecraft.gliders.VCGliders;

public class GliderDamageSource  {


    public static final ResourceKey<DamageType> BAD_LIGHTNING_EXPERIMENT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(VCGliders.MOD_ID, "zap_experiment"));

    public static DamageSource getSource(ServerLevel level, ResourceKey<DamageType> damageTypeResourceKey) {
        Holder.Reference<DamageType> damageType = level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(damageTypeResourceKey);
        return new DamageSource(damageType);
    }
}
