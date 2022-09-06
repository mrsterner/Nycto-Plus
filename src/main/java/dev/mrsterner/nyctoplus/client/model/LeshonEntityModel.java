package dev.mrsterner.nyctoplus.client.model;

import dev.mrsterner.nyctoplus.common.entity.LeshonEntity;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LeshonEntityModel extends AnimatedGeoModel<LeshonEntity> {

    @Override
    public Identifier getModelResource(LeshonEntity object) {
        return Constants.id("geo/leshon.geo.json");
    }

    @Override
    public Identifier getTextureResource(LeshonEntity object) {
        return Constants.id("textures/entity/leshon/leshon.png");
    }

    @Override
    public Identifier getAnimationResource(LeshonEntity animatable) {
        return Constants.id("animations/leshon.animation.json");
    }
}
