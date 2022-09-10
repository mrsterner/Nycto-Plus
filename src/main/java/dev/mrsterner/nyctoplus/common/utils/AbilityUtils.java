package dev.mrsterner.nyctoplus.common.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class AbilityUtils {
    public static boolean isFireSourceNear(LivingEntity livingEntity){
        double rangeCheck = 4;
        BlockPos blockPos = livingEntity.getBlockPos();
        List<Block> blockList = new ArrayList<>();
        for(double x = -rangeCheck; x <= rangeCheck; ++x) {
            for (double y = -rangeCheck; y <= rangeCheck; ++y) {
                for (double z = -rangeCheck; z <= rangeCheck; ++z) {
                    BlockPos heatedBlock = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    if(livingEntity.world.getBlockState(heatedBlock).isIn(Constants.Tags.HOT_BLOCK)){
                        blockList.add(livingEntity.world.getBlockState(heatedBlock).getBlock());
                    }
                }
            }
        }
        return !blockList.isEmpty();
    }


}
