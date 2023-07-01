package net.venturecraft.gliders.util;

import net.venturecraft.gliders.VCGliders;

public class ModConstants {

    // Tooltip
    public static final String INSTALLED_UPGRADES = createToolTip("installed_upgrades");
    public static final String NETHER_UPGRADE = createToolTip("nether_upgrade");
    public static final String COPPER_UPGRADE = createToolTip("copper_upgrade");

    // Messages


    public static String createToolTip(String id) {
        return "tooltip." + VCGliders.MOD_ID + "." + id;
    }

    public static String createMessage(String id) {
        return "message." + VCGliders.MOD_ID + "." + id;
    }

}
