package dev.mrsterner.nyctoplus.common.entity;

import com.mojang.serialization.Dynamic;
import dev.mrsterner.nyctoplus.common.entity.ai.LeshonBrain;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class LeshonEntity extends AlmostUnkillableEntity implements IAnimatable {
    /**
     * Pose Flags Indexes: 0 - Quad, 1 - Sleep, 2 - Dead
     */
    private static final TrackedData<Byte> POSE_FLAGS = DataTracker.registerData(LeshonEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(LeshonEntity.class, TrackedDataHandlerRegistry.INTEGER);


    private final AnimationFactory factory = new AnimationFactory(this);
    public Vec3d motionCalc = new Vec3d(0,0,0);

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
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35F)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        LeshonBrain.setCurrentPosAsHome(this);
        this.setVariant(1);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("leshonBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        LeshonBrain.updateActivities(this);
        super.mobTick();
    }

    @Override
    public boolean canTakeDamage() {
        return super.canTakeDamage();
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(POSE_FLAGS, (byte) 0b0000_0000);
        dataTracker.startTracking(VARIANT, 1);
        super.initDataTracker();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte(Constants.NBT.POSE_FLAGS, dataTracker.get(POSE_FLAGS));
        nbt.putInt(Constants.NBT.VARIANT, dataTracker.get(VARIANT));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(POSE_FLAGS, nbt.getByte(Constants.NBT.POSE_FLAGS));
        dataTracker.set(VARIANT, nbt.getInt(Constants.NBT.VARIANT));
    }

    public int getVariant(){
        return dataTracker.get(VARIANT);
    };

    public void setVariant(int variant){
        this.getDataTracker().set(VARIANT, variant);
    }

    @Override
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

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (!this.world.isClient && !this.isAiDisabled()) {
            Entity entity = source.getAttacker();
            if (this.brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isEmpty()
                    && entity instanceof LivingEntity livingEntity
                    && (!(source instanceof ProjectileDamageSource) || this.isInRange(livingEntity, 5.0))) {
                this.setAttackTarget(livingEntity);
            }
        }
        return bl;
    }

    public void setAttackTarget(LivingEntity entity) {
        UpdateAttackTargetTask.updateAttackTarget(this, entity);
    }

    protected void setPoseFlag(int index, boolean value) {
        byte b = this.dataTracker.get(POSE_FLAGS);
        if (value) {
            this.dataTracker.set(POSE_FLAGS, (byte) (b | 1 << index));
        } else {
            this.dataTracker.set(POSE_FLAGS, (byte) (b & ~(1 << index)));
        }
    }

    protected boolean getPoseFlag(int index) {
        return (this.dataTracker.get(POSE_FLAGS) & 1 << index) != 0;
    }

    @Override
    public boolean isSleeping() {
        return isLeshonSleeping();
    }

    public boolean isLeshonSleeping() {
        return getPoseFlag(1);
    }

    public void setLeshonSleeping(boolean sleeping) {
        setPoseFlag(1, sleeping);
    }


    public boolean isLeshonQuad() {
        return getPoseFlag(0);
    }

    public void setLeshonQuad(boolean quadruped) {
        setPoseFlag(0, quadruped);
    }





    private <E extends IAnimatable> PlayState movement(AnimationEvent<E> animationEvent) {
        AnimationBuilder builder = new AnimationBuilder();
        boolean isMovingHorizontal = Math.sqrt(Math.pow(motionCalc.x, 2) + Math.pow(motionCalc.z, 2)) > 0.005;
        if (this.isSleeping()) {
            builder.addAnimation("animation.leshon.quad.sleep", true);
        }else if (this.getPose() == EntityPose.SWIMMING) {
            builder.addAnimation("animation.leshon.swim", true);
        }else if(this.isSubmergedInWater()){
            builder.addAnimation("animation.leshon.standing.swim", false);
        }else if (!this.isOnGround() && motionCalc.getY() < -0.6) {
            if (!this.isClimbing()) {
                builder.addAnimation("animation.leshon.standing.fall", false);
            }
        }else if (this.isSneaking()) {
            if (isMovingHorizontal) {
                if(this.forwardSpeed < 0){
                    builder.addAnimation("animation.leshon.standing.sneakDev_back", true);
                }else{
                    builder.addAnimation("animation.leshon.standing.sneakDev", true);
                }
            } else {
                builder.addAnimation("animation.leshon.standing.sneak_idle", true);
            }
        }else {
            if (this.isLeshonQuad()) {
                if(this.isSprinting()){
                    builder.addAnimation("animation.leshon.quad.runningDev", true);
                }else{
                    builder.addAnimation("animation.leshon.quad.idle", true);
                }
            }else if(this.forwardSpeed < 0){
                builder.addAnimation("animation.leshon.standing.walk_back", true);
            }else if (isMovingHorizontal || animationEvent.isMoving()) {
                builder.addAnimation("animation.leshon.standing.walk", true);
            }
        }
        if(animationEvent.getController().getCurrentAnimation() == null || builder.getRawAnimationList().size() <= 0){
            builder.addAnimation( "animation.leshon.standing.idle", true);
        }
        animationEvent.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attack(AnimationEvent<E> animationEvent) {
        if (this.handSwinging) {
            animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("animation.leshon.standing.attack", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "Movement", 2, this::movement));
        animationData.addAnimationController(new AnimationController<>(this, "Attack", 2, this::attack));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
