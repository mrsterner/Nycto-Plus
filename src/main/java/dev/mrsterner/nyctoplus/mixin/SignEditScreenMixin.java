package dev.mrsterner.nyctoplus.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SignEditScreen.class)
@Environment(EnvType.CLIENT)
public class SignEditScreenMixin {
	/*
    @Shadow
    @Final
    private SignBlockEntity sign;

    @ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/util/SignType;)Lnet/minecraft/client/util/SpriteIdentifier;"))
    private SpriteIdentifier getSignTextureId(SpriteIdentifier spriteIdentifier) {
        if (sign.getCachedState().getBlock() instanceof YewSign) {
            return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ((YewSign) sign.getCachedState().getBlock()).getTexture());
        }
        return spriteIdentifier;
    }

	 */
}
