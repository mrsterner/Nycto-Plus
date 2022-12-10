package dev.mrsterner.nyctoplus.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SignBlockEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class SignBlockEntityRendererMixin {
	/*
    @ModifyVariable(method = "render*", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/util/SignType;)Lnet/minecraft/client/util/SpriteIdentifier;"))
    private SpriteIdentifier getSignTextureId(SpriteIdentifier spriteIdentifier, SignBlockEntity signBlockEntity) {
        if (signBlockEntity.getCachedState().getBlock() instanceof YewSign) {
            return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((YewSign) signBlockEntity.getCachedState().getBlock()).getTexture());
        }
        return spriteIdentifier;
    }

	 */
}
