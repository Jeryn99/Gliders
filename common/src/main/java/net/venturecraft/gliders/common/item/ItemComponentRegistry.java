package net.venturecraft.gliders.common.item;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistryHolder;

import java.util.function.UnaryOperator;

import static net.venturecraft.gliders.VCGliders.MOD_ID;

public class ItemComponentRegistry {
    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES =
            DeferredRegister.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static RegistryHolder<DataComponentType<?>, DataComponentType<Boolean>> COPPER_UPGRADE = registerComponentType("copper_upgrade",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static RegistryHolder<DataComponentType<?>, DataComponentType<Boolean>> NETHER_UPGRADE = registerComponentType("nether_upgrade",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static RegistryHolder<DataComponentType<?>, DataComponentType<Boolean>> GLIDE = registerComponentType("glide",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static RegistryHolder<DataComponentType<?>, DataComponentType<Boolean>> BROKEN = registerComponentType("broken",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static RegistryHolder<DataComponentType<?>, DataComponentType<Boolean>> STRUCK = registerComponentType("struck",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    private static <T> RegistryHolder<DataComponentType<?>, DataComponentType<T>> registerComponentType(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return COMPONENT_TYPES.register(id, () -> (builderOperator.apply(DataComponentType.builder())).build());
    }
}
