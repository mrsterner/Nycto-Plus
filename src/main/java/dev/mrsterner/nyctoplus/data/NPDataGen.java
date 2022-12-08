package dev.mrsterner.nyctoplus.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class NPDataGen implements DataGeneratorEntrypoint {


    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.createPack().addProvider(NPBlockTagProvider::new);
        fabricDataGenerator.createPack().addProvider(NPRecipeProvider::new);
       // fabricDataGenerator.createPack().addProvider(NPBlockLootTableProvider::new);
        fabricDataGenerator.createPack().addProvider(NPModelProvider::new);
        fabricDataGenerator.createPack().addProvider(NPEntityTagProvider::new);
        fabricDataGenerator.createPack().addProvider(NPItemTagProvider::new);
		//fabricDataGenerator.createPack().addProvider(NPBlockTagProvider::new);
    }


}
