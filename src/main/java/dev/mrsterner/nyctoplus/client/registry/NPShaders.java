package dev.mrsterner.nyctoplus.client.registry;

import com.mojang.blaze3d.vertex.VertexFormats;
import com.mojang.datafixers.util.Pair;
import dev.mrsterner.nyctoplus.client.shader.ExtendedShader;
import dev.mrsterner.nyctoplus.client.shader.ShaderInstance;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.resource.ResourceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NPShaders {

    public static List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shaderList;
    public static final ShaderInstance DUMMY = new ShaderInstance("");
	public static final ShaderInstance PORTAL = new ShaderInstance("");

    public static void init(ResourceFactory factory) throws IOException {
        shaderList = new ArrayList<>();
        //registerShader(ExtendedShader.createShaderInstance(DUMMY, factory, Constants.id("eoe__dummy"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
		registerShader(ExtendedShader.createShaderInstance(PORTAL, factory, Constants.id("portal"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL));

    }

    public static void registerShader(ExtendedShader extendedShaderInstance) {
        shaderList.add(Pair.of(extendedShaderInstance, (shader) -> ((ExtendedShader) shader).getHolder().setInstance(shader)));
    }
}
