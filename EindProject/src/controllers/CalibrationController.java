package controllers;

import gui.GUI;
import sensors.MyColorSensor;
import sensors.MyLightSensor;
import lejos.nxt.ColorSensor.Color;

/**
 * @author Pim van Hespen <pimvanhespen@gmail.com>
 * @version 1.2
 * @since 02-04-2014
 * 
 *        This class calibrates both Light and MyColorSensor for further usage.
 */
public class CalibrationController {

	private MyColorSensor cs; // /< MyColorSensor object.
	private MyLightSensor ls; // /< LightSensor object.

	private final int CIRCLE = 360; // /< 360, the amount of degrees in a
									// circle, therefore called CIRCLE
	private final int MEDIAN = 50; // /< 50 is the MEDIAN of 0 and 100. used to
									// distinguish between high and low values.

	/**
	 * Constructor for the CalibrationController class.
	 * 
	 * @param myColorSensor
	 *            the MyColorSensor that has to be calibrated.
	 * @param myLightSensor
	 *            the LightSensor that has to be calibrated.
	 * @see MyColorSensor
	 * @see MyLightSensor
	 * 
	 */
	public CalibrationController(MyColorSensor myColorSensor,
			MyLightSensor myLightSensor, GUI gui) {
		LineFollowController.pauseLineFollowing();
		this.cs = myColorSensor;
		this.ls = myLightSensor;

		gui.showPopUp("Calibrating");

		calibrateAllSensors();
		gui.cancelPopUp();
		LineFollowController.continueLineFollowing();
	}

	/**
	 * This class will calibrate all given Sensors, as of now there are only two
	 * Sensors: one MyColorSensor and one LightSensor.
	 */
	public void calibrateAllSensors() {

		// ensure the flood light color form the MyColorSensor is red, then turn
		// on the lights from both sensors.
		cs.setFloodlight(Color.RED);
		cs.setFloodlight(true);
		ls.setFloodlight(true);

		// Let the robot drive one 'CIRCLE' with a speed of 45 degrees per
		// second. Then, while the robot is moving, also start measuring raw
		// light values and add these values to their designated ArrayLists.
		MotorController.setRotateSpeed(45);
		MotorController.rotate(CIRCLE, true);

		// integers used to store all the required values for the calibration.
		int colorLow = 1023, colorHigh = 0, lightLow = 1023, lightHigh = 0;
		int currentLightValue = 0, currentColorValue = 0;
		
		while (MotorController.moving()) {
			
			currentLightValue = ls.getNormalizedLightValue();
			currentColorValue = cs.getRawLightValue();
			
			if(currentColorValue  > colorHigh)colorHigh = currentColorValue;
			else if(currentColorValue < colorLow)colorLow = currentColorValue;
			if(currentLightValue > lightHigh)lightHigh = currentLightValue;
			else if(currentLightValue < lightLow)lightLow = currentLightValue;
			
		}

		// Set the highest and lowest measured values from the measurements as
		// new high and low for the sensors to calibrate them and give them
		// their new range.
		ls.setHigh(lightHigh);
		ls.setLow(lightLow);
		cs.setHigh(colorHigh);
		cs.setLow(colorLow);

		// Drive another circle until the black line is found.
		MotorController.rotate(CIRCLE, true);

		while (MotorController.moving()) {
			if (ls.getLightValue() < MEDIAN && cs.getLightValue() > MEDIAN) {
				MotorController.stop();
			}
		}
	}

}
