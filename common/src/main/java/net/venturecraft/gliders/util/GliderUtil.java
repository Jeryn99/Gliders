package net.venturecraft.gliders.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.venturecraft.gliders.common.compat.trinket.CuriosTrinketsUtil;
import net.venturecraft.gliders.common.item.GliderItem;
import net.venturecraft.gliders.common.sound.SoundRegistry;
import net.venturecraft.gliders.data.GliderData;
import net.venturecraft.gliders.network.MessagePOV;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

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

    public static boolean canDeployHere(LocalPlayer localPlayer) {
        boolean isAir = localPlayer.level.getBlockState(localPlayer.blockPosition().below(2)).isAir() && localPlayer.level.getBlockState(localPlayer.blockPosition().below()).isAir();
        boolean updraftAround = nearUpdraft(localPlayer);
        boolean isUpdraft = localPlayer.level.getBlockState(localPlayer.blockPosition().below()).is(VCGliderTags.UPDRAFT_BLOCKS);
        return isUpdraft || isAir || updraftAround || localPlayer.fallDistance > 2;
    }

    public static boolean nearUpdraft(LivingEntity localPlayer) {
        for (Iterator<BlockPos> iterator = BlockPos.withinManhattanStream(localPlayer.blockPosition(), 2, 3, 2).iterator(); iterator.hasNext(); ) {
            BlockPos pos = iterator.next();
            BlockState blockState = localPlayer.level.getBlockState(pos);
            if (blockState.is(VCGliderTags.UPDRAFT_BLOCKS)) {
                return true;
            }
        }
        return false;
    }

    public static void onTickPlayerGlide(Level level, LivingEntity player) {
        ItemStack glider = CuriosTrinketsUtil.getInstance().getFirstFoundGlider(player);
        boolean playerCanGlide = !GliderUtil.isFlyingBlocked(player);
        boolean gliderCanGlide = isGlidingEnabled(glider);

        if (player instanceof Player player1) {
            playerCanGlide = playerCanGlide && !player1.getAbilities().flying;
        }

        if (playerCanGlide && gliderCanGlide) {

            player.resetFallDistance();

            // Handle Movement
            Vec3 m = player.getDeltaMovement();
            boolean hasSpeedMods = hasCopperUpgrade(glider) && hasBeenStruck(glider);

            lightningLogic(level, player, glider);

            if (player.tickCount % (player.level.dimension() == Level.NETHER ? 40 : 100) == 0) {
                if (player instanceof ServerPlayer serverPlayer) {

                    int damageAmount = player.level.dimension() == Level.NETHER && !hasNetherUpgrade(glider) ? glider.getMaxDamage() / 2 : 1;
                    glider.setDamageValue(glider.getDamageValue() + damageAmount);
                    if (glider.getDamageValue() >= glider.getMaxDamage()) {
                        level.playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
                        GliderItem.setBroken(glider, true);

                        if (player.level.dimension() != Level.NETHER) {
                            glider.setCount(0);
                        }
                    }
                }
            }


            handleNetherLogic(level, player, glider);

            if (checkUpdraft(player.blockPosition(), level, player)) return;


            // Particles
            float horizonalSpeed = (float) player.getDeltaMovement().horizontalDistance();
            if (horizonalSpeed >= 0.01F) {

                // Space Glider
                if (isSpaceGlider(glider)) {
                    for (int i = 0; i < 2; ++i) {
                        level.addParticle(ParticleTypes.DRAGON_BREATH, player.getRandomX(0.5D), player.getY() + 2.5, player.getRandomZ(0.5D), 1, 0.0D, 0.0D);
                    }
                }

                // Speed Modifications
                if (hasSpeedMods) {
                    level.addParticle(ParticleTypes.WITCH, player.getRandomX(0.5D), player.getY() + 2.5, player.getRandomZ(0.5D), 0.5, 0.0D, 0.0D);
                }
            }


            if (m.y < -0.05)
                player.setDeltaMovement(new Vec3(m.x, -0.05, m.z));
            return;
        }

        if (GliderUtil.isFlyingBlocked(player)) {

            // Reset Gliding status when on Ground
            setGlide(glider, false);
            setStruck(glider, false);
            if (player instanceof ServerPlayer serverPlayer) {
                new MessagePOV("").send(serverPlayer);
            }
        }
    }

    private static void handleNetherLogic(Level level, LivingEntity player, ItemStack glider) {
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
    }

    private static void lightningLogic(Level level, LivingEntity player, ItemStack glider) {
        if (level.isRainingAt(player.blockPosition())) {

            GliderData.get(player).ifPresent(gliderData -> {
                gliderData.setLightningTimer(gliderData.lightningTimer() + 1);

                if (gliderData.lightningTimer() == 1) {
                    player.playSound(SoundRegistry.INCOMING_LIGHTNING.get());
                }

                if (player.level.random.nextInt(24) == 0 && gliderData.lightningTimer() > 200 && !hasBeenStruck(glider)) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                    lightningBolt.setPos(player.getX(), player.getY(), player.getZ());
                    lightningBolt.setVisualOnly(false);
                    level.addFreshEntity(lightningBolt);
                }
            });

            if (player.level.random.nextInt(24) == 0 && !hasCopperUpgrade(glider)) {
                for (int i = 0; i < 2; i++) {
                    level.addParticle(ParticleTypes.WAX_ON, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.2D, 1.0D, 0.0D);
                    level.addParticle(ParticleTypes.WAX_OFF, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.2D, 0.0D);
                    level.addParticle(ParticleTypes.WARPED_SPORE, player.getRandomX(0.5), player.getY() + 2.5D, player.getRandomZ(0.5), 0.0D, 0.0D, 0.0D);
                }
            }

        } else {
           GliderData.get(player).ifPresent(gliderData -> gliderData.setLightningTimer(0));
        }
    }


    public static boolean checkUpdraft(BlockPos playerPosition, Level world, LivingEntity player) {
        AABB boundingBox = player.getBoundingBox().contract(2, 20, 2);
        List<BlockState> blocks = world.getBlockStatesIfLoaded(boundingBox).toList();
        Stream<BlockState> filteredBlocks = blocks.stream().filter(blockState -> blockState.is(VCGliderTags.UPDRAFT_BLOCKS));
        if (filteredBlocks.toList().size() > 0 || GliderUtil.nearUpdraft(player)) {
            player.setDeltaMovement(0, 0.5, 0);
            return true;
        }
        return false;
    }

    public static boolean isFlyingBlocked(LivingEntity livingEntity) {
        return livingEntity.isOnGround() || livingEntity.isInWater() || livingEntity.isUnderWater() || livingEntity.isSwimming() || livingEntity.isFallFlying();
    }


    public static boolean isGlidingWithActiveGlider(LivingEntity livingEntity) {
        return hasGliderEquipped(livingEntity) && isGliderActive(livingEntity) && !livingEntity.isOnGround() && !livingEntity.isInWater();
    }

    public static void updraftParticles(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (GliderUtil.isGlidingWithActiveGlider(Minecraft.getInstance().player) && state.is(VCGliderTags.UPDRAFT_BLOCKS)) {
            level.addAlwaysVisibleParticle(ParticleTypes.SNOWFLAKE, true, (double) pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble(), (double) pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double) (random.nextBoolean() ? 1 : -1), 0.0, 1, 0.0);
        }
    }
}
