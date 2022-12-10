package dev.mrsterner.nyctoplus.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.nyctoplus.client.model.LeshonEntityModel;
import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.math.Axis;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import javax.annotation.Nullable;

public class LeshonEntityRenderer extends DynamicGeoEntityRenderer<LeshonEntity> {
	private static final String LEFT_HAND = "bipedHandLeft";
	private static final String RIGHT_HAND = "bipedHandRight";
	protected ItemStack mainHandItem;
	protected ItemStack offhandItem;

    public LeshonEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LeshonEntityModel());
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {

			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, LeshonEntity animatable) {
				return switch (bone.getName()) {
					case LEFT_HAND -> animatable.isLeftHanded() ?
							LeshonEntityRenderer.this.mainHandItem : LeshonEntityRenderer.this.offhandItem;
					case RIGHT_HAND -> animatable.isLeftHanded() ?
							LeshonEntityRenderer.this.offhandItem : LeshonEntityRenderer.this.mainHandItem;
					default -> null;
				};
			}

			@Override
			protected ModelTransformation.Mode getTransformTypeForStack(GeoBone bone, ItemStack stack, LeshonEntity animatable) {
				return switch (bone.getName()) {
					case LEFT_HAND, RIGHT_HAND -> ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND;
					default -> ModelTransformation.Mode.NONE;
				};
			}

			@Override
			protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack, LeshonEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight, int packedOverlay) {
				if (stack == LeshonEntityRenderer.this.mainHandItem) {
					poseStack.multiply(Axis.X_POSITIVE.rotationDegrees(-90f));

					if (stack.getItem() instanceof ShieldItem)
						poseStack.translate(0, 0.125, -0.25);
				}
				else if (stack == LeshonEntityRenderer.this.offhandItem) {
					poseStack.multiply(Axis.X_POSITIVE.rotationDegrees(-90f));

					if (stack.getItem() instanceof ShieldItem) {
						poseStack.translate(0, 0.125, 0.25);
						poseStack.multiply(Axis.Y_POSITIVE.rotationDegrees(180));
					}
				}

				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
			}
		});
    }

	@Override
	public void preRender(MatrixStack poseStack, LeshonEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		super.preRender(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

		this.mainHandItem = animatable.getMainHandStack();
		this.offhandItem = animatable.getOffHandStack();
	}
}
