package net.venturecraft.gliders.data.neoforge;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static net.venturecraft.gliders.VCGliders.MOD_ID;

public class VCAttachments {

    static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
    public static final Supplier<AttachmentType<CompoundTag>> PLAYER_DATA = ATTACHMENT_TYPES.register(
            "glider_data", () -> AttachmentType.builder(CompoundTag::new).serialize(CompoundTag.CODEC).build()
    );

    public static final Supplier<AttachmentType<Integer>> LIGHTNING_TIMER = ATTACHMENT_TYPES.register(
            "lightning_timer", () -> AttachmentType.builder(()->0).serialize(Codec.INT).build()
    );

    public static final Supplier<AttachmentType<Boolean>> IS_GLIDING = ATTACHMENT_TYPES.register(
            "is_gliding", () -> AttachmentType.builder(()->false).serialize(Codec.BOOL).build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}