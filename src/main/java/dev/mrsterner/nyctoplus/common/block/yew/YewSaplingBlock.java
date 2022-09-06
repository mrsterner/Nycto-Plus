package dev.mrsterner.nyctoplus.common.block.yew;

import dev.mrsterner.nyctoplus.common.utils.Constants;
import dev.mrsterner.nyctoplus.common.utils.WorldGenUtils;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class YewSaplingBlock extends PlantBlock implements Fertilizable {
    public static final IntProperty STAGE = Properties.STAGE;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    private final String nbtLocation;
    private final int variants;

    public YewSaplingBlock(int variants, String nbtLocation, AbstractBlock.Settings settings) {
        super(settings);
        this.nbtLocation = nbtLocation;
        this.variants = variants;
        this.setDefaultState(this.stateManager.getDefaultState().with(STAGE, 0));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(7) == 0) {
            this.generate(world, pos, state);
        }
    }

    public void generate(ServerWorld world, BlockPos pos, BlockState state) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, state.cycle(STAGE), Block.NO_REDRAW);
        } else {
            generateStructureTree(world, pos);
        }

    }

    private void generateStructureTree(ServerWorld world, BlockPos pos) {
        WorldGenUtils.generateNbtFeature(Constants.id(nbtLocation + "_" + world.getRandom().nextInt(variants)), world, pos, 1);
    }


    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, RandomGenerator random, BlockPos pos, BlockState state) {
        return (double)world.random.nextFloat() < 0.45;
    }

    @Override
    public void grow(ServerWorld world, RandomGenerator random, BlockPos pos, BlockState state) {
        this.generate(world, pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
