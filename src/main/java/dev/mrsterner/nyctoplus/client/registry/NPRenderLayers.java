package dev.mrsterner.nyctoplus.client.registry;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.RenderLayerHelper;
import ladysnake.satin.mixin.client.render.RenderLayerAccessor;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class NPRenderLayers extends RenderLayer {
    public NPRenderLayers(String string, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, drawMode, i, bl, bl2, runnable, runnable2);
    }

    public static RenderLayer makeLayer(String name, VertexFormat format, VertexFormat.DrawMode mode, int bufSize, boolean hasCrumbling, boolean sortOnUpload, RenderLayer.MultiPhaseParameters glState) {
        return RenderLayerAccessor.satin$of(name, format, mode, bufSize, hasCrumbling, sortOnUpload, glState);
    }


    public static final Function<Identifier, RenderLayer> DUMMY = Util.memoize(texture -> {
        RenderLayer.MultiPhaseParameters glState = RenderLayer.MultiPhaseParameters.builder()
                .shader(new RenderPhase.Shader(NPShaders.DUMMY.getInstance()))
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .cull(DISABLE_CULLING)
                .lightmap(RenderLayer.ENABLE_LIGHTMAP)
                .overlay(RenderLayer.ENABLE_OVERLAY_COLOR)
                .build(true);
        return makeLayer(Constants.MODID + "dummy", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, glState);
    });

	public static final Function<Identifier, RenderLayer> PORT = Util.memoize(texture -> {
		RenderLayer.MultiPhaseParameters glState = RenderLayer.MultiPhaseParameters.builder()
				.shader(new RenderPhase.Shader(NPShaders.PORTAL.getInstance()))
				.texture(RenderPhase.Textures.create().add(texture, false, false)
						.add(EndPortalBlockEntityRenderer.PORTAL_TEXTURE, false, false).build())
				.lightmap(ENABLE_LIGHTMAP)
				.transparency(TRANSLUCENT_TRANSPARENCY)
				.target(TRANSLUCENT_TARGET)
				.build(true);
		return makeLayer(Constants.MODID + "portal", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, glState);
	});
}
