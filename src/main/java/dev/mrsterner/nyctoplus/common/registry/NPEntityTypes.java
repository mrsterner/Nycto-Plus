package dev.mrsterner.nyctoplus.common.registry;

import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class NPEntityTypes {
    private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    public static final EntityType<LeshonEntity> LESHON = register("leshon", FabricEntityTypeBuilder
            .<LeshonEntity>createMob()
            .spawnGroup(SpawnGroup.MONSTER)
            .entityFactory(LeshonEntity::new)
            .defaultAttributes(LeshonEntity::createLeshonAttributes)
            .dimensions(EntityDimensions.fixed(1f, 3f))
            .build());


    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        ENTITY_TYPES.put(type, Constants.id(name));
        return type;
    }

    public static void init() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registries.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
    }
}
