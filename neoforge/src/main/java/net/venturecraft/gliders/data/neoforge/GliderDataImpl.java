package net.venturecraft.gliders.data.neoforge;


import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.data.neoforge.VCComponents;

import java.util.Objects;
import java.util.Optional;

public class GliderDataImpl extends GliderData {

    public GliderDataImpl(Player livingEntity) {
        super(livingEntity);
    }

    public static Optional<GliderData> get(Player player) {
        try {
            GliderData gliderData = new GliderData(player);
            CompoundTag data = player.getData(VCComponents.PLAYER_DATA);
            gliderData.deserializeNBT(data);
            return Optional.of(gliderData);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void readFromNbt(CompoundTag tag, HolderLookup.Provider provider) {
        this.deserializeNBT(tag);
    }

    public void writeToNbt(CompoundTag tag, HolderLookup.Provider provider) {
        CompoundTag nbt = this.serializeNBT();
        for (String key : nbt.getAllKeys()) {
            tag.put(key, Objects.requireNonNull(nbt.get(key)));
        }
    }
}