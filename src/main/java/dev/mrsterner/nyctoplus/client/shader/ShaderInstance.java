package dev.mrsterner.nyctoplus.client.shader;

import net.minecraft.client.render.ShaderProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class ShaderInstance {
    public ShaderProgram instance;
    public final ArrayList<String> uniforms;
    public final ArrayList<ExtendedShader.UniformData> defaultUniformData = new ArrayList<>();

    public ShaderInstance(String... uniforms) {
        this.uniforms = new ArrayList<>(List.of(uniforms));
    }

    public Supplier<ShaderProgram> getInstance() {
        return () -> instance;
    }

    public void setInstance(ShaderProgram instance) {
        this.instance = instance;
    }
}