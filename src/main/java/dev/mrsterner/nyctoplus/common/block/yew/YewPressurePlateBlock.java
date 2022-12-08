package dev.mrsterner.nyctoplus.common.block.yew;

import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class YewPressurePlateBlock extends PressurePlateBlock {
    public YewPressurePlateBlock(Settings settings) {
        super(
				PressurePlateBlock.ActivationRule.EVERYTHING,
				AbstractBlock.Settings.of(Material.WOOD, NPObjects.YEW_PLANKS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD),
				SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF,
				SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON
		);
    }
}
