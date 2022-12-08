package dev.mrsterner.nyctoplus.common.block.yew;

import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class YewTrapdoorBlock extends TrapdoorBlock {
	private static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return false;
	}
    public YewTrapdoorBlock() {
        super(AbstractBlock.Settings.of(Material.WOOD, MapColor.ORANGE).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(YewTrapdoorBlock::never),
				SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE,
				SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN);
    }
}
