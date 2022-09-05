package dev.mrsterner.nyctoplus.common.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import dev.mrsterner.nyctoplus.common.registry.NPSensorType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.WardenEntitySensor;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PiglinBruteBrain;
import net.minecraft.entity.mob.warden.WardenBrain;
import net.minecraft.entity.mob.warden.WardenEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.function.Supplier;

public class LeshonBrain {

    private static final List<SensorType<? extends Sensor<? super LeshonEntity>>> SENSORS;
    private static final List<MemoryModuleType<?>> MEMORIES;

    public LeshonBrain() {
    }

    public static Brain<?> create(LeshonEntity leshonEntity, Dynamic<?> dynamic) {
        Brain.Profile<LeshonEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<LeshonEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<LeshonEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new StayAboveWaterTask(0.8F),
                new LookAroundTask(45, 90)));
    }

    private static void addIdleActivities(Brain<LeshonEntity> brain) {

    }

    public static void updateActivities(LeshonEntity leshonEntity) {

    }

    private static boolean isTarget(LeshonEntity leshonEntity, LivingEntity entity) {
        return leshonEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter((targetedEntity) -> targetedEntity == entity).isPresent();
    }

    public static void updateActivities(WardenEntity warden) {
        warden.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }



    static {
        SENSORS = List.of(
                SensorType.NEAREST_PLAYERS,
                NPSensorType.LESHON_ENTITY_SENSOR);
        MEMORIES = List.of(
                MemoryModuleType.MOBS,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.NEAREST_VISIBLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.NEAREST_ATTACKABLE);

    }



}
