package net.venturecraft.gliders.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.phys.Vec3;
import net.threetag.palladiumcore.util.Platform;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.sound.SoundRegistry;
import net.venturecraft.gliders.network.MessagePOV;

import static net.venturecraft.gliders.common.item.GliderItem.*;

public class GliderUtil {
    public static boolean hasGliderEquipped(LivingEntity livingEntity) {
        return CuriosTrinketsUtil.getInstance().getFirstGliderInSlot(livingEntity, CuriosTrinketsUtil.BACK.identifier()).getItem() instanceof GliderItem;
    }

    public static boolean isGliderActive(LivingEntity livingEntity) {
        ItemStack glider = CuriosTrinketsUtil.getInstance().getFirstGliderInSlot(livingEntity, CuriosTrinketsUtil.BACK.identifier());
        if (glider == null) return false;
        if (glider.getItem() instanceof GliderItem) {
            return isGlidingEnabled(glider);
        }
        return false;
    }

    public static void onTickPlayerGlide(Level level, LivingEntity player) {
        ItemStack glider = CuriosTrinketsUtil.getInstance().getFirstGliderInSlot(player, CuriosTrinketsUtil.BACK.identifier());
        boolean playerCanGlide = !GliderUtil.isPlayerOnGroundOrWater(player);
        boolean gliderCanGlide = isGlidingEnabled(glider);

        if(player instanceof Player player1){
            playerCanGlide = playerCanGlide && !player1.getAbilities().flying;
        }

        if (playerCanGlide && gliderCanGlide) {

            player.resetFallDistance();

            // Handle Movement
            Vec3 m = player.getDeltaMovement();
            boolean hasSpeedMods = hasCopperUpgrade(glider) && hasBeenStruck(glider);

            if (!hasCopperUpgrade(glider) && level.isRainingAt(player.blockPosition())) {
                int lightningTime = getLightningCounter(glider);
                setLightningCounter(glider, lightningTime + 1);

                if (lightningTime == 1) {
                    player.playSound(SoundRegistry.INCOMING_LIGHTNING.get());
                }

                if (player.level().random.nextInt(24) == 0) {
                    for (int i = 0; i < 2; i++) {
                        level.addParticle(ParticleTypes.WAX_ON, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.2D, 1.0D, 0.0D);
                        level.addParticle(ParticleTypes.WAX_OFF, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.2D, 0.0D);
                        level.addParticle(ParticleTypes.WARPED_SPORE, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.0D, 0.0D);
                    }
                }

                if (player.level().random.nextInt(24) == 0 && lightningTime > 200) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt.setPos(player.getX(), player.getY(), player.getZ());
                    lightningBolt.setVisualOnly(false);
                    level.addFreshEntity(lightningBolt);
                }
            } else {
                setLightningCounter(glider, 0);
            }

            if (player.tickCount % 200 == 0) {
                if (player instanceof ServerPlayer serverPlayer) {
                    glider.hurtAndBreak(player.level().dimension() == Level.NETHER && !hasNetherUpgrade(glider) ? glider.getMaxDamage() / 2 : 1, player, player1 -> {
                        level.playSound(null, player1.getX(), player1.getY(), player1.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
                        level.playSound(null, player1.getX(), player1.getY(), player1.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));

                        glider.setDamageValue(0);
                        GliderItem.setBroken(glider, true);
                    });
                }
            }


            if (level.dimension() == Level.NETHER && !hasNetherUpgrade(glider)) {
                if (player.level().random.nextInt(24) == 0 && !player.isSilent()) {
                    player.level().playLocalSound(player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, SoundEvents.BLAZE_BURN, player.getSoundSource(), 1.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F, false);

                    for (int i = 0; i < 2; i++) {
                        level.addParticle(ParticleTypes.LARGE_SMOKE, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.2D, 1.0D, 0.0D);
                        level.addParticle(ParticleTypes.SMOKE, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.2D, 0.0D);
                        level.addParticle(ParticleTypes.LAVA, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.0D, 0.0D);
                    }
                }
            }

            if (level.getBlockState(player.blockPosition().below(2)).getBlock() instanceof FireBlock) {
                player.setDeltaMovement(player.getDeltaMovement().add(0, 2, 0));
                return;
            }


            // Particles
            float horizonalSpeed = (float) player.getDeltaMovement().horizontalDistance();
            if (isSpaceGlider(glider) && horizonalSpeed >= 0.01F) {

                if (!level.isClientSide()) {
                    for (int i = 0; i < 2; ++i) {
                        for (ServerPlayer serverplayer : Platform.getCurrentServer().getPlayerList().getPlayers()) {
                            ((ServerLevel) serverplayer.level()).sendParticles(ParticleTypes.DRAGON_BREATH, player.getRandomX(0.5D), player.getY() + 2.5, player.getRandomZ(0.5D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }

            // Speed Modifications
            if (hasSpeedMods) {

                if (!level.isClientSide() && horizonalSpeed >= 0.01F) {
                    for (int i = 0; i < 2; ++i) {
                        for (ServerPlayer serverplayer : Platform.getCurrentServer().getPlayerList().getPlayers()) {
                            ((ServerLevel) serverplayer.level()).sendParticles(ParticleTypes.GLOW, player.getRandomX(0.5D), player.getY() + 2.5, player.getRandomZ(0.5D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }

            if (m.y < -0.05)
                player.setDeltaMovement(new Vec3(m.x, -0.05, m.z));
            return;
        }

        if (GliderUtil.isPlayerOnGroundOrWater(player)) {

            // Reset Gliding status when on Ground
            setGlide(glider, false);
            setStruck(glider, false);
            if (player instanceof ServerPlayer serverPlayer) {
                new MessagePOV("").send(serverPlayer);
            }
        }
    }

    public static boolean isPlayerOnGroundOrWater(LivingEntity livingEntity) {
        return livingEntity.onGround() || livingEntity.isInWater() || livingEntity.isUnderWater() || livingEntity.isSwimming();
    }


    public static boolean isGlidingWithActiveGlider(LivingEntity livingEntity) {
        return hasGliderEquipped(livingEntity) && isGliderActive(livingEntity) && !livingEntity.onGround() && !livingEntity.isInWater();
    }
}
