package dev.mrsterner.nyctoplus.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.nyctoplus.client.registry.NPRenderLayers;
import dev.mrsterner.nyctoplus.common.block.blockentity.HellsGateBlockEntity;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import dev.mrsterner.nyctoplus.common.utils.RenderUtils;
import dev.mrsterner.nyctoplus.common.utils.Quint;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class HellsGateBlockEntityRenderer<T extends HellsGateBlockEntity> implements BlockEntityRenderer<T> {
	List<Quint<Float, Float, Integer, Integer>> list = new ArrayList<>();


	public HellsGateBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		list.add(new Quint<>(3.0F, 0.125F, 0,1));
		list.add(new Quint<>(2.5F, 0.6F, 1,1));
		list.add(new Quint<>(2.75F, 1.0F, 1,0));
		list.add(new Quint<>(3.5F, 0.75F, 0,0));

		list.add(new Quint<>(2.75F, 1.0F, 0,1));
		list.add(new Quint<>(0.5F, 1.5F, 1,1));
		list.add(new Quint<>(2.3F, 3.0F, 1,0));
		list.add(new Quint<>(3.3F, 2.25F, 0,0));

		list.add(new Quint<>(2.3F, 3.0F, 0,1));
		list.add(new Quint<>(1.1F, 3.5F, 1,1));
		list.add(new Quint<>(2.5F, 4.3F, 1,0));
		list.add(new Quint<>(2.8F, 3.75F, 0,0));

		list.add(new Quint<>(2.8F, 3.75F, 0,1));
		list.add(new Quint<>(2.5F, 4.3F, 1,1));
		list.add(new Quint<>(3.0F, 5.75F, 1,0));
		list.add(new Quint<>(3.2F, 4.2F, 0,0));

		list.add(new Quint<>(2.8F, 3.75F, 0,1));
		list.add(new Quint<>(4F, 3F, 1,1));
		list.add(new Quint<>(3.3F, 2.25F, 1,0));
		list.add(new Quint<>(2.3F, 3F, 0,0));

		list.add(new Quint<>(3.3F, 2.25F, 0,1));
		list.add(new Quint<>(5F, 1F, 1,1));
		list.add(new Quint<>(3.5F, 3/4F, 1,0));
		list.add(new Quint<>(2.75F, 1F, 0,0));
	}

	public static final Identifier DEFAULT_MASK = Constants.id("textures/block/mask/hells_gate_mask.png");
	@Override
	public void render(HellsGateBlockEntity endPortalBlockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		Matrix4f matrix4f = matrixStack.peek().getModel();
		float[] colors = {1F, 0.2F, 0.2F, 1};
		if(true){
			renderVertices(matrix4f, vertexConsumerProvider.getBuffer(NPRenderLayers.PORT.apply(EndPortalBlockEntityRenderer.SKY_TEXTURE)), colors, light, overlay);
		}else {
			Identifier mask = DEFAULT_MASK;
			matrixStack.push();
			matrixStack.translate(0, 1F, 0);
			RenderUtils.renderPortalLayer(mask, matrix4f, vertexConsumerProvider, 6F, 6F, light, overlay, colors);
			matrixStack.pop();

		}
	}



	private void renderVertices(Matrix4f matrix4f, VertexConsumer vertexConsumer, float[] colors,  int light, int overlay) {


		//1
		/*
		renderSide(matrix4f, vertexConsumer, light, overlay,0,1, colors, 3.0F, 0.125F);
		renderSide(matrix4f, vertexConsumer, light, overlay,1,1, colors,2.5F, 3.0F/5);
		renderSide(matrix4f, vertexConsumer, light, overlay,1,0, colors,2.75F, 1.0F);
		renderSide(matrix4f, vertexConsumer, light, overlay,0,0, colors, 3.5F, 3.0F/4);

		 */

		/*
		//2
		renderSide(matrix4f, vertexConsumer,light, overlay,0,1, colors, 2.75F, 1.0F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,1, colors,0.5F, 1.5F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,0, colors,2.3F, 3.0F);
		renderSide(matrix4f, vertexConsumer, light, overlay,0,0,colors, 3.3F, 2.25F);


		 */
		/*
		//3
		renderSide(matrix4f, vertexConsumer,light, overlay,0,1, colors, 2.3F, 3.0F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,1, colors,1.1F, 3.5F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,0, colors,2.5F, 4.3F);
		renderSide(matrix4f, vertexConsumer, light, overlay,0,0,colors, 2.8F, 3.75F);


		 */
		//4
		/*
		renderSide(matrix4f, vertexConsumer,light, overlay,0,1, colors, 2.8F, 3.75F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,1, colors,2.5F, 4.3F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,0, colors,3.0F, 5.75F);
		renderSide(matrix4f, vertexConsumer, light, overlay,0,0,colors, 3.2F, 4.2F);


		 */
		/*
		//5
		renderSide(matrix4f, vertexConsumer,light, overlay,0,1, colors, 2.8F, 3.75F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,1, colors,4F, 3F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,0, colors,3.3F, 2.25F);
		renderSide(matrix4f, vertexConsumer, light, overlay,0,0,colors, 2.3F, 3F);

		//6
		renderSide(matrix4f, vertexConsumer,light, overlay,0,1, colors, 3.3F, 2.25F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,1, colors,5F, 1F);
		renderSide(matrix4f, vertexConsumer,light, overlay,1,0, colors,3.5F, 3/4F);
		renderSide(matrix4f, vertexConsumer, light, overlay,0,0,colors, 2.75F, 1F);

		 */



		list.forEach(l -> {
			float[] rgba = {1F, 0.2F, 0.2F, 0.5f};
			renderSide(matrix4f, vertexConsumer, l.getU(), l.getV(), light, overlay, rgba, l.getX(), l.getZ());
		});
	}

	private void renderSide(Matrix4f matrix4f, VertexConsumer vertexConsumer, int u, int v,  int light, int overlay, float[] rgba, float x, float z) {
		vertexConsumer.m_rkxaaknb(matrix4f, x, 0.001F, z).color(rgba[0], rgba[1], rgba[2], rgba[3]).uv(u, v).light(light).overlay(overlay).normal(0, 1, 0).next();
	}
}
