package dev.mrsterner.nyctoplus.common.entity;

import com.mojang.datafixers.util.Pair;
import dev.mrsterner.nyctoplus.NyctoPlus;
import dev.mrsterner.nyctoplus.common.block.blockentity.LinkBlockEntity;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import dev.mrsterner.nyctoplus.common.world.NPWorldState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public abstract class AlmostUnkillableEntity extends HostileEntity {
    public static final TrackedData<Boolean> KINDA_DEAD = DataTracker.registerData(AlmostUnkillableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Integer> REVIVE_TIMER = DataTracker.registerData(AlmostUnkillableEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> REVIVE_COOLDOWN = DataTracker.registerData(AlmostUnkillableEntity.class, TrackedDataHandlerRegistry.INTEGER);

	//Time the entity is recovering from dying, healing
	public static final int REVIVE_TIMER_VALUE = 20 * 20;
	//Time for revive ability to be available again
	public static final int REVIVE_COOLDOWN_VALUE = REVIVE_TIMER_VALUE + 20 * 60;

    protected AlmostUnkillableEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

	//The link block will keep this entity from dying after being killed, and rise again
    public static Pair<ServerWorld, LinkBlockEntity> getLink(LivingEntity livingEntity) {
        if (livingEntity.world instanceof ServerWorld) {
            if (livingEntity.world.getServer() != null) {
                for (ServerWorld serverWorld : livingEntity.world.getServer().getWorlds()) {
                    NPWorldState worldState = NPWorldState.get(serverWorld);
                    if (worldState.link.containsKey(livingEntity.getUuid())) {
                        BlockEntity entity = serverWorld.getBlockEntity(worldState.link.get(livingEntity.getUuid()));
                        if (entity instanceof LinkBlockEntity) {
                            return new Pair<>(serverWorld, (LinkBlockEntity) entity);
                        }
                        worldState.removeLink(livingEntity.getUuid());
                    }
                }
            }else{
                NyctoPlus.LOGGER.info("ServerWorld doesn't exist");
            }
        }
        return null;
    }

    @Override
    protected void mobTick() {
        if(isKindaDead()){
            if(getReviveTimer() > 0){
				decreaseReviveTimer();
                if(getReviveTimer() % 20 == 0){
                    this.heal(1);
                }
            }else{
                setKindaDead(false);
            }
        }else{
			if(getReviveCooldown() > 0){
				decreaseReviveCooldown();
			}
		}
        super.mobTick();
    }

	@Override
	public void tickMovement() {
		if(!isKindaDead()){
			super.tickMovement();
		}
	}

	public void setReviveTimer(int value){
        this.getDataTracker().set(REVIVE_TIMER, value);
    }

    public int getReviveTimer(){
        return this.getDataTracker().get(REVIVE_TIMER);
    }

    public void decreaseReviveTimer(){
		setReviveTimer(this.getDataTracker().get(REVIVE_TIMER) - 1);
    }

	public void setReviveCooldown(int value){
		this.getDataTracker().set(REVIVE_COOLDOWN, value);
	}

	public int getReviveCooldown(){
		return this.getDataTracker().get(REVIVE_COOLDOWN);
	}

	public void decreaseReviveCooldown(){
		setReviveTimer(this.getDataTracker().get(REVIVE_COOLDOWN) - 1);
	}


	@Override
    protected void applyDamage(DamageSource source, float amount) {
        if(amount != 0){
            if(source.getAttacker() == null){
                super.applyDamage(source, amount);
            }else if(getLink(this) != null){
                if(!isKindaDead() && getReviveCooldown() == 0){
                    if (this.getHealth() - amount <= 1) {
                        this.setHealth(1);
                        this.setKindaDead(true);
                    }else{
                        super.applyDamage(source, amount);
                    }
                }
            }else{
                super.applyDamage(source, amount);
            }
        }
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(KINDA_DEAD, false);
		dataTracker.startTracking(REVIVE_TIMER, 0);
		dataTracker.startTracking(REVIVE_COOLDOWN, 0);
        super.initDataTracker();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean(Constants.NBT.KINDA_DEAD, dataTracker.get(KINDA_DEAD));
		nbt.putInt(Constants.NBT.REVIVE_TIMER, dataTracker.get(REVIVE_TIMER));
		nbt.putInt(Constants.NBT.REVIVE_COOLDOWN, dataTracker.get(REVIVE_COOLDOWN));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(KINDA_DEAD, nbt.getBoolean(Constants.NBT.KINDA_DEAD));
		dataTracker.set(REVIVE_TIMER, nbt.getInt(Constants.NBT.REVIVE_TIMER));
		dataTracker.set(REVIVE_COOLDOWN, nbt.getInt(Constants.NBT.REVIVE_COOLDOWN));
    }

    public boolean isKindaDead() {
        return this.getDataTracker().get(KINDA_DEAD);
    }

    public void setKindaDead(boolean bl) {
		setReviveCooldown(REVIVE_COOLDOWN_VALUE);
		setReviveTimer(REVIVE_TIMER_VALUE);
        this.getDataTracker().set(KINDA_DEAD, bl);
    }
}
