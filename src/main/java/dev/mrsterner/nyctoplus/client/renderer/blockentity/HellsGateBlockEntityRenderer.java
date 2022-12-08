package dev.mrsterner.nyctoplus.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import dev.mrsterner.nyctoplus.NyctoPlus;
import dev.mrsterner.nyctoplus.NyctoPlusClient;
import dev.mrsterner.nyctoplus.common.block.blockentity.HellsGateBlockEntity;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.block.entity.EndPortalBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class HellsGateBlockEntityRenderer<T extends HellsGateBlockEntity> implements BlockEntityRenderer<T> {
	/*
	public static final RenderLayer.MultiPhase SEEP =
			RenderLayerAccessor.satin$of(
					"seep",
					VertexFormats.POSITION,
					VertexFormat.DrawMode.QUADS,
					256,
					false,
					false,
					RenderLayer.MultiPhaseParameters.builder()
							.shader(new RenderPhase.Shader(() -> renderTypeSeepShader))
							.texture(RenderPhase.Textures.create()
									.add(Constants.id( "textures/environment/seep_0.png"), false, false)
									.add(Constants.id( "textures/environment/seep_1.png"), false, false)
									.build()).build(false));


	 */

	public HellsGateBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

	}


	@Override
	public void render(HellsGateBlockEntity endPortalBlockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		Matrix4f matrix4f = matrixStack.peek().getModel();
		this.renderSides(matrix4f, vertexConsumerProvider.getBuffer(this.getLayer()), tickDelta);
	}

	private void renderSides(Matrix4f matrix4f, VertexConsumer vertexConsumer, float tickDelta) {
		double ticks = (NyctoPlusClient.ClientTickHandler.ticksInGame + tickDelta) * 0.5;
		float deg =  (float) (ticks / 4 % 360F);
		//MathHelper.sin(deg) / (float) Math.PI

		this.renderSide(matrix4f, vertexConsumer,
				0.0F,
				1.0F,
				0.01F,
				0.01F,
				1 + MathHelper.sin(deg) / (float) Math.PI,
				1 + MathHelper.cos(deg) / (float) Math.PI,
				MathHelper.cos(deg) / (float) Math.PI - 1,
				MathHelper.sin(deg) / (float) Math.PI - 1,
				Direction.UP);
	}

	private void renderSide(Matrix4f matrix4f, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4, Direction side) {
		renderSide(matrix4f, vertices, 0,0);
		renderSide(matrix4f, vertices, 0.5f,-0.5f);
		renderSide(matrix4f, vertices, 1.5f,0.5f);
		renderSide(matrix4f, vertices, -0.5f,0.5f);
		renderSide(matrix4f, vertices, 0,1);
		/*

		renderSide(matrix4f, vertices, 0.1f,0.3f);
		renderSide(matrix4f, vertices, 0.6f,1);
		renderSide(matrix4f, vertices, 0.2f,2);
		renderSide(matrix4f, vertices, 0.5f,2.4f);
		renderSide(matrix4f, vertices, 0.3f,2.5f);

		renderSide(matrix4f, vertices, 0,3);

		renderSide(matrix4f, vertices, -0.2f,2.6f);
		renderSide(matrix4f, vertices, -0.6f,2);
		renderSide(matrix4f, vertices, -0.2f,1.5f);
		renderSide(matrix4f, vertices, -0.7f,1);
		renderSide(matrix4f, vertices, -0.3f,0.5f);

		 */

	}

	private void renderSide(Matrix4f matrix4f, VertexConsumer vertices, float x, float z){
		vertices.m_rkxaaknb(matrix4f, x, 0.01F, z).next();
	}


	protected RenderLayer getLayer() {
		return RenderLayer.getEndPortal();
	}
}
