package nxt;

import java.util.ArrayList;
import lejos.nxt.ColorSensor.Color;

/**
 * @author Pim van Hespen <pim.vanhespen@hu.nl>
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 02-04-2014
 * 
 *        This class calibrates both Light and ColorSensor for further usage.
 */
public class CalibrationController {

	private ColorSensor cs; // /< ColorSensor object.
	private LightSensor ls; // /< LightSensor object.

	private ArrayList<Integer> lightValues; // /< ArrayList to be filled with
											// integers from readings of the
											// LightSensor
	private ArrayList<Integer> colorValues; // /< ArrayList to be filled with
											// integers from readings of the
											// ColorSensor

	private final int CIRCLE = 360; // /< 360, the amount of degrees in a
									// circle, therefore called CIRCLE
	private final int MEDIAN = 50; // /< 50 is the MEDIAN of 0 and 100. used to
									// distinguish between high and low values.

	/**
	 * Constructor for the CalibrationController class.
	 * 
	 * @param colorSensor
	 *            the ColorSensor that has to be calibrated.
	 * @param lightSensor
	 *            the LightSensor that has to be calibrated.
	 * @see ColorSensor
	 * @see LightSensor
	 * 
	 */
	public CalibrationController(ColorSensor colorSensor,
			LightSensor lightSensor) {
		LineFollowController.pauseLineFollowing();
		this.cs = colorSensor;
		this.ls = lightSensor;

		lightValues = new ArrayList<Integer>();
		colorValues = new ArrayList<Integer>();

		calibrateAllSensors();
		LineFollowController.continueLineFollowing();
	}

	/**
	 * This class will calibrate all given Sensors, as of now there are only two
	 * Sensors: one ColorSensor and one LightSensor.
	 */
	public void calibrateAllSensors() {

		// ensure the flood light color form the ColorSensor is red, then turn
		// on
		// the lights from both sensors.
		cs.setFloodlight(Color.RED);
		cs.setFloodlight(true);
		ls.setFloodlight(true);

		// Let the robot drive one 'CIRCLE' with a speed of 45 degrees per
		// second. Then, while the robot is moving, also start measuring raw
		// light values and add these values to their designated ArrayLists.
		MotorController.setRotateSpeed(45);
		MotorController.rotate(CIRCLE, true);

		while (MotorController.moving()) {
			lightValues.add(ls.getNormalizedLightValue());
			colorValues.add(cs.getRawLightValue());
		}

		// Set the highest and lowest measured values from the measurements as
		// new high and low for the sensors to calibrate them and give them
		// their new range.
		ls.setHigh(getHighestValue(lightValues));
		ls.setLow(getLowestValue(lightValues));
		cs.setHigh(getHighestValue(colorValues));
		cs.setLow(getLowestValue(colorValues));

		// Drive another circle until the black line is found.
		MotorController.rotate(CIRCLE, true);

		while (MotorController.moving()) {
			if (ls.getLightValue() < MEDIAN && cs.getLightValue() > MEDIAN) {
				MotorController.stop();
			}
		}
	}

	/**
	 * @param al
	 *            the given ArrayList to filter the highest value from.
	 * @return highestValue the highest value found in the given arrayList.
	 * @see ArrayList
	 */
	public int getHighestValue(ArrayList<Integer> al) {
		// Create an integer to store the highest value, then process all the
		// measurements stored in the ArrayList and compare them with the
		// current highest value. If the highest value in the 'highestValue' is
		// lower than the current highest value. Replace highest value with the
		// current value.
		int highestValue = 0;
		for (int currentValue : al) {
			if (currentValue > highestValue)
				highestValue = currentValue;
		}
		return highestValue;
	}

	/**
	 * @param al
	 *            the given ArrayList to filter the lowest value from.
	 * @return lowestValue the lowest value found in the given arrayList.
	 * @see ArrayList
	 */
	public int getLowestValue(ArrayList<Integer> al) {
		// Create an integer to store the lowest value, then process all the
		// measurements stored in the ArrayList and compare them with the
		// current lowest value. If the lowest value in the 'lowestValue' is
		// lower than the currentValue. Replace the lowest value with the
		// current value.
		int lowestValue = 1023;
		for (int currentValue : al) {
			if (currentValue < lowestValue)
				lowestValue = currentValue;
		}
		return lowestValue;
	}

}
