package net.venturecraft.gliders.data.forge;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.venturecraft.gliders.VCGliders;
import net.venturecraft.gliders.data.GliderData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = VCGliders.MOD_ID)
public class GliderDataImpl implements ICapabilitySerializable<CompoundTag> {

    public static Capability<GliderData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent e) {
        e.register(GliderData.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof Player player) {
            e.addCapability(VCGliders.id("player_data"), new GliderDataImpl(player));
        }
    }

    public final GliderData capability;
    public final LazyOptional<GliderData> lazyOptional;

    public GliderDataImpl(Player player) {
        this.capability = new GliderData(player);
        this.lazyOptional = LazyOptional.of(() -> this.capability);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        return capability == PLAYER_DATA ? this.lazyOptional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.capability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag arg) {
        this.capability.deserializeNBT(arg);
    }

    public static Optional<GliderData> get(LivingEntity player) {
        return player.getCapability(PLAYER_DATA).resolve();
    }
}