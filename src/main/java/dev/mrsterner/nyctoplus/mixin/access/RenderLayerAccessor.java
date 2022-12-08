package dev.mrsterner.nyctoplus.mixin.access;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderLayer.class)
public interface RenderLayerAccessor {
	@Accessor
	boolean isTranslucent();

	@Invoker("of")
	static RenderLayer.MultiPhase of(@SuppressWarnings("unused") String name, @SuppressWarnings("unused") VertexFormat vertexFormat, @SuppressWarnings("unused") VertexFormat.DrawMode drawMode, @SuppressWarnings("unused") int expectedBufferSize, @SuppressWarnings("unused") boolean hasCrumbling, @SuppressWarnings("unused") boolean translucent, @SuppressWarnings("unused") RenderLayer.MultiPhaseParameters phases) {
		throw new IllegalStateException("Mixin not transformed");
	}
}
