package dev.mrsterner.nyctoplus.common.block.yew;

import dev.mrsterner.nyctoplus.common.block.blockentity.YewLogBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class YewCutLogBlock extends PillarBlock implements BlockEntityProvider {
    private final Supplier<Block> stripped;

    public YewCutLogBlock(Supplier<Block> stripped, MapColor top, Settings settings) {
        super(settings);
        this.stripped = stripped;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> YewLogBlockEntity.tick(tickerWorld, pos, tickerState, (YewLogBlockEntity) blockEntity);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new YewLogBlockEntity(pos, state);
    }
}
