package dev.mrsterner.nyctoplus.common.block.yew;

import dev.mrsterner.nyctoplus.common.block.blockentity.ClawedYewBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ClawedYewLog extends PillarBlock implements BlockEntityProvider {
    private final Supplier<Block> stripped;

    public ClawedYewLog(Supplier<Block> stripped, MapColor top, Settings settings) {
        super(settings);
        this.stripped = stripped;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> ClawedYewBlockEntity.tick(tickerWorld, pos, tickerState, (ClawedYewBlockEntity) blockEntity);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ClawedYewBlockEntity(pos, state);
    }
}
