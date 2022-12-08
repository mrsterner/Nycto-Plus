package dev.mrsterner.nyctoplus.common.block.blockentity;

import dev.mrsterner.nyctoplus.common.registry.NPBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class HellsGateBlockEntity extends BlockEntity {
	public HellsGateBlockEntity( BlockPos blockPos, BlockState blockState) {
		super(NPBlockEntityTypes.HELLS_GATE_BLOCK_ENTITY, blockPos, blockState);
	}
}
