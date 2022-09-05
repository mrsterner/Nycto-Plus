package dev.mrsterner.nyctoplus.common.ai;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestLivingEntitiesSensor;
import net.minecraft.entity.mob.warden.WardenEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LeshonEntitySensor extends NearestLivingEntitiesSensor<LeshonEntity> {

    public LeshonEntitySensor(){

    }

    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.copyOf(Iterables.concat(super.getOutputMemoryModules(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
    }

    protected void sense(ServerWorld serverWorld, LeshonEntity leshonEntity) {
        super.sense(serverWorld, leshonEntity);
        getClosestEntity(leshonEntity, (livingEntity) -> {
            return livingEntity.getType() == EntityType.PLAYER;
        }).or(() -> {
            return getClosestEntity(leshonEntity, (livingEntity) -> {
                return livingEntity.getType() != EntityType.PLAYER;
            });
        }).ifPresentOrElse((livingEntity) -> {
            leshonEntity.getBrain().remember(MemoryModuleType.NEAREST_ATTACKABLE, livingEntity);
        }, () -> {
            leshonEntity.getBrain().forget(MemoryModuleType.NEAREST_ATTACKABLE);
        });
    }

    private static Optional<LivingEntity> getClosestEntity(LeshonEntity leshonEntity, Predicate<LivingEntity> entityPredicate) {
        Stream<LivingEntity> var10000 = leshonEntity.getBrain().getOptionalMemory(MemoryModuleType.MOBS).stream().flatMap(Collection::stream);
        Objects.requireNonNull(leshonEntity);
        return var10000.filter(leshonEntity::isEnemy).filter(entityPredicate).findFirst();
    }

    protected int horizontalRadius() {
        return 24;
    }

    protected int verticalRadius() {
        return 24;
    }
}
