package dev.mrsterner.nyctoplus;

import dev.mrsterner.nyctoplus.common.registry.*;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NyctoPlus implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("NyctoPlus");

	@Override
	public void onInitialize(ModContainer mod) {
		NPObjects.init();
		NPBlockEntityTypes.init();
		NPEntityTypes.init();
		NPWorldGenerators.init();
		NPSensorTypes.init();

	}
}
