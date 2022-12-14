package dev.mrsterner.nyctoplus.data;

import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.*;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.*;

public class NPModelProvider extends FabricModelProvider {
	public NPModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerLog(NPObjects.YEW_LOG).log(NPObjects.YEW_LOG).wood(NPObjects.YEW_WOOD);
        generator.registerLog(NPObjects.STRIPPED_YEW_LOG).log(NPObjects.STRIPPED_YEW_LOG).wood(NPObjects.STRIPPED_YEW_WOOD);
        generator.registerFlowerPotPlant(NPObjects.YEW_SAPLING, NPObjects.POTTED_YEW_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        generator.registerDoor(NPObjects.YEW_DOOR);
        generator.registerTrapdoor(NPObjects.YEW_TRAPDOOR);

        generator.registerSingleton(NPObjects.YEW_LEAVES, TexturedModel.LEAVES);

        generator.new BlockTexturePool(TexturedModel.CUBE_ALL.get(NPObjects.YEW_PLANKS).getTexture()).stairs(NPObjects.YEW_STAIRS);
        generator.new BlockTexturePool(TexturedModel.CUBE_ALL.get(NPObjects.YEW_PLANKS).getTexture()).pressurePlate(NPObjects.YEW_PRESSURE_PLATE);
        generator.new BlockTexturePool(TexturedModel.CUBE_ALL.get(NPObjects.YEW_PLANKS).getTexture()).button(NPObjects.YEW_BUTTON);
        generator.new BlockTexturePool(TexturedModel.CUBE_ALL.get(NPObjects.YEW_PLANKS).getTexture()).fence(NPObjects.YEW_FENCE);
        generator.new BlockTexturePool(TexturedModel.CUBE_ALL.get(NPObjects.YEW_PLANKS).getTexture()).fenceGate(NPObjects.YEW_FENCE_GATE);

        registerSlab(generator, NPObjects.YEW_SLAB, NPObjects.YEW_PLANKS);
    }

    public static void registerSlab(BlockStateModelGenerator blockStateModelGenerator, Block slab, Block source) {
        TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(source);
        BlockStateModelGenerator.BlockTexturePool pool = blockStateModelGenerator.new BlockTexturePool(texturedModel.getTexture());
        pool.base(source, Models.CUBE_ALL);
        pool.slab(slab);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(NPObjects.YEW_SIGN_ITEM, Models.GENERATED);
        itemModelGenerator.register(NPObjects.YEW_BERRIES, Models.GENERATED);
    }

}
