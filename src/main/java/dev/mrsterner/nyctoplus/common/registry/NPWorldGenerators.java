package dev.mrsterner.nyctoplus.common.registry;

import dev.mrsterner.nyctoplus.common.utils.Constants;
import dev.mrsterner.nyctoplus.common.world.feature.YewTreeFeature;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.util.Holder;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModification;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;
import org.quiltmc.qsl.worldgen.biome.api.ModificationPhase;


public class NPWorldGenerators extends ConfiguredFeatureUtil {

    public static final Feature<ProbabilityConfig> YEW_TREE_FEATURE = registerFeature("yew_tree_feature", new YewTreeFeature(ProbabilityConfig.CODEC));
    public static final Holder<ConfiguredFeature<ProbabilityConfig, ?>> YEW_TREE_CONFIGURED_FEATURE = ConfiguredFeatureUtil.register("yew_tree_configured_feature", YEW_TREE_FEATURE, new ProbabilityConfig(0));


    public static final Holder<PlacedFeature> YEW_TREE_PLACED_FEATURE = PlacedFeatureUtil.register("yew_tree_placed",
            YEW_TREE_CONFIGURED_FEATURE,
            RarityFilterPlacementModifier.create(32),
            InSquarePlacementModifier.getInstance(),
            PlacedFeatureUtil.MOTION_BLOCKING_HEIGHTMAP,
            BiomePlacementModifier.getInstance());

    private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registry.FEATURE, name, feature);
    }



    public static void init() {
        BiomeModification worldGen = BiomeModifications.create(Constants.id("world_features"));
        worldGen.add(ModificationPhase.ADDITIONS,
                BiomeSelectors.isIn(ConventionalBiomeTags.EXTREME_HILLS).or(BiomeSelectors.isIn(ConventionalBiomeTags.MOUNTAIN)),
                context -> context.getGenerationSettings().addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, YEW_TREE_PLACED_FEATURE.value()));
    }
}
