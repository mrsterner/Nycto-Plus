package dev.mrsterner.nyctoplus.mixin;

import dev.mrsterner.nyctoplus.common.entity.ai.LeshonBrain;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "onBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;spawnBreakParticles(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"))
    private void alertLeshon(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci){
        if (state.isIn(Constants.Tags.GUARDED_BY_LESHON)) {
            LeshonBrain.onGuardedBlockInteracted(player, false);
        }
    }
}
