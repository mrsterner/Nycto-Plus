package dev.mrsterner.nyctoplus.common.entity;

import com.mojang.serialization.Dynamic;
import dev.mrsterner.nyctoplus.common.ai.LeshonBrain;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class LeshonEntity extends HostileEntity {
    public LeshonEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.getNavigation().setCanSwim(true);
        this.setPathfindingPenalty(PathNodeType.UNPASSABLE_RAIL, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
        this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 8.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 8.0F);
    }

    public static DefaultAttributeContainer.Builder createLeshonAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 64.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0);
    }

    protected void mobTick() {
        ServerWorld serverWorld = (ServerWorld)this.world;
        serverWorld.getProfiler().push("leshonBrain");
        this.getBrain().tick(serverWorld, this);

        this.world.getProfiler().pop();
        super.mobTick();


        LeshonBrain.updateActivities(this);
    }

    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return LeshonBrain.create(this, dynamic);
    }

    public Brain<LeshonEntity> getBrain() {
        return (Brain<LeshonEntity>) super.getBrain();
    }

    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Nullable
    public LivingEntity getTarget() {
        return this.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    public void setAttackTarget(LivingEntity entity) {
        UpdateAttackTargetTask.updateAttackTarget(this, entity);
    }

    @Contract("null->false")
    public boolean isEnemy(@Nullable Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return this.world == entity.world && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity)
                    && !this.isTeammate(entity)
                    && livingEntity.getType() != EntityType.ARMOR_STAND
                    && livingEntity.getType() != EntityType.WARDEN
                    && !livingEntity.isInvulnerable()
                    && !livingEntity.isDead()
                    && this.world.getWorldBorder().contains(livingEntity.getBoundingBox());
        }
        return false;
    }
}
