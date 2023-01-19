package net.venturecraft.gliders.forge.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.common.item.ItemRegistry;

public class EnglishLangProvider extends LanguageProvider {

    public EnglishLangProvider(DataGenerator gen) {
        super(gen, VCGliders.MOD_ID, "en_us");
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

    }
}
