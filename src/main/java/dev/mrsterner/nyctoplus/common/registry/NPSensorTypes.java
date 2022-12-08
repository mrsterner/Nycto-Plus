package dev.mrsterner.nyctoplus.common.registry;

import dev.mrsterner.nyctoplus.common.entity.ai.LeshonSpecificSourceSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class NPSensorTypes {
    public static final SensorType<LeshonSpecificSourceSensor> LESHON_SPECIFIC_SENSOR = register("leshon_specific", LeshonSpecificSourceSensor::new);


    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, new Identifier(id), new SensorType<>(factory));
    }

    public static void init() {

    }
}
