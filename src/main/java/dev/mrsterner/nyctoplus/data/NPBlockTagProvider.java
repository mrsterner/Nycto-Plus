package dev.mrsterner.nyctoplus.data;

import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class NPBlockTagProvider extends FabricTagProvider.BlockTagProvider {


	public NPBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider arg) {
		getOrCreateTagBuilder(Constants.Tags.GUARDED_BY_LESHON).add(NPObjects.YEW_LOG, NPObjects.YEW_WOOD, NPObjects.YEW_LEAVES, NPObjects.CLAWED_YEW_BLOCK);
		getOrCreateTagBuilder(Constants.Tags.HOT_BLOCK).add(Blocks.TORCH, Blocks.CAMPFIRE, Blocks.MAGMA_BLOCK, Blocks.FIRE, Blocks.SOUL_CAMPFIRE, Blocks.SOUL_FIRE);
	}
}
