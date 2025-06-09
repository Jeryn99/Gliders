package net.venturecraft.gliders.data.fabric;


import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.data.GliderData;
import org.ladysnake.cca.api.v3.component.ComponentV3;

import java.util.Objects;
import java.util.Optional;

public class GliderDataImpl extends GliderData implements ComponentV3 {

    public GliderDataImpl(Player livingEntity) {
        super(livingEntity);
    }

    public static Optional<GliderData> get(LivingEntity player) {
        try {
            return Optional.of(VCComponents.PLAYER_DATA.get(player));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider provider) {
        this.deserializeNBT(tag);
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider provider) {
        CompoundTag nbt = this.serializeNBT();
        for (String key : nbt.getAllKeys()) {
            tag.put(key, Objects.requireNonNull(nbt.get(key)));
        }
    }
}