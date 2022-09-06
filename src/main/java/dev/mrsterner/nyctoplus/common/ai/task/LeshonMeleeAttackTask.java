package dev.mrsterner.nyctoplus.common.ai.task;

import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import net.minecraft.entity.ai.brain.task.MeleeAttackTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class LeshonMeleeAttackTask extends MeleeAttackTask {
    public LeshonMeleeAttackTask(int i) {
        super(i);
    }

    @Override
    protected void finishRunning(ServerWorld world, MobEntity entity, long time) {
        if(entity instanceof LeshonEntity leshonEntity){
            leshonEntity.setAttacking(false);
        }
        super.finishRunning(world, entity, time);
    }

    @Override
    protected void run(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        if(mobEntity instanceof LeshonEntity leshonEntity){
            leshonEntity.setAttacking(true);
        }
        super.run(serverWorld, mobEntity, l);
    }
}
