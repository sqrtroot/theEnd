package nxt;

import gui.GUI;
import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import sensors.MyColorSensor;
import sensors.MyLightSensor;
import sensors.MyUltraSonicSensor;
import sensors.Position;
import controllers.CalibrationController;
import controllers.ObstructionController;
import controllers.LineFollowController;

/**
 * This class starts all required controllers to control the robot.
 * 
 * @author Jacob Visser <Jacob.Visser@student.hu.nl>
 * @author Pim van Hespen <PimvanHespen@gmail.com>
 * @author Robert Bezem <Robert.Bezem@student.hu.nl>
 * @since 01-04-2014
 * @version 2.0
 * 
 * 
 */
public class Main {
	/**
	 * This method defines all sensors and creates the controllers
	 * 
	 * @param args
	 *            incoming arguments from outside the code
	 * 
	 */
	public static void main(String[] args) {
		MyColorSensor cs = new MyColorSensor(SensorPort.S1, Position.Right);
		MyLightSensor ls = new MyLightSensor(SensorPort.S4, Position.Left);
		MyUltraSonicSensor us = new MyUltraSonicSensor(SensorPort.S2);

		GUI gui = new GUI();
		Button.waitForAnyPress();

		new CalibrationController(cs, ls, gui);
		new ObstructionController(cs, ls, us, gui);
		new LineFollowController(cs, ls, gui);
		Button.waitForAnyPress();
		for (int i = 0; i < 2; i++) {
			Sound.beepSequenceUp();
			Sound.beepSequence();
			
			
		}
		System.exit(1);

	}
}
