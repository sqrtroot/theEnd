package nxt;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;

public class Main {
	public static void main(String[] args) {
		//
		Button.waitForAnyPress();
		ColorSensor cs = new ColorSensor(SensorPort.S1, Position.Left);
		LightSensor ls = new LightSensor(SensorPort.S2, Position.Right);
		UltraSonicSensor us = new UltraSonicSensor(SensorPort.S4);
		new CalibreerController(cs, ls);
		new LineFollowController(cs, ls);
		/*ArrayList<UpdatingSensor> sensors = new ArrayList<UpdatingSensor>();
		sensors.add(cs);
		sensors.add(ls);
		sensors.add(us);

		System.out.println(cs.getHigh());
		System.out.println(cs.getLow());
		sensors = new CalibreerController().calibreer(sensors);

		for (UpdatingSensor sensor : sensors) {
			if (sensor.getSensorType() == SensorType.Colorsensor) {
				cs = (ColorSensor) sensor;
			}
			if (sensor.getSensorType() == SensorType.Lightsensor) {
				ls = (LightSensor) sensor;

			}
		}
		System.out.println(cs.getHigh());
		System.out.println(cs.getLow());

		Button.waitForAnyPress();*/
	}
}
