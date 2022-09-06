package dev.mrsterner.nyctoplus.common.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import dev.mrsterner.nyctoplus.common.registry.NPSensorType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.VisibleLivingEntitiesCache;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.dynamic.GlobalPos;

import java.util.List;
import java.util.Optional;

public class LeshonBrain {
    private static final int HOME_CLOSE_ENOUGH_DISTANCE = 2;
    private static final int HOME_TOO_FAR_DISTANCE = 100;
    private static final int HOME_STROLL_AROUND_DISTANCE = 5;
    private static final List<SensorType<? extends Sensor<? super LeshonEntity>>> SENSORS =List.of(
            SensorType.NEAREST_PLAYERS,
            NPSensorType.LESHON_ENTITY_SENSOR,
            SensorType.HURT_BY);
    private static final List<MemoryModuleType<?>> MEMORIES = List.of(
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.HOME,
            MemoryModuleType.PACIFIED
    );

    public LeshonBrain() {
    }

    public static Brain<?> create(LeshonEntity leshonEntity, Dynamic<?> dynamic) {
        Brain.Profile<LeshonEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<LeshonEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(leshonEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<LeshonEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new StayAboveWaterTask(0.6f),
                        new LookAroundTask(45, 90),
                        new WanderAroundTask(),
                        new ForgetAngryAtTargetTask<>(),
                        new UpdateAttackTargetTask<>(LeshonBrain::getAttackTarget)
                )
        );
    }

    private static void addIdleActivities(Brain<LeshonEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, new UpdateAttackTargetTask<>(LeshonBrain::getAttackTarget)),
                        Pair.of(1, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(new StrollTask(0.6F), 2),
                                        Pair.of(new ConditionalTask<>(LeshonBrain::canWander, new GoTowardsLookTarget(0.6F, 3)), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                ))),
                        Pair.of(2, new GoToNearbyPositionTask(MemoryModuleType.HOME, 0.6f, HOME_CLOSE_ENOUGH_DISTANCE, HOME_TOO_FAR_DISTANCE)),
                        Pair.of(3, new GoToIfNearbyTask(MemoryModuleType.HOME, 0.6f, HOME_STROLL_AROUND_DISTANCE))

                )
        );
    }

    private static void addFightActivities(LeshonEntity leshonEntity, Brain<LeshonEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        new RangedApproachTask(1.0F),
                        new UpdateAttackTargetTask<>(LeshonBrain::getAttackTarget),
                        new FollowMobTask(mob -> isTarget(leshonEntity, mob), (float)leshonEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        new MeleeAttackTask(18)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    public static void updateActivities(LeshonEntity leshonEntity) {
        leshonEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        leshonEntity.setAttacking(leshonEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    private static Optional<? extends LivingEntity> getAttackTarget(LeshonEntity leshonEntity) {
        Brain<LeshonEntity> brain = leshonEntity.getBrain();
        if (brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)) {
            return brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
        }
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            VisibleLivingEntitiesCache visibleLivingEntitiesCache = leshonEntity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get();
            return visibleLivingEntitiesCache.method_38975(entity -> entity.getType() == EntityType.PLAYER && !entity.isSubmergedInWater());
        }
        return Optional.empty();
    }

    private static boolean isTarget(LeshonEntity leshonEntity, LivingEntity entity) {
        return leshonEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }

    private static boolean canWander(LivingEntity livingEntity) {
        return true;
    }

    public static void setCurrentPosAsHome(LeshonEntity leshonEntity) {
        GlobalPos globalPos = GlobalPos.create(leshonEntity.world.getRegistryKey(), leshonEntity.getBlockPos());
        leshonEntity.getBrain().remember(MemoryModuleType.HOME, globalPos);
    }
}