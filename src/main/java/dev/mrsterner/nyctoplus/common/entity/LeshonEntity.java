package dev.mrsterner.nyctoplus.common.entity;

import com.mojang.serialization.Dynamic;
import dev.mrsterner.nyctoplus.common.entity.ai.LeshonBrain;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LeshonEntity extends AlmostUnkillableEntity implements GeoEntity {
    /**
     * Pose Flags Indexes: 0 - Standing, 1 - Quad, 2 - Sleep, 3 - Dead
     */
    private static final TrackedData<Byte> POSE_FLAGS = DataTracker.registerData(LeshonEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(LeshonEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final RawAnimation INTERACT_RIGHT = RawAnimation.begin().thenPlay("misc.interact.right");
	private static final RawAnimation BLOCK_LEFT = RawAnimation.begin().thenPlay("attack.block.left");
	private static final RawAnimation BLOCK_RIGHT = RawAnimation.begin().thenPlay("attack.block.right");

	private static final RawAnimation SNEAK_REVERSE = RawAnimation.begin().thenPlay("move.sneak.reverse");
	private static final RawAnimation QUAD_REST = RawAnimation.begin().thenPlay("quad.misc.rest");
	private static final RawAnimation QUAD_RUN = RawAnimation.begin().thenPlay("quad.move.run");
	private static final RawAnimation QUAD_IDLE = RawAnimation.begin().thenPlay("quad.move.idle");
	private static final RawAnimation ON_WATER_SWIM = RawAnimation.begin().thenPlay("leshon.move.swim");
	private static final RawAnimation FALL = RawAnimation.begin().thenPlay("move.fall");
	private static final RawAnimation WALK_REVERSE = RawAnimation.begin().thenPlay("move.walk.reverse");


	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
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
        this.setReviveCooldown(20 * 30);
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

	private void setAttackTarget(LivingEntity entity) {
		this.brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		this.brain.remember(MemoryModuleType.ATTACK_TARGET, entity, 200L);
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
        return getPoseFlag(2);
    }

    public void setLeshonSleeping(boolean sleeping) {
        setPoseFlag(2, sleeping);
    }

    public boolean isLeshonStanding() {
        return getPoseFlag(0);
    }

    public void setLeshonStandning(boolean standing) {
        setPoseFlag(0, standing);
    }


    public boolean isLeshonQuad() {
        return getPoseFlag(1);
    }

    public void setLeshonQuad(boolean quadruped) {
        setPoseFlag(1, quadruped);
    }


	// Helper method to get the left hand in an ambidextrous entity
	protected Hand getLeftHand() {
		return this.isLeftHanded() ? Hand.MAIN_HAND : Hand.OFF_HAND;
	}

	// Helper method to get the right hand in an ambidextrous entity
	protected Hand getRightHand() {
		return !this.isLeftHanded() ? Hand.MAIN_HAND : Hand.OFF_HAND;
	}

	/*
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

	 */

	protected PlayState poseBody(AnimationState<LeshonEntity> state) {
		boolean isMovingHorizontal = Math.sqrt(Math.pow(motionCalc.x, 2) + Math.pow(motionCalc.z, 2)) > 0.005;
		if (this.isSleeping()) {
			state.setAnimation(QUAD_REST);
		}else if(hasVehicle()){
			state.setAnimation(DefaultAnimations.SIT);
		}else if(this.getPose() == EntityPose.SWIMMING){
			state.setAnimation(DefaultAnimations.SWIM);
		}else if (this.isSubmergedInWater()) {
			state.setAnimation(ON_WATER_SWIM);
		}else if (!this.isOnGround() && motionCalc.getY() < -0.6) {
			if (!this.isClimbing()) {
				state.setAnimation(FALL);
			} else if (this.isSneaking()) {
				if (isMovingHorizontal) {
					if (this.forwardSpeed < 0) {
						state.setAnimation(SNEAK_REVERSE);
					} else {
						state.setAnimation(DefaultAnimations.SNEAK);
					}
				}
			}
		} else {
			if (this.isLeshonQuad()) {
				if(this.isSprinting()){
					state.setAnimation(QUAD_RUN);
				}else{
					state.setAnimation(QUAD_IDLE);
				}
			}else if(this.forwardSpeed < 0){
				state.setAnimation(WALK_REVERSE);
			}else if (isMovingHorizontal) {
				state.setAnimation(DefaultAnimations.WALK);
			}
		}
		return PlayState.CONTINUE;
	}

	protected PlayState predicateHandPose(Hand hand, AnimationState<LeshonEntity> state) {
		ItemStack heldStack = getStackInHand(hand);
		if (heldStack.isEmpty()){
			return PlayState.STOP;
		}
		Item handItem = heldStack.getItem();
		if (isBlocking() && (handItem instanceof ShieldItem || handItem.getUseAction(heldStack) == UseAction.BLOCK)){
			return state.setAndContinue(getLeftHand() == hand ? BLOCK_LEFT : BLOCK_RIGHT);
		}
		return PlayState.STOP;
	}


	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(
				DefaultAnimations.genericIdleController(this),
				new AnimationController<>(this, "Body", 20, this::poseBody),
				new AnimationController<>(this, "Right Hand", 10, state -> predicateHandPose(getRightHand(), state))
						.triggerableAnim("interact", INTERACT_RIGHT)
		);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}
}
