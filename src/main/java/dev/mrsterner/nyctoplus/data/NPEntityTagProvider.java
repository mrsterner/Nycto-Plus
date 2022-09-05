package dev.mrsterner.nyctoplus.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;

public class NPEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public NPEntityTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
    }
}