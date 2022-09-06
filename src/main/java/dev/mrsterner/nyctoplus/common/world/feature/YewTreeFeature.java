package dev.mrsterner.nyctoplus.common.world.feature;

import com.mojang.serialization.Codec;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import dev.mrsterner.nyctoplus.common.utils.WorldGenUtils;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class YewTreeFeature extends Feature<ProbabilityConfig> {
    public YewTreeFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeatureContext<ProbabilityConfig> context) {
        return WorldGenUtils.generateNbtFeature(Constants.id("yew_tree_0"), context.getWorld(), context.getOrigin(), 1);
    }


}