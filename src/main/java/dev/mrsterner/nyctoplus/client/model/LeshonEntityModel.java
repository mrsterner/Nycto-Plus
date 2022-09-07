package dev.mrsterner.nyctoplus.client.model;

import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class LeshonEntityModel extends AnimatedGeoModel<LeshonEntity> {

    @Override
    public Identifier getModelResource(LeshonEntity object) {
        return Constants.id("geo/leshon.geo.json");
    }

    @Override
    public Identifier getTextureResource(LeshonEntity object) {
        return Constants.id("textures/entity/leshon/leshon_0.png");
    }

    @Override
    public Identifier getAnimationResource(LeshonEntity animatable) {
        return Constants.id("animations/leshon.animation.json");
    }

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
}
