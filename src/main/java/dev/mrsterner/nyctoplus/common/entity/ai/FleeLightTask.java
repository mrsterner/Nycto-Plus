package dev.mrsterner.nyctoplus.common.entity.ai;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.dynamic.GlobalPos;


public class FleeLightTask extends Task<LivingEntity> {
    public FleeLightTask(MemoryModuleType<GlobalPos> memoryModuleType, float f) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED, memoryModuleType, MemoryModuleState.VALUE_PRESENT));
    }
}
