package dev.mrsterner.nyctoplus.common.block.blockentity;

import dev.mrsterner.nyctoplus.common.registry.NPBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class LinkBlockEntity extends BlockEntity {
    public LinkBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NPBlockEntityTypes.LINK_BLOCK_ENTITY, blockPos, blockState);
    }
}
