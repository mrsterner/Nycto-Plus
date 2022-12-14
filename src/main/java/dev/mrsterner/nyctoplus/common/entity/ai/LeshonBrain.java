package dev.mrsterner.nyctoplus.common.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import dev.mrsterner.nyctoplus.common.registry.NPSensorTypes;
import dev.mrsterner.nyctoplus.mixin.BlockMixin;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.unmapped.C_rcqaryar;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;

public class LeshonBrain {
    private static final int HOME_CLOSE_ENOUGH_DISTANCE = 4;
    private static final int HOME_TOO_FAR_DISTANCE = 100;
    private static final int HOME_STROLL_AROUND_DISTANCE = 6;
    private static final UniformIntProvider AVOID_MEMORY_DURATION = TimeHelper.betweenSeconds(5, 20);
    private static final List<SensorType<? extends Sensor<? super LeshonEntity>>> SENSORS =List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            NPSensorTypes.LESHON_SPECIFIC_SENSOR);
    private static final List<MemoryModuleType<?>> MEMORIES = List.of(
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.HOME,
            MemoryModuleType.PACIFIED,
            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.AVOID_TARGET
    );

    public LeshonBrain() {
    }

    /**
     * Initializes the brain when the entity is made.
     * Activities get re-added to the Brain object each time. This means they can differ per entity instance, if we want them to!
     * Only one non-core Activity can run at once. When an Activity is run, it cycles through each prioritized Behavior and tries to run them all.
     */
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

    /**
     * These activities are always active. So, expect them to run every tick.
     */
    private static void addCoreActivities(Brain<LeshonEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new StayAboveWaterTask(0.6f), //Make the leshon not sink and die
                        new LookAroundTask(45, 90), //Look around is nice
                        new WanderAroundTask(), //Wandering
						UpdateAttackTargetTask.m_cstouyov(LeshonBrain::getAttackTarget)//Update targeted entity(player) for the fightingActivities
                )
        );
    }

    /**
     * These will run whenever we don't have something better to do. Essentially walk and swim randomly, or do nothing.
     */
    private static void addIdleActivities(Brain<LeshonEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0 , GoToRememberedPositionTask.toBlock(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true)),
                        Pair.of(1, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(StrollTask.m_wuhievxg(0.6F), 2),
										Pair.of(C_rcqaryar.m_ipranrme(livingEntity -> true, GoTowardsLookTarget.m_ftybsvym(0.6F, 3)), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                ))),
                        Pair.of(2, GoToNearbyPositionTask.m_ivikwldh(MemoryModuleType.HOME, 0.6f, HOME_CLOSE_ENOUGH_DISTANCE, HOME_TOO_FAR_DISTANCE)), //Won't wander too far
                        Pair.of(3, GoToIfNearbyTask.m_alpmpfvp(MemoryModuleType.HOME, 0.6f, HOME_STROLL_AROUND_DISTANCE))

                )
        );
    }

    private static void addFightActivities(LeshonEntity leshonEntity, Brain<LeshonEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        RangedApproachTask.m_lybdhlji(1.0F),
                        FollowMobTask.m_xtjussog(mob -> isTarget(leshonEntity, mob), (float)leshonEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        MeleeAttackTask.m_bsseqsar(18)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    /**
     * This is what lets you switch activities. It should be in reverse order of the importance of the activity.
     */
    public static void updateActivities(LeshonEntity leshonEntity) {
        leshonEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        leshonEntity.setAttacking(leshonEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    /**
     * Used in {@link BlockMixin} to make the leshon angry
     */
    public static void onGuardedBlockInteracted(PlayerEntity player, boolean blockOpen) {
        List<LeshonEntity> list = player.world.getNonSpectatingEntities(LeshonEntity.class, player.getBoundingBox().expand(16.0));
        list.stream().filter(leshonEntity -> leshonEntity.getBrain().hasActivity(Activity.IDLE)).filter(leshon -> !blockOpen || LookTargetUtil.isVisibleInMemory(leshon, player)).forEach(leshon -> {
            if (Sensor.testAttackableTargetPredicateIgnoreVisibility(leshon, player)) {
                leshon.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
                leshon.getBrain().remember(MemoryModuleType.ANGRY_AT, player.getUuid(), 600L);
            }
        });
    }

    /**
     * get target from memory in order of: Angry at > Player > Visible mob
     * @param leshonEntity the leshon
     * @return Optional of Living entity
     */
    private static Optional<? extends LivingEntity> getAttackTarget(LeshonEntity leshonEntity) {
        Brain<LeshonEntity> brain = leshonEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(leshonEntity, MemoryModuleType.ANGRY_AT);
        if(optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(leshonEntity, optional.get())){
            return optional;
        }
        if (brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)) {
            return brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
        }
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            Optional<VisibleLivingEntitiesCache> visibleLivingEntitiesCache = leshonEntity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
            if(visibleLivingEntitiesCache.isPresent()){
                return visibleLivingEntitiesCache.get().m_yzezovsk(entity -> entity.getType() == EntityType.PLAYER && !entity.isSubmergedInWater());
            }
        }
        return Optional.empty();
    }

    /**
     * Check if an entity is a target
     * @param leshonEntity the leshon
     * @param entity the potential target
     * @return if entity is target
     */
    private static boolean isTarget(LeshonEntity leshonEntity, LivingEntity entity) {
        return leshonEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }

    /**
     * Sets the Home memory module to wherever the leshon is when the method is run
     * @param leshonEntity the leshon
     */
    public static void setCurrentPosAsHome(LeshonEntity leshonEntity) {
        GlobalPos globalPos = GlobalPos.create(leshonEntity.world.getRegistryKey(), leshonEntity.getBlockPos());
        leshonEntity.getBrain().remember(MemoryModuleType.HOME, globalPos);
    }
}
