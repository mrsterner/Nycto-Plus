package dev.mrsterner.nyctoplus.common.utils;

import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

public class Constants {
    public static final String MODID = "nyctoplus";
    public static final QuiltItemGroup NYCTO_PLUS_GROUP = QuiltItemGroup.builder(Constants.id("items")).icon(() -> new ItemStack(NPObjects.YEW_BERRIES)).build();

    public static class Tags {

    }

    public static class NBT {

    }

    public static class DataTrackers {

    }

    public static Identifier id(String name) {
        return new Identifier(Constants.MODID, name);
    }
}
