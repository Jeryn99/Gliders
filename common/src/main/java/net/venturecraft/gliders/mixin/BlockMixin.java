package net.venturecraft.gliders.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.venturecraft.gliders.util.GliderUtil;
import net.venturecraft.gliders.util.VCGliderTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "animateTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V")
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if(GliderUtil.isGlidingWithActiveGlider(Minecraft.getInstance().player) && state.is(VCGliderTags.UPDRAFT_BLOCKS)) {
            for (int i = 0; i < 3; ++i) {
                double xCoord = (double) pos.getX() + random.nextDouble();
                double yCoord = (double) pos.getY() + random.nextDouble() * 0.5 + 0.5;
                double zCoord = (double) pos.getZ() + random.nextDouble();
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, xCoord, yCoord, zCoord, 0.0, 0.005, 0.0);
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, xCoord, yCoord + 1, zCoord, 0.0, 0.005, 0.0);
            }
        }
    }
}
