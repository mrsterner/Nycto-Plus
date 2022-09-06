package dev.mrsterner.nyctoplus.common.block.blockentity;

import dev.mrsterner.nyctoplus.common.registry.NPBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class YewLogBlockEntity extends BlockEntity {
    private int effectGiverCoolerDowner;
    public YewLogBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NPBlockEntityTypes.YEW_LOG_BLOCK_ENTITY, blockPos, blockState);
    }

    public static void tick(World world, BlockPos pos, BlockState state, YewLogBlockEntity blockEntity) {
        blockEntity.effectGiverCoolerDowner++;
        if(blockEntity.effectGiverCoolerDowner > 40){
            blockEntity.effectGiverCoolerDowner = 0;
        }
    }
}
