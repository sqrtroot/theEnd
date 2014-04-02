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
	}
}
