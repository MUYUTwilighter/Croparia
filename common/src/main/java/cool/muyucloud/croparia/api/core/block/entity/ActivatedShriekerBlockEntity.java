package cool.muyucloud.croparia.api.core.block.entity;

import com.mojang.logging.LogUtils;
import cool.muyucloud.croparia.api.core.block.ActivatedShrieker;
import cool.muyucloud.croparia.registry.BlockEntities;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.OptionalInt;

/**
 * Copy from {@link SculkShriekerBlockEntity}
 */
public class ActivatedShriekerBlockEntity extends BlockEntity implements GameEventListener.Provider<VibrationSystem.Listener>, VibrationSystem {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Int2ObjectMap<SoundEvent> SOUND_BY_LEVEL = Util.make(new Int2ObjectOpenHashMap<>(), map -> {
        map.put(1, SoundEvents.WARDEN_NEARBY_CLOSE);
        map.put(2, SoundEvents.WARDEN_NEARBY_CLOSER);
        map.put(3, SoundEvents.WARDEN_NEARBY_CLOSEST);
        map.put(4, SoundEvents.WARDEN_LISTENING_ANGRY);
    });
    private final VibrationSystem.User vibrationUser = new ActivatedShriekerBlockEntity.VibrationUser();
    private VibrationSystem.Data vibrationData = new VibrationSystem.Data();
    private final VibrationSystem.Listener vibrationListener = new VibrationSystem.Listener(this);

    public ActivatedShriekerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.ACTIVATED_SHRIEKER.get(), blockPos, blockState);
    }

    public VibrationSystem.@NotNull Data getVibrationData() {
        return this.vibrationData;
    }

    public VibrationSystem.@NotNull User getVibrationUser() {
        return this.vibrationUser;
    }

    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);

        RegistryOps<Tag> registryOps = provider.createSerializationContext(NbtOps.INSTANCE);
        if (compoundTag.contains("listener", 10)) {
            Data.CODEC.parse(registryOps, compoundTag.getCompound("listener")).resultOrPartial(
                string -> LOGGER.error("Failed to parse vibration listener for Sculk Shrieker: '{}'", string)
            ).ifPresent((data) -> this.vibrationData = data);
        }

    }

    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        RegistryOps<Tag> registryOps = provider.createSerializationContext(NbtOps.INSTANCE);
        Data.CODEC.encodeStart(registryOps, this.vibrationData).resultOrPartial(
            string -> LOGGER.error("Failed to encode vibration listener for Sculk Shrieker: '{}'", string)
        ).ifPresent((tag) -> compoundTag.put("listener", tag));
    }

    @Nullable
    public static ServerPlayer tryGetPlayer(@Nullable Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer;
        } else {
            if (entity != null) {
                LivingEntity passenger = entity.getControllingPassenger();
                if (passenger instanceof ServerPlayer serverPlayer) {
                    return serverPlayer;
                }
            }
            Entity owner;
            if (entity instanceof Projectile projectile) {
                owner = projectile.getOwner();
                if (owner instanceof ServerPlayer serverPlayer) {
                    return serverPlayer;
                }
            }
            if (entity instanceof ItemEntity itemEntity) {
                owner = itemEntity.getOwner();
                if (owner instanceof ServerPlayer serverPlayer) {
                    return serverPlayer;
                }
            }
            return null;
        }
    }

    public void tryShriek(ServerLevel serverLevel, @Nullable ServerPlayer serverPlayer) {
        if (serverPlayer != null) {
            BlockState blockState = this.getBlockState();
            if (!(Boolean) blockState.getValue(ActivatedShrieker.SHRIEKING)) {
                if (!this.canRespond(serverLevel) || this.tryToWarn(serverLevel, serverPlayer)) {
                    this.shriek(serverLevel, serverPlayer);
                }
            }
        }
    }

    private boolean tryToWarn(ServerLevel serverLevel, ServerPlayer serverPlayer) {
        OptionalInt optionalInt = WardenSpawnTracker.tryWarn(serverLevel, this.getBlockPos(), serverPlayer);
        return optionalInt.isPresent();
    }

    private void shriek(ServerLevel serverLevel, @Nullable Entity entity) {
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getBlockState();
        serverLevel.setBlock(blockPos, blockState.setValue(ActivatedShrieker.SHRIEKING, true), 2);
        serverLevel.scheduleTick(blockPos, blockState.getBlock(), 90);
        serverLevel.levelEvent(3007, blockPos, 0);
        serverLevel.gameEvent(GameEvent.SHRIEK, blockPos, GameEvent.Context.of(entity));
    }

    private boolean canRespond(ServerLevel serverLevel) {
        return this.getBlockState().getValue(ActivatedShrieker.CAN_SUMMON) && serverLevel.getDifficulty() != Difficulty.PEACEFUL && serverLevel.getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING);
    }

    public void tryRespond(ServerLevel serverLevel) {
        if (this.canRespond(serverLevel)) {
            if (!this.trySummonWarden(serverLevel)) {
                this.playWardenReplySound(serverLevel);
            }

            Warden.applyDarknessAround(serverLevel, Vec3.atCenterOf(this.getBlockPos()), null, 40);
        }

    }

    private void playWardenReplySound(Level level) {
        SoundEvent soundEvent = SOUND_BY_LEVEL.get(4);
        if (soundEvent != null) {
            BlockPos blockPos = this.getBlockPos();
            int i = blockPos.getX() + Mth.randomBetweenInclusive(level.random, -10, 10);
            int j = blockPos.getY() + Mth.randomBetweenInclusive(level.random, -10, 10);
            int k = blockPos.getZ() + Mth.randomBetweenInclusive(level.random, -10, 10);
            level.playSound(null, i, j, k, soundEvent, SoundSource.HOSTILE, 5.0F, 1.0F);
        }

    }

    private boolean trySummonWarden(ServerLevel serverLevel) {
        return SpawnUtil.trySpawnMob(EntityType.WARDEN, EntitySpawnReason.TRIGGERED, serverLevel, this.getBlockPos(), 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER, false).isPresent();
    }

    public VibrationSystem.@NotNull Listener getListener() {
        return this.vibrationListener;
    }

    private class VibrationUser implements VibrationSystem.User {
        private static final int LISTENER_RADIUS = 8;
        private final PositionSource positionSource;

        public VibrationUser() {
            this.positionSource = new BlockPositionSource(ActivatedShriekerBlockEntity.this.worldPosition);
        }

        public int getListenerRadius() {
            return LISTENER_RADIUS;
        }

        public @NotNull PositionSource getPositionSource() {
            return this.positionSource;
        }

        public @NotNull TagKey<GameEvent> getListenableEvents() {
            return GameEventTags.SHRIEKER_CAN_LISTEN;
        }

        public boolean canReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, Holder<GameEvent> holder, GameEvent.Context context) {
            return !(Boolean) ActivatedShriekerBlockEntity.this.getBlockState().getValue(ActivatedShrieker.SHRIEKING) && ActivatedShriekerBlockEntity.tryGetPlayer(context.sourceEntity()) != null;
        }

        public void onReceiveVibration(ServerLevel serverLevel, BlockPos blockPos, Holder<GameEvent> holder, @Nullable Entity entity, @Nullable Entity entity2, float f) {
            ActivatedShriekerBlockEntity.this.tryShriek(serverLevel, ActivatedShriekerBlockEntity.tryGetPlayer(entity2 != null ? entity2 : entity));
        }

        public void onDataChanged() {
            ActivatedShriekerBlockEntity.this.setChanged();
        }

        public boolean requiresAdjacentChunksToBeTicking() {
            return true;
        }
    }
}
