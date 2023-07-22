package net.venturecraft.gliders.forge.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.data.LanguageProvider;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.GliderDamageSource;
import net.venturecraft.gliders.common.item.ItemRegistry;
import net.venturecraft.gliders.common.sound.SoundRegistry;
import net.venturecraft.gliders.util.ModConstants;

public class EnglishLangProvider extends LanguageProvider {

    public EnglishLangProvider(DataGenerator gen) {
        super(gen.getPackOutput(), VCGliders.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Gliders
        add(ItemRegistry.PARAGLIDER_WOOD.get(), "Basic Paraglider");
        add(ItemRegistry.PARAGLIDER_IRON.get(), "Iron Paraglider");
        add(ItemRegistry.PARAGLIDER_DIAMOND.get(), "Diamond Paraglider");
        add(ItemRegistry.PARAGLIDER_GOLD.get(), "Gold Paraglider");
        add(ItemRegistry.PARAGLIDER_NETHERITE.get(), "Netherite Paraglider");

        // Paper
        add(ItemRegistry.REINFORCED_PAPER.get(), "Re-Inforced Paper");
        add(ItemRegistry.REINFORCED_PAPER_IRON.get(), "Re-Inforced Paper (Iron)");
        add(ItemRegistry.REINFORCED_PAPER_GOLD.get(), "Re-Inforced Paper (Gold)");
        add(ItemRegistry.REINFORCED_PAPER_DIAMOND.get(), "Re-Inforced Paper (Diamond)");
        add(ItemRegistry.REINFORCED_PAPER_NETHERITE.get(), "Re-Inforced Paper (Netherite)");

        add(ItemRegistry.COPPER_UPGRADE.get(), Rarity.UNCOMMON.color + "Copper Upgrade");
        add(ItemRegistry.NETHER_UPGRADE.get(), Rarity.EPIC.color + "Nether Upgrade");

        add(ModConstants.NETHER_UPGRADE, Rarity.EPIC.color + "Nether Upgrade");
        add(ModConstants.COPPER_UPGRADE, Rarity.UNCOMMON.color + "Copper Upgrade");
        add(ModConstants.INSTALLED_UPGRADES, "Installed Upgrades:");

        addSound(SoundRegistry.GLIDER_OPEN.get(), "Glider opens");
        addSound(SoundRegistry.SPACE_GLIDE.get(), "Space Glide");
        addSound(SoundRegistry.SPACE_DEPLOY.get(), "Space Deploy");
        addSound(SoundRegistry.INCOMING_LIGHTNING.get(), "Incoming Lightning");

        add("itemGroup." + VCGliders.MOD_ID, "VentureCraft - Gliders");
        add("itemGroup." + VCGliders.MOD_ID + ".main", "VentureCraft - Gliders");
        add("options.glider_perspective", "Glider Perspective");
        add("curios.identifier.glider", "Glider");

        add(GliderDamageSource.ZAP_EXPERIMENT, "&s was killed by a bad lightning experiment");
    }


    public void add(ResourceKey<DamageType> damageSource, String message) {
        add("death.attack." + damageSource.location().getPath(), message);
        add("death.attack." + damageSource.location().getPath() + ".player", message);
    }

    private void addSound(SoundEvent soundEvent, String subtitle) {
        add("subtitle." + VCGliders.MOD_ID + "." + soundEvent.getLocation().getPath(), subtitle);
    }
}
