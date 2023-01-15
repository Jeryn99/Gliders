package net.venturecraft.gliders.util;

import net.venturecraft.gliders.VCGliders;

public class ModConstants {

    // Tooltip
    public static final String INSTALLED_MODS = createToolTip("installed_mods");
    public static final String COPPER_MOD = createToolTip("copper_mod");

    // Messages


    public static String createToolTip(String id){
        return "tooltip." + VCGliders.MOD_ID + "." + id;
    }

    public static String createMessage(String id){
        return "message." + VCGliders.MOD_ID + "." + id;
    }

}
