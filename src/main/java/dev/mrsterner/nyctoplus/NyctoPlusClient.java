package dev.mrsterner.nyctoplus;

import dev.mrsterner.nyctoplus.client.model.DemonHornsModel;
import dev.mrsterner.nyctoplus.client.registry.NPSpriteIdentifierRegistry;
import dev.mrsterner.nyctoplus.client.renderer.LeshonEntityRenderer;
import dev.mrsterner.nyctoplus.client.renderer.blockentity.HellsGateBlockEntityRenderer;
import dev.mrsterner.nyctoplus.common.registry.NPBlockEntityTypes;
import dev.mrsterner.nyctoplus.common.registry.NPEntityTypes;
import dev.mrsterner.nyctoplus.common.registry.NPObjects;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;

public class NyctoPlusClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		NPSpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, NPObjects.YEW_SIGN_TEXTURE));
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),NPObjects.YEW_SAPLING, NPObjects.YEW_DOOR, NPObjects.YEW_TRAPDOOR, NPObjects.CLAWED_YEW_BLOCK);

		EntityRendererRegistry.register(NPEntityTypes.LESHON, LeshonEntityRenderer::new);

		EntityModelLayerRegistry.registerModelLayer(DemonHornsModel.LAYER_LOCATION, DemonHornsModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(DemonHornsModel.LAYER_LOCATION_2, DemonHornsModel::getTexturedModelData2);
		EntityModelLayerRegistry.registerModelLayer(DemonHornsModel.LAYER_LOCATION_3, DemonHornsModel::getTexturedModelData3);

		BlockEntityRendererRegistry.register(NPBlockEntityTypes.HELLS_GATE_BLOCK_ENTITY, HellsGateBlockEntityRenderer::new);

		ClientTickEvents.END.register(ClientTickHandler::clientTickEnd);

	}

	public static final class ClientTickHandler {
		private ClientTickHandler() {
		}

		public static int ticksInGame = 0;
		public static float partialTicks = 0;
		public static float delta = 0;
		public static float total = 0;

		public static void calcDelta() {
			float oldTotal = total;
			total = ticksInGame + partialTicks;
			delta = total - oldTotal;
		}

		public static void renderTick(float renderTickTime) {
			partialTicks = renderTickTime;
		}

		public static void clientTickEnd(MinecraftClient mc) {
			if (!mc.isPaused()) {
				ticksInGame++;
				partialTicks = 0;
			}
			calcDelta();
		}
	}
}
