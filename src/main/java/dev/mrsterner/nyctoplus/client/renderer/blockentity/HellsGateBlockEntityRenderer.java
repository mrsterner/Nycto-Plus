package dev.mrsterner.nyctoplus.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.nyctoplus.client.registry.NPRenderLayers;
import dev.mrsterner.nyctoplus.common.block.blockentity.HellsGateBlockEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class HellsGateBlockEntityRenderer<T extends HellsGateBlockEntity> implements BlockEntityRenderer<T> {
	List<Pair<Float, Float>> points = new ArrayList<>();


	public HellsGateBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		points.add(new Pair<>(3.0F, 0.125F));
		points.add(new Pair<>(2.5F, 0.6F));
		points.add(new Pair<>(2.75F, 1.0F));
		points.add(new Pair<>(3.5F, 0.75F));

		points.add(new Pair<>(2.75F, 1.0F));
		points.add(new Pair<>(0.5F, 1.5F));
		points.add(new Pair<>(2.3F, 3.0F));
		points.add(new Pair<>(3.3F, 2.25F));

		points.add(new Pair<>(2.3F, 3.0F));
		points.add(new Pair<>(1.1F, 3.5F));
		points.add(new Pair<>(2.5F, 4.3F));
		points.add(new Pair<>(2.8F, 3.75F));

		points.add(new Pair<>(2.8F, 3.75F));
		points.add(new Pair<>(2.5F, 4.3F));
		points.add(new Pair<>(3.0F, 5.75F));
		points.add(new Pair<>(3.2F, 4.2F));

		points.add(new Pair<>(2.8F, 3.75F));
		points.add(new Pair<>(4F, 3F));
		points.add(new Pair<>(3.3F, 2.25F));
		points.add(new Pair<>(2.3F, 3F));

		points.add(new Pair<>(3.3F, 2.25F));
		points.add(new Pair<>(5F, 1F));
		points.add(new Pair<>(3.5F, 3/4F));
		points.add(new Pair<>(2.75F, 1F));
	}

	@Override
	public void render(HellsGateBlockEntity endPortalBlockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		matrixStack.push();
		Matrix4f matrix4f = matrixStack.peek().getModel();
		renderVertices(endPortalBlockEntity,tickDelta, matrix4f, vertexConsumerProvider.getBuffer(NPRenderLayers.PORT.apply(EndPortalBlockEntityRenderer.SKY_TEXTURE)), light, overlay, false);
		matrixStack.pop();
	}

	public static float QuadEaseInOut(float time) {
		return time < 0.5f ? 2.0f * time * time : -1.0f + (4.0f - 2.0f * time) * time;
	}

	private void renderVertices(HellsGateBlockEntity endPortalBlockEntity, float tickDelta, Matrix4f matrix4f, VertexConsumer vertexConsumer,  int light, int overlay, boolean outer) {
		float[] rgba = {1F, 0.2F, 0.2F, outer ? 0.75f : 1.0f};
		for(Pair<Float, Float> pair : points){
			float g = ((float)endPortalBlockEntity.age + tickDelta - 1.0F) / 20.0F * 1.6F;
			if (g >= 1.0F) {
				g = 1.0F;
			}else{
				g = QuadEaseInOut(g);
			}
			float swiftyX = (pair.getA() * g) - 3 * g;
			vertexConsumer.m_rkxaaknb(matrix4f,  swiftyX, outer ? 0.01F : 0.001F, pair.getB()).color(rgba[0], rgba[1], rgba[2], rgba[3]).uv(0, 1).light(light).overlay(overlay).normal(0, 1, 0).next();
		}
	}
}
