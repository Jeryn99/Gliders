package net.venturecraft.gliders.util;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.phys.Vec3;
import net.threetag.palladiumcore.util.Platform;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.sound.SoundRegistry;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.network.MessagePOV;

import static net.venturecraft.gliders.common.item.GliderItem.*;

public class GliderUtil {
    public static boolean hasGliderEquipped(LivingEntity livingEntity) {
        return CuriosTrinketsUtil.getInstance().getFirstFoundGlider(livingEntity).getItem() instanceof GliderItem;
    }

    public static boolean isGliderActive(LivingEntity livingEntity) {
        ItemStack glider = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(livingEntity);
        if (glider == null) return false;
        if (glider.getItem() instanceof GliderItem) {
            return isGlidingEnabled(glider);
        }
        return false;
    }

    public static void onTickPlayerGlide(Level level, LivingEntity player) {
        ItemStack glider = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(player);
        boolean playerCanGlide = !GliderUtil.isPlayerOnGroundOrWater(player);
        boolean gliderCanGlide = isGlidingEnabled(glider);

        if (player instanceof Player player1) {
            playerCanGlide = playerCanGlide && !player1.getAbilities().flying;
        }

        if (playerCanGlide && gliderCanGlide) {

            player.resetFallDistance();

            // Handle Movement
            Vec3 m = player.getDeltaMovement();
            boolean hasSpeedMods = hasCopperUpgrade(glider) && hasBeenStruck(glider);

            if (!hasCopperUpgrade(glider) && level.isRainingAt(player.blockPosition())) {

                GliderData.get(player).ifPresent(gliderData -> {
                    gliderData.setLightningTimer(gliderData.lightningTimer() + 1);

                    if(gliderData.lightningTimer() == 1){
                        player.playSound(SoundRegistry.INCOMING_LIGHTNING.get());
                    }

                    if (player.level.random.nextInt(24) == 0 && gliderData.lightningTimer() > 200) {
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                        lightningBolt.setPos(player.getX(), player.getY(), player.getZ());
                        lightningBolt.setVisualOnly(false);
                        level.addFreshEntity(lightningBolt);
                    }
                });

                if (player.level.random.nextInt(24) == 0) {
                    for (int i = 0; i < 2; i++) {
                        level.addParticle(ParticleTypes.WAX_ON, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.2D, 1.0D, 0.0D);
                        level.addParticle(ParticleTypes.WAX_OFF, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.2D, 0.0D);
                        level.addParticle(ParticleTypes.WARPED_SPORE, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.0D, 0.0D);
                    }
                }

            } else {
               GliderData.get(player).ifPresent(gliderData -> gliderData.setLightningTimer(0));
            }

            if (player.tickCount % (player.level.dimension() == Level.NETHER ? 40 : 200) == 0) {
                if (player instanceof ServerPlayer serverPlayer) {

                    glider.setDamageValue(glider.getDamageValue() - (player.level.dimension() == Level.NETHER && !hasNetherUpgrade(glider) ? glider.getMaxDamage() / 2 : 1));
                    if (glider.getDamageValue() >= glider.getMaxDamage()) {
                        level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
                        level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
                        GliderItem.setBroken(glider, true);
                    }
                }
            }


            if (level.dimension() == Level.NETHER && !hasNetherUpgrade(glider)) {
                if (player.level.random.nextInt(24) == 0 && !player.isSilent()) {
                    player.level.playLocalSound(player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, SoundEvents.BLAZE_BURN, player.getSoundSource(), 1.0F + level.random.nextFloat(), level.random.nextFloat() * 0.7F + 0.3F, false);

                    for (int i = 0; i < 2; i++) {
                        level.addParticle(ParticleTypes.LARGE_SMOKE, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.2D, 1.0D, 0.0D);
                        level.addParticle(ParticleTypes.SMOKE, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.2D, 0.0D);
                        level.addParticle(ParticleTypes.LAVA, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.0D, 0.0D);
                    }
                }
            }

            if(checkUpdraft(player.blockPosition(), level, player)) return;


            // Particles
            float horizonalSpeed = (float) player.getDeltaMovement().horizontalDistance();
            if (isSpaceGlider(glider) && horizonalSpeed >= 0.01F) {

                if (!level.isClientSide()) {
                    for (int i = 0; i < 2; ++i) {
                        for (ServerPlayer serverplayer : Platform.getCurrentServer().getPlayerList().getPlayers()) {
                            ((ServerLevel) serverplayer.level).sendParticles(ParticleTypes.DRAGON_BREATH, player.getRandomX(0.5D), player.getY() + 2.5, player.getRandomZ(0.5D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }

            // Speed Modifications
            if (hasSpeedMods) {

                if (!level.isClientSide() && horizonalSpeed >= 0.01F) {
                    for (int i = 0; i < 2; ++i) {
                        for (ServerPlayer serverplayer : Platform.getCurrentServer().getPlayerList().getPlayers()) {
                            ((ServerLevel) serverplayer.level).sendParticles(ParticleTypes.GLOW, player.getRandomX(0.5D), player.getY() + 2.5, player.getRandomZ(0.5D), 1, 0.0D, 0.0D, 0.0D, 0.0D);
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

    public static boolean checkUpdraft(BlockPos playerPosition, Level world, LivingEntity player) {
        BlockPos updraftBlockPos = playerPosition.below(2);

        if (world.getBlockState(updraftBlockPos).is(VCGliderTags.UPDRAFT_BLOCKS)) {
            double playerY = player.getY();
            double updraftHeight = playerY + 20.0;

            if (playerY < updraftHeight) {
                double deltaY = Math.min(updraftHeight - playerY, 2.0);
                player.setDeltaMovement(player.getDeltaMovement().add(0, deltaY, 0));
                return true;
            } else {
                double deltaY = Math.max(updraftHeight - playerY, -0.08);
                player.setDeltaMovement(player.getDeltaMovement().add(0, deltaY, 0));
                return true;
            }
        }
        return false;
    }

    public static boolean isPlayerOnGroundOrWater(LivingEntity livingEntity) {
        return livingEntity.isOnGround() || livingEntity.isInWater() || livingEntity.isUnderWater() || livingEntity.isSwimming();
    }


    public static boolean isGlidingWithActiveGlider(LivingEntity livingEntity) {
        return hasGliderEquipped(livingEntity) && isGliderActive(livingEntity) && !livingEntity.isOnGround() && !livingEntity.isInWater();
    }
}
