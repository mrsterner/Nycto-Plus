package dev.mrsterner.nyctoplus.common.utils;

import dev.mrsterner.nyctoplus.NyctoPlus;
import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.minecraft.block.Blocks;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.StructureWorldAccess;

import java.util.Optional;
import java.util.random.RandomGenerator;

public class WorldGenUtils {
    /** This method places and nbt structure at a given origin
     * @author - MrSterner
     * @param nbtLocation the location of the structure file about to be placed
     * @param world the structures world access
     * @param origin the blockpos of the structure corner
     * @param chance the probability of a successful placement
     * @return true if the placement of the structure was successfully
     */
    public static boolean generateNbtFeature(Identifier nbtLocation, StructureWorldAccess world, BlockPos origin, float chance){
        StructureTemplateManager templateManager = world.getServer().getStructureTemplateManager();
        //Try fetch the nbt with the structure manager
        Optional<Structure> structureOptional = templateManager.getStructure(nbtLocation);
        if (structureOptional.isEmpty()) {
            NyctoPlus.LOGGER.info("NBT " + nbtLocation + " does not exist!");
        }else if (touchGrass(world, origin, structureOptional.get().getSize().getY())) {
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            Structure structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = origin.subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2), 0, Math.floor((double) structure.getSize().getX() / 2)));
            //Get basic placementData
            StructurePlacementData placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            //Place the structure at the normalized origin
            structure.place(world, normalizeOrigin, normalizeOrigin, placementData, world.getRandom(), 2);
            WorldGenUtils.checkAir(world, normalizeOrigin);
            return true;
        }
        return false;
    }

    public static boolean touchGrass(StructureWorldAccess world, BlockPos pos, int structureHeight) {
        if (world.getBlockState(pos.down()).isIn(BlockTags.DIRT) && pos.getY() + structureHeight < world.getTopY()) {
            return treeNearby(world, pos, structureHeight, 0);
        }
        return false;
    }

    /**
     * @author - Safro
     */
    public static boolean treeNearby(StructureWorldAccess world, BlockPos pos, int treeHeight, int distance) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int yOffset = 0; yOffset <= treeHeight + 1; ++yOffset) {
            for (int xOffset = -distance; xOffset <= distance; ++xOffset) {
                for (int zOffset = -distance; zOffset <= distance; ++zOffset) {
                    BlockPos test = mutable.set(x + xOffset, y + yOffset, z + zOffset);
                    if (world.getBlockState(test).isIn(BlockTags.LOGS) || world.getBlockState(test).isIn(BlockTags.LEAVES)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void checkAir(StructureWorldAccess world, BlockPos center) {
        for (BlockPos pos : BlockPos.iterateOutwards(center.down(), 4, 2, 4)) {
            if (world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isOf(NPObjects.YEW_LOG)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
            }
        }
    }
}
