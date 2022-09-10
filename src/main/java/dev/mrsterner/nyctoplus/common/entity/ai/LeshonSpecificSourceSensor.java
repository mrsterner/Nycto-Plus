package dev.mrsterner.nyctoplus.common.entity.ai;

import com.google.common.collect.ImmutableSet;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;

import java.util.Optional;
import java.util.Set;

public class LeshonSpecificSourceSensor extends Sensor<LivingEntity> {
    public static final int SHY_LIGHT_LEVEL = 8;

    public LeshonSpecificSourceSensor(){

    }

    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_REPELLENT);
    }
    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Brain<?> brain = entity.getBrain();
        brain.remember(MemoryModuleType.NEAREST_REPELLENT, this.findNearestHeatSource(world, entity));
    }

    private Optional<BlockPos> findNearestHeatSource(ServerWorld world, LivingEntity livingEntity) {
        Optional<BlockPos> optionalBlockPos = BlockPos.findClosest(livingEntity.getBlockPos(), 8, 4, pos -> world.getBlockState(pos).isIn(Constants.Tags.HOT_BLOCK));
        if(optionalBlockPos.isPresent()){
            return BlockPos.findClosest(livingEntity.getBlockPos(), 4, 4, pos -> world.getBlockState(pos).isIn(Constants.Tags.HOT_BLOCK));
        }
        if(world.isNight()){
            return BlockPos.findClosest(livingEntity.getBlockPos(), 12, 5, pos -> world.getLightLevel(LightType.BLOCK, pos) > SHY_LIGHT_LEVEL);
        }
        return Optional.empty();


    }

}
