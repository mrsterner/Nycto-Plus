package dev.mrsterner.nyctoplus.common.utils;

import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

public class Constants {
    public static final String MODID = "nyctoplus";
    public static final QuiltItemGroup NYCTO_PLUS_GROUP = QuiltItemGroup.builder(Constants.id("items")).icon(() -> new ItemStack(NPObjects.YEW_BERRIES)).build();

    public static class Tags {
        public static final TagKey<Block> GUARDED_BY_LESHON = TagKey.of(Registry.BLOCK_KEY, new Identifier(MODID, "guarded_by_leshon"));
        public static final TagKey<Block> HOT_BLOCK = TagKey.of(Registry.BLOCK_KEY, new Identifier(MODID, "hot_block"));
    }

    public static class NBT {

        public static final String POSE_FLAGS = "pose_flags";
        public static final String VARIANT = "variant";
        public static final String KINDA_DEAD = "kinda_dead";
        public static final String PLAYER = "player";
        public static final String POS = "pos";
        public static final String LINK = "link";
    }

    public static class DataTrackers {

    }

    public static Identifier id(String name) {
        return new Identifier(Constants.MODID, name);
    }
}
