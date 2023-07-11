package net.venturecraft.gliders.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.venturecraft.gliders.VCGliders;
import org.lwjgl.glfw.GLFW;

public class GliderKeybinds {

    public static KeyMapping ACTIVATE_GLIDER = new KeyMapping("Activate Glider", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, VCGliders.MOD_ID);


}
