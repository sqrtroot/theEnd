package nxt;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;

/**
 * This class starts all required controllers to control the robot.
 * 
 * @author	 Jacob Visser 	<Jacob.Visser@student.hu.nl>
 * @author	 Pim van Hespen	<pimvanhespen@gmail.com>
 * @author	 Robert Bezem 	<Robert.Bezem@student.hu.nl>
 * @since 	 01-04-2014
 * @version	 2.0	
 * 
 * 
 */
public class Main {
	/**
	 * This method defines all sensors and starts the controllers
	 * 
	 * @param args incoming arguments from outside the code.
	 *  
	 */
	public static void main(String[] args) {
		//
		Button.waitForAnyPress();
		MyColorSensor cs = new MyColorSensor(SensorPort.S1,
				SensorPosition.Right);
		MyLightSensor ls = new MyLightSensor(SensorPort.S4, SensorPosition.Left);
		MyUltraSonicSensor us = new MyUltraSonicSensor(SensorPort.S2);

		GUI gui = new GUI();

		new CalibrationController(cs, ls, gui);
		new ObstructionController(cs, ls, us, gui);
		new FollowTheLine(cs, ls);
	}
}
