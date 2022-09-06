package dev.mrsterner.nyctoplus.common.registry;

import dev.mrsterner.nyctoplus.common.block.blockentity.YewLogBlockEntity;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class NPBlockEntityTypes {
    private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

   public static final BlockEntityType<YewLogBlockEntity> YEW_LOG_BLOCK_ENTITY = register("yew_log", QuiltBlockEntityTypeBuilder.create(YewLogBlockEntity::new, NPObjects.YEW_CUT_LOG).build(null));


    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, Constants.id(name));
        return type;
    }



    public static void init() {
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
    }
}
