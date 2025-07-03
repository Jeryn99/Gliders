package net.venturecraft.gliders.common.item;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.venturecraft.gliders.registry.DeferredRegistry;
import net.venturecraft.gliders.registry.RegistrySupplier;

import java.util.function.UnaryOperator;

import static net.venturecraft.gliders.VCGliders.MOD_ID;

public class ItemComponentRegistry {
    public static final DeferredRegistry<DataComponentType<?>> COMPONENT_TYPES =
            DeferredRegistry.create(MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static RegistrySupplier<DataComponentType<Boolean>> COPPER_UPGRADE = registerComponentType("copper_upgrade",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static RegistrySupplier<DataComponentType<Boolean>> NETHER_UPGRADE = registerComponentType("nether_upgrade",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static RegistrySupplier<DataComponentType<Boolean>> GLIDE = registerComponentType("glide",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static RegistrySupplier<DataComponentType<Boolean>> BROKEN = registerComponentType("broken",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static RegistrySupplier<DataComponentType<Boolean>> STRUCK = registerComponentType("struck",
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    private static <T> RegistrySupplier<DataComponentType<T>> registerComponentType(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return COMPONENT_TYPES.register(id, () -> (builderOperator.apply(DataComponentType.builder())).build());
    }
}
