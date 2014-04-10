package controllers;

import gui.GUI;
import sensors.MyColorSensor;
import sensors.MyLightSensor;
import lejos.nxt.ColorSensor.Color;
import motors.MotorController;

/**
 * This class calibrates the light and color sensors
 * 
 * @author Pim van Hespen <PimvanHespen@gmail.com>
 * @author Jacob Visser <Jacob.Visser@student.hu.nl>
 * @version 1.6
 * @since 02-04-2014
 * 
 *        This class calibrates both Light and MyColorSensor for further usage.
 */
public class CalibrationController {

	private MyColorSensor colorSensor; // /< MyColorSensor object.
	private MyLightSensor lightSensor; // /< LightSensor object.

	private final int CIRCLE = 360; // /< 360, the amount of degrees in a
									// circle, therefore called CIRCLE

	/**
	 * Constructor for the CalibrationController class.
	 * 
	 * @param myColorSensor
	 *            the MyColorSensor that has to be calibrated.
	 * @param myLightSensor
	 *            the Mylightsensor that has to be calibrated.
	 * @see MyColorSensor
	 * @see MyLightSensor
	 * 
	 */
	public CalibrationController(MyColorSensor myColorSensor,
			MyLightSensor myLightSensor, GUI gui) {
		LineFollowController.pauseLineFollower();
		this.colorSensor = myColorSensor;
		this.lightSensor = myLightSensor;

		gui.showPopUp("Calibrating");

		calibrateAllSensors();
		gui.cancelPopUp();
		LineFollowController.continueLineFollower();
	}

	/**
	 * This class will calibrate all given Sensors, as of now there are only two
	 * Sensors: one color sensor and one light sensor.
	 */
	public void calibrateAllSensors() {

		// ensure the flood light color form the MyColorSensor is red, then turn
		// on the lights from both sensors.
		colorSensor.setFloodlight(Color.RED);
		colorSensor.setFloodlight(true);
		lightSensor.setFloodlight(true);

		// integers used to store all the required values for the calibration.
		int colorLow = 1023, colorHigh = 0, lightLow = 1023, lightHigh = 0;
		int currentLightValue = 0, currentColorValue = 0;
		
		// Let the robot drive one 'CIRCLE' with a speed of 45 degrees per
		// second. Then, while the robot is moving, also start measuring raw
		// light values and add these values to their designated ArrayLists.
		MotorController.setRotateSpeed(100); // was 45
		MotorController.rotate(CIRCLE, true);

		while (MotorController.moving()) {

			currentLightValue = lightSensor.getNormalizedLightValue();
			currentColorValue = colorSensor.getRawLightValue();

			if (currentColorValue > colorHigh)
				colorHigh = currentColorValue;
			else if (currentColorValue < colorLow)
				colorLow = currentColorValue;
			if (currentLightValue > lightHigh)
				lightHigh = currentLightValue;
			else if (currentLightValue < lightLow)
				lightLow = currentLightValue;

		}

		// Set the highest and lowest measured values from the measurements as
		// new high and low for the sensors to calibrate them and give them
		// their new range.
		lightSensor.setHigh(lightHigh);
		lightSensor.setLow(lightLow);
		colorSensor.setHigh(colorHigh);
		colorSensor.setLow(colorLow);
	}

}
