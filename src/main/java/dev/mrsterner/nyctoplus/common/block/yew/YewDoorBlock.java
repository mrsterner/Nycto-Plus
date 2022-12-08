package dev.mrsterner.nyctoplus.common.block.yew;

import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class YewDoorBlock extends DoorBlock {
    public YewDoorBlock() {
        super(AbstractBlock.Settings.of(Material.WOOD,
						NPObjects.YEW_PLANKS.getDefaultMapColor()).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque(),
				SoundEvents.BLOCK_WOODEN_DOOR_CLOSE,
				SoundEvents.BLOCK_WOODEN_DOOR_OPEN);
    }
}
