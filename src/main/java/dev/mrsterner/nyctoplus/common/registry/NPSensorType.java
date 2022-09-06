package dev.mrsterner.nyctoplus.common.registry;

import dev.mrsterner.nyctoplus.common.entity.ai.LeshonEntitySensor;
import dev.mrsterner.nyctoplus.mixin.access.SensorTypeAccessor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;

public class NPSensorType<U extends Sensor<?>>{
    public static final SensorType<LeshonEntitySensor> LESHON_ENTITY_SENSOR = SensorTypeAccessor.callRegister("leshon_entity_sensor", LeshonEntitySensor::new);

    public static void init() {
    }
}
