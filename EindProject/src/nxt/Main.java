package nxt;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;

public class Main {
	public static void main(String[] args) {
		//
		Button.waitForAnyPress();
		ColorSensor cs = new ColorSensor(SensorPort.S1, SensorPosition.Left);
		LightSensor ls = new LightSensor(SensorPort.S2, SensorPosition.Right);
		UltraSonicSensor us = new UltraSonicSensor(SensorPort.S4);
		new CalibrationController(cs, ls);
		new LineFollowController(cs, ls);
	}
}
