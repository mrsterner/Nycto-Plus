package dev.mrsterner.nyctoplus.client.renderer.feature;

import dev.mrsterner.nyctoplus.client.model.DemonHornsModel;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DemonHornsFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
	private static final Identifier TEXTURE = Constants.id("textures/entity/demon/horns.png");
	private static DemonHornsModel MODEL;

	public DemonHornsFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context, EntityModelLoader loader) {
		super(context);
		MODEL = new DemonHornsModel(loader.getModelPart(DemonHornsModel.LAYER_LOCATION_3));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if(true){//TODO condition for demon transformation
			matrices.push();
			getContextModel().head.rotate(matrices);
			matrices.translate(0,0,0.1F);
			MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
			matrices.pop();
		}
	}
}
