package net.venturecraft.gliders.data.fabric;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.ResourceLocation;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import net.venturecraft.gliders.VCGliders;

import static net.venturecraft.gliders.VCGliders.MOD_ID;

public class VCAttachments {

    public static final AttachmentType<Integer> LIGHTNING_TIMER = AttachmentRegistry.createPersistent(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "lightning_timer"), Codec.INT
    );
    public static final AttachmentType<Boolean> IS_GLIDING = AttachmentRegistry.createPersistent(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "is_gliding"), Codec.BOOL
    );
}