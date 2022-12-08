package dev.mrsterner.nyctoplus.common.registry;

import dev.mrsterner.nyctoplus.common.block.HellsGateBlock;
import dev.mrsterner.nyctoplus.common.block.TotemBlock;
import dev.mrsterner.nyctoplus.common.block.yew.*;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import dev.mrsterner.nyctoplus.mixin.access.BlocksAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings.copyOf;

public class NPObjects {
    public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    public static final Identifier YEW_SIGN_TEXTURE = new Identifier(Constants.MODID, "entity/sign/yew");

    //ITEMS
    public static final Item YEW_BERRIES = register("yew_berries", new Item(settings().food(new FoodComponent.Builder().alwaysEdible().hunger(2).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 8,1), 1).build())));


    //BLOCKS

    //YEW
    public static final Block STRIPPED_YEW_LOG = register("stripped_yew_log", new PillarBlock(copyOf(Blocks.OAK_LOG)), true);
    public static final Block STRIPPED_YEW_WOOD = register("stripped_yew_wood", new PillarBlock(copyOf(STRIPPED_YEW_LOG)), true);
    public static final Block YEW_LOG = register("yew_log", new YewLogBlock(() -> STRIPPED_YEW_LOG, MapColor.BROWN, copyOf(STRIPPED_YEW_LOG)), true);
    public static final Block CLAWED_YEW_BLOCK = register("clawed_yew_log", new ClawedYewLog(() -> STRIPPED_YEW_LOG, MapColor.BROWN, copyOf(STRIPPED_YEW_LOG)), true);
    public static final Block YEW_WOOD = register("yew_wood", new YewLogBlock(() -> STRIPPED_YEW_WOOD, MapColor.BROWN, copyOf(STRIPPED_YEW_LOG)), true);
    public static final Block YEW_PLANKS = register("yew_planks", new Block(copyOf(Blocks.OAK_PLANKS)), true);
    public static final Block YEW_STAIRS = register("yew_stairs", new YewStairsBlock(YEW_PLANKS, copyOf(Blocks.OAK_STAIRS)), true);
    public static final Block YEW_SLAB = register("yew_slab", new SlabBlock(copyOf(Blocks.OAK_SLAB)), true);
    public static final Block YEW_FENCE = register("yew_fence", new FenceBlock(copyOf(Blocks.OAK_FENCE)), true);
    public static final Block YEW_FENCE_GATE = register("yew_fence_gate", new FenceGateBlock(AbstractBlock.Settings.of(Material.WOOD, YEW_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD), SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN), true);
    public static final Block YEW_LEAVES = register("yew_leaves", BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS), true);
    public static final Block YEW_SAPLING = register("yew_sapling", new YewSaplingBlock(1, "yew_tree", copyOf(Blocks.OAK_SAPLING)), true);
    public static final Block POTTED_YEW_SAPLING = register("potted_yew_sapling", new FlowerPotBlock(YEW_SAPLING, copyOf(Blocks.POTTED_OAK_SAPLING)), false);
    public static final Block YEW_PRESSURE_PLATE = register("yew_pressure_plate", new YewPressurePlateBlock(copyOf(Blocks.OAK_PRESSURE_PLATE)), true);
    public static final Block YEW_BUTTON = register("yew_button", new YewButtonBlock(), true);
    public static final Block YEW_TRAPDOOR = register("yew_trapdoor", new YewTrapdoorBlock(), true);
    public static final Block YEW_DOOR = register("yew_door", new YewDoorBlock(), false);
    public static final Block YEW_SIGN = register("yew_sign", new YewSignBlock(YEW_SIGN_TEXTURE, copyOf(Blocks.OAK_SIGN)), false);
    public static final Block YEW_WALL_SIGN = register("yew_wall_sign", new YewWallSignBlock(YEW_SIGN_TEXTURE, copyOf(Blocks.OAK_WALL_SIGN)), false);
    public static final Item YEW_DOOR_ITEM = register("yew_door", new TallBlockItem(YEW_DOOR, settings()));
    public static final Item YEW_SIGN_ITEM = register("yew_sign", new SignItem(settings().maxCount(16), YEW_SIGN, YEW_WALL_SIGN));

    public static final Block TOTEM_BLOCK = register("totem_block", new TotemBlock(QuiltBlockSettings.copyOf(Blocks.OAK_LOG)), true);
	public static final Block HELLS_GATE_BLOCK = register("hells_gate", new HellsGateBlock(QuiltBlockSettings.copyOf(Blocks.STONE)), true);


    private static QuiltItemSettings settings() {
        return new QuiltItemSettings();
    }

    private static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, Constants.id(name));
        return item;
    }

    public static <T extends Block> T register(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(Constants.MODID, name));
        if (createItem) {
            ITEMS.put(new BlockItem(block, settings()), BLOCKS.get(block));
        }
        return block;
    }

    public static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registries.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
    }
}
