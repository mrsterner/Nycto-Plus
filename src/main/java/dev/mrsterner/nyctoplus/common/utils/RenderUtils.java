package dev.mrsterner.nyctoplus.common.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.mrsterner.nyctoplus.client.shader.ShaderInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;


public class RenderUtils {
    public static void blit(MatrixStack stack, ShaderInstance shader, int x, int y, double width, double height, float r, float g, float b, float a, float u, float v, float sizeX, float sizeY) {
        Matrix4f last = stack.peek().getModel();
        RenderSystem.setShader(shader.getInstance());
        BufferBuilder bufferbuilder = MinecraftClient.getInstance().getBufferBuilders().getBlockBufferBuilders().get(RenderLayer.getTranslucent());
        bufferbuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferbuilder.vertex(last, (float) x, (float) y + (float) height, 0).color(r, g, b, a).uv(u, v + (float)(height / sizeY)).next();
        bufferbuilder.vertex(last, (float) x + (float) width, (float) y + (float) height, 0).color(r, g, b, a).uv(u + (float)(width / sizeX), v + (float)(height / sizeY)).next();
        bufferbuilder.vertex(last, (float) x + (float) width, (float) y, 0).color(r, g, b, a).uv(u + (float)(width / sizeX), v).next();
        bufferbuilder.vertex(last, (float) x, (float) y, 0).color(r, g, b, a).uv(u, v).next();
        BufferRenderer.drawWithShader(bufferbuilder.end());
    }
}
