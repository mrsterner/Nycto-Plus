package dev.mrsterner.nyctoplus;

import dev.mrsterner.nyctoplus.client.registry.NPSpriteIdentifierRegistry;
import dev.mrsterner.nyctoplus.client.renderer.LeshonEntityRenderer;
import dev.mrsterner.nyctoplus.common.registry.NPEntityTypes;
import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class NyctoPlusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		NPSpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, NPObjects.YEW_SIGN.getTexture()));
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),NPObjects.YEW_SAPLING, NPObjects.YEW_DOOR, NPObjects.YEW_TRAPDOOR, NPObjects.YEW_CUT_LOG);

		EntityRendererRegistry.register(NPEntityTypes.LESHON, LeshonEntityRenderer::new);
	}
}
