package dev.mrsterner.nyctoplus.mixin;

import com.mojang.blaze3d.shader.ShaderStage;
import com.mojang.datafixers.util.Pair;
import dev.mrsterner.nyctoplus.client.registry.NPShaders;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
final class GameRendererMixin {

	@Inject(method = "loadShaders", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	private void eyesOfEnder$registerShaders(ResourceFactory factory, CallbackInfo ci, List<ShaderStage> list, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> list2) throws IOException {
		NPShaders.init(factory);
		list2.addAll(NPShaders.shaderList);
	}
}
