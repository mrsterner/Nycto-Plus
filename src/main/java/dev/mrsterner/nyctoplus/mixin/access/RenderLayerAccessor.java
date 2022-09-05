package dev.mrsterner.nyctoplus.mixin.access;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(RenderLayer.class)
public interface RenderLayerAccessor {
    @Invoker("of")
    static RenderLayer.MultiPhase of(@SuppressWarnings("unused") String name, @SuppressWarnings("unused") VertexFormat vertexFormat, @SuppressWarnings("unused") VertexFormat.DrawMode drawMode, @SuppressWarnings("unused") int expectedBufferSize, @SuppressWarnings("unused") boolean hasCrumbling, @SuppressWarnings("unused") boolean translucent, @SuppressWarnings("unused") RenderLayer.MultiPhaseParameters phases) {
        throw new IllegalStateException("Mixin not transformed");
    }
}