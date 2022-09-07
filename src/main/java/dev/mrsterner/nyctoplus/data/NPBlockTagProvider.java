package dev.mrsterner.nyctoplus.data;

import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class NPBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public NPBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(Constants.Tags.GUARDED_BY_LESHON).add(NPObjects.YEW_LOG, NPObjects.YEW_WOOD, NPObjects.YEW_LEAVES, NPObjects.CLAWED_YEW_BLOCK);
    }
}
