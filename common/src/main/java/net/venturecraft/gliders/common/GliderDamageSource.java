package net.venturecraft.gliders.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.venturecraft.gliders.VCGliders;

public class GliderDamageSource extends DamageSource {

    public static GliderDamageSource BAD_LIGHTNING_EXPERIMENT = new GliderDamageSource("zap_experiment");
    private final String translationKey;

    public GliderDamageSource(String string) {
        super(string);
        this.translationKey = string;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
        return Component.translatable("dmg." + VCGliders.MOD_ID + "." + translationKey, livingEntity.getDisplayName());
    }
}
