package dev.mrsterner.nyctoplus;

import dev.mrsterner.nyctoplus.client.registry.NPSpriteIdentifierRegistry;
import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NyctoPlusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		NPSpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, NPObjects.YEW_SIGN.getTexture()));
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),NPObjects.YEW_SAPLING, NPObjects.YEW_DOOR, NPObjects.YEW_TRAPDOOR, NPObjects.YEW_CUT_LOG);
	}
}
