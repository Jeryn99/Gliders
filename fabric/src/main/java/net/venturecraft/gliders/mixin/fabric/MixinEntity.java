package net.venturecraft.gliders.mixin.fabric;


import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.util.GliderUtil;
import net.venturecraft.gliders.common.GliderDamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "thunderHit(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LightningBolt;)V", at = @At("HEAD"))
    private void gliders$thunderHit(ServerLevel world, LightningBolt lightning, CallbackInfo ci) {
        Entity self = (Entity) (Object) this;

        if (!(self instanceof ServerPlayer player)) return;

        var stack = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(player);

        boolean hasCopperMod = GliderItem.hasCopperUpgrade(stack);
        boolean isGliding = GliderUtil.isGlidingWithActiveGlider(player);

        if (!hasCopperMod && isGliding) {
            GliderItem.setBroken(stack, true);
            return;
        }

        if (hasCopperMod && isGliding) {
            GliderItem.setStruck(stack, true);
            if (GliderItem.hasBeenStruck(stack)) {
                player.hurt(GliderDamageSource.getSource(world, GliderDamageSource.ZAP_EXPERIMENT), 2.0F);
            }
        }
    }
}
