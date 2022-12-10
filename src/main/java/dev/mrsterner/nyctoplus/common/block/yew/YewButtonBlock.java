package dev.mrsterner.nyctoplus.common.block.yew;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

public class YewButtonBlock extends AbstractButtonBlock {
    public YewButtonBlock() {
        super(AbstractBlock.Settings.of(Material.DECORATION)
				.noCollision()
				.strength(0.5F)
				.sounds(BlockSoundGroup.WOOD),
				30,
				true,
				SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF,
				SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON);
    }

}
