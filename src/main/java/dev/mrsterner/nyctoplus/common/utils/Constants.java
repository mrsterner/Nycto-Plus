package dev.mrsterner.nyctoplus.common.utils;

import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class Constants {
    public static final String MODID = "nyctoplus";
    //public static final FabricItemGroup NYCTO_PLUS_GROUP = FabricItemGroup.builder(Constants.id("items")).icon(() -> new ItemStack(NPObjects.YEW_BERRIES)).build();

	public static final ItemGroup NYCTO_PLUS_GROUP = FabricItemGroup.builder(Constants.id("items"))
			.m_kdtuovgn(() -> new ItemStack(NPObjects.YEW_BERRIES)).m_cetlzdca((enabledFeatures, entries, operatorEnabled) -> {
				for(Item item : NPObjects.ITEMS.keySet()) {
					entries.addItem(item);
				}
			}).m_fepxguyf();

    public static class Tags {
        public static final TagKey<Block> GUARDED_BY_LESHON = TagKey.of(RegistryKeys.BLOCK, new Identifier(MODID, "guarded_by_leshon"));
        public static final TagKey<Block> HOT_BLOCK = TagKey.of(RegistryKeys.BLOCK, new Identifier(MODID, "hot_block"));
    }

    public static class NBT {

        public static final String POSE_FLAGS = "PoseFlags";
        public static final String VARIANT = "Variant";
        public static final String KINDA_DEAD = "KindaDead";
        public static final String PLAYER = "Player";
        public static final String POS = "Pos";
        public static final String LINK = "Link";
        public static final String REVIVE_TIMER = "ReviveTimer";
		public static final String REVIVE_COOLDOWN = "ReviveCooldown";
        public static final String AGE = "Age";
    }

    public static class DataTrackers {

    }

    public static Identifier id(String name) {
        return new Identifier(Constants.MODID, name);
    }
}
