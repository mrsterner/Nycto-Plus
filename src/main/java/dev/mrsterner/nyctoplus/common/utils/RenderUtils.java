package dev.mrsterner.nyctoplus.common.utils;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.nyctoplus.client.registry.NPRenderLayers;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class RenderUtils {

	public static void renderPortalLayer(Identifier base, Matrix4f matrix4f, VertexConsumerProvider vertexConsumers, float sizeX, float sizeY, int light, int overlay, float[] rgba) {
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(getPortalEffect(base));

		vertexConsumer.m_rkxaaknb(matrix4f, 0, 5.01f, sizeY).color(rgba[0], rgba[1], rgba[2], rgba[3]).uv(0, 1).light(light).overlay(overlay).normal(0, 1, 0).next();
		vertexConsumer.m_rkxaaknb(matrix4f, sizeX, 5.01f, sizeY).color(rgba[0], rgba[1], rgba[2], rgba[3]).uv(1, 1).light(light).overlay(overlay).normal(0, 1, 0).next();
		vertexConsumer.m_rkxaaknb(matrix4f, sizeX, 5.01f, 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).uv(1, 0).light(light).overlay(overlay).normal(0, 1, 0).next();
		vertexConsumer.m_rkxaaknb(matrix4f, 0, 5.01f, 0).color(rgba[0], rgba[1], rgba[2], rgba[3]).uv(0, 0).light(light).overlay(overlay).normal(0, 1, 0).next();
	}

	public static RenderLayer getPortalEffect(Identifier texture) {
		return NPRenderLayers.PORT.apply(texture);
	}
}
