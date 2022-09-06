package dev.mrsterner.nyctoplus.common.entity.ai;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestLivingEntitiesSensor;
import net.minecraft.server.world.ServerWorld;

import java.util.*;
import java.util.function.Predicate;

public class LeshonEntitySensor extends NearestLivingEntitiesSensor<LeshonEntity> {

    public LeshonEntitySensor(){

    }

    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.copyOf(Iterables.concat(super.getOutputMemoryModules(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
    }

    protected void sense(ServerWorld serverWorld, LeshonEntity leshonEntity) {
        super.sense(serverWorld, leshonEntity);
        getClosestEntity(leshonEntity, (livingEntity) -> livingEntity.getType() == EntityType.PLAYER).or(() -> getClosestEntity(leshonEntity, (livingEntity) -> livingEntity.getType() != EntityType.PLAYER)).ifPresentOrElse((livingEntity) -> {
            leshonEntity.getBrain().remember(MemoryModuleType.NEAREST_ATTACKABLE, livingEntity);
        }, () -> {
            leshonEntity.getBrain().forget(MemoryModuleType.NEAREST_ATTACKABLE);
        });
    }

    private static Optional<LivingEntity> getClosestEntity(LeshonEntity leshonEntity, Predicate<LivingEntity> entityPredicate) {
        return leshonEntity.getBrain()
                .getOptionalMemory(MemoryModuleType.MOBS)
                .stream()
                .flatMap(Collection::stream)
                .filter(leshonEntity::isEnemy)
                .filter(entityPredicate)
                .findFirst();
    }

    protected int horizontalRadius() {
        return 24;
    }

    protected int verticalRadius() {
        return 24;
    }
}
