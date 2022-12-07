package dev.mrsterner.nyctoplus.client.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;

public class DemonHornsModel extends Model {
	public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Constants.id("demon_horns"), "main");
	private final ModelPart armorHead;
	public DemonHornsModel(ModelPart root) {
		super(RenderLayer::getEntityTranslucent);
		armorHead = root.getChild("armorHead");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = new ModelData();
		ModelPartData root = data.getRoot();

		ModelPartData armorHead = root.addChild("armorHead", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.8F, 0.1F));
		ModelPartData leftHorn = armorHead.addChild("leftHorn", ModelPartBuilder.create().uv(12, 16).cuboid(-1.5F, -4.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.1F, -7.5F, -2.2F, 0.5236F, 0.2618F, 0.3927F));
		ModelPartData bone2 = leftHorn.addChild("bone2", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -4.5F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, -4.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
		ModelPartData bone3 = bone2.addChild("bone3", ModelPartBuilder.create().uv(0, 24).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, -3.5F, -0.2F, -0.4363F, 0.0F, 0.0F));
		ModelPartData rightHorn = armorHead.addChild("rightHorn", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5F, -4.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-2.1F, -7.5F, -2.2F, 0.5236F, -0.2618F, -0.3927F));
		ModelPartData bone4 = rightHorn.addChild("bone4", ModelPartBuilder.create().uv(22, 22).cuboid(-1.0F, -4.5F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, -4.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
		ModelPartData bone5 = bone4.addChild("bone5", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(-0.01F)), ModelTransform.of(0.0F, -3.5F, -0.2F, -0.4363F, 0.0F, 0.0F));

		return TexturedModelData.of(data, 32, 32);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		armorHead.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}
