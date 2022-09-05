package dev.mrsterner.nyctoplus;

import dev.mrsterner.nyctoplus.common.registry.*;
import dev.mrsterner.nyctoplus.common.utils.Constants;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.GeckoLibMod;

public class NyctoPlus implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("NyctoPlus");

	@Override
	public void onInitialize(ModContainer mod) {
		GeckoLibMod.DISABLE_IN_DEV = true;
		MidnightConfig.init(Constants.MODID, NyctoPlusConfig.class);
		NPObjects.init();
		NPBlockEntityTypes.init();
		NPEntityTypes.init();
		NPSensorType.init();
		NPWorldGenerators.init();
	}
}
