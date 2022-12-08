package dev.mrsterner.nyctoplus.client.model;

import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class LeshonEntityModel extends DefaultedEntityGeoModel<LeshonEntity> {


	public LeshonEntityModel() {
		super(Constants.id("leshon"));
	}

	@Override
    public Identifier getModelResource(LeshonEntity leshon) {
        return Constants.id("geo/leshon" + (leshon.getVariant() >= 10 ? "_nether" : "") + ".geo.json");
    }

    @Override
    public Identifier getTextureResource(LeshonEntity leshon) {
        return Constants.id("textures/entity/leshon/leshon_" + leshon.getVariant() + ".png");
    }

    @Override
    public Identifier getAnimationResource(LeshonEntity leshon) {
        return Constants.id("animations/leshon.animation.json");
    }

	/*
    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(LeshonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationZ(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }

	 */
}
