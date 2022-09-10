package dev.mrsterner.nyctoplus.common.entity;

import com.mojang.datafixers.util.Pair;
import dev.mrsterner.nyctoplus.common.block.blockentity.LinkBlockEntity;
import dev.mrsterner.nyctoplus.common.entity.ai.LeshonBrain;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import dev.mrsterner.nyctoplus.common.world.NPWorldState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AlmostUnkillableEntity extends HostileEntity {
    /**
     * Kinda Dead Flags Indexes: 0 - Alive, 1 - Kinda Dead
     */
    public static final TrackedData<Byte> KINDA_DEAD = DataTracker.registerData(AlmostUnkillableEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static final TrackedData<Integer> REVIVE_COOLDOWN = DataTracker.registerData(AlmostUnkillableEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected AlmostUnkillableEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static Pair<ServerWorld, LinkBlockEntity> getLink(LivingEntity livingEntity) {
        if (livingEntity.world instanceof ServerWorld) {
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
        }
        return null;
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if(isKindaDead() && getReviveCooldown() > 0){
            decreaseReviveCooldown();
        }
    }

    public void setReviveCooldown(int value){
        this.getDataTracker().set(REVIVE_COOLDOWN, value);
    }

    public int getReviveCooldown(){
        return this.getDataTracker().get(REVIVE_COOLDOWN);
    }

    public void decreaseReviveCooldown(){
        setReviveCooldown(this.getDataTracker().get(REVIVE_COOLDOWN) - 1);
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if(amount != 0){
            if(getLink(this) != null){
                if(isKindaAlive()){
                    if (this.getHealth() - amount <= 1) {
                        this.setHealth(1);
                        this.setKindaDead(true);
                    }else{
                        super.applyDamage(source, amount);
                    }
                }
            }else{
                if(isKindaAlive() || isKindaDead()){
                    super.applyDamage(source, amount);
                }
            }
        }
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(KINDA_DEAD, (byte) 0b0000_0000);
        super.initDataTracker();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte(Constants.NBT.KINDA_DEAD, dataTracker.get(KINDA_DEAD));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(KINDA_DEAD, nbt.getByte(Constants.NBT.KINDA_DEAD));
    }

    protected void setKindaDeadFlag(int index, boolean value) {
        byte b = this.dataTracker.get(KINDA_DEAD);
        if (value) {
            this.dataTracker.set(KINDA_DEAD, (byte) (b | 1 << index));
        } else {
            this.dataTracker.set(KINDA_DEAD, (byte) (b & ~(1 << index)));
        }
    }

    protected boolean getKindaDeadFlag(int index) {
        return (this.dataTracker.get(KINDA_DEAD) & 1 << index) != 0;
    }

    public boolean isKindaAlive() {
        return getKindaDeadFlag(0);
    }

    public void setKindaAlive(boolean isAlive) {
        setKindaDeadFlag(0, isAlive);
    }

    public boolean isKindaDead() {
        return getKindaDeadFlag(1);
    }

    public void setKindaDead(boolean isKindaDead) {
        setKindaDeadFlag(1, isKindaDead);
    }
}
