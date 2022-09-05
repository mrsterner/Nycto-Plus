package dev.mrsterner.nyctoplus.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NPDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(NPBlockTagProvider::new);
        fabricDataGenerator.addProvider(NPRecipeProvider::new);
        fabricDataGenerator.addProvider(NPBlockLootTableProvider::new);
        fabricDataGenerator.addProvider(NPModelProvider::new);
        fabricDataGenerator.addProvider(NPEntityTagProvider::new);
        fabricDataGenerator.addProvider(
                new NPItemTagProvider(fabricDataGenerator,
                        fabricDataGenerator.addProvider(NPBlockTagProvider::new)));
    }
}