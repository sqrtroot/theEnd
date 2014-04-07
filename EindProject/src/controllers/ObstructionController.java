package controllers;

import gui.GUI;
import sensors.LightSensorListener;
import sensors.MyColorSensor;
import sensors.MyLightSensor;
import sensors.MyUltraSonicSensor;
import sensors.SensorPosition;
import sensors.UltraSonicSensorListener;
import sensors.UpdatingSensor;
import lejos.nxt.Sound;

/**
 * @author Pim van Hespen <Pimvanhespen@gmail.com>
 * @version 1.2
 * @since 01-04-2014
 * 
 *        This is class will evade objects
 * 
 */
public class ObstructionController extends Thread implements
		LightSensorListener, UltraSonicSensorListener {

	private int current_distance;
	private int sensor_value_left;
	private int sensor_value_right;

	private final int SAFE_DISTANCE = 20;
	private final int ARC_DEGREES = 360;
	private final int MEDIAN = 50;

	private GUI gui;

	/**
	 * The constructor for the ObstructionController class
	 * 
	 * @param cs
	 *            The colorsensor on the robot
	 * @param ls
	 *            The lightsensor on the robot
	 * @param us
	 *            The ultrasonicsensor on the robot
	 * @param gui
	 *            The gui that is used
	 */

	public ObstructionController(MyColorSensor cs, MyLightSensor ls,
			MyUltraSonicSensor us, GUI gui) {

		this.gui = gui;

		cs.addListener(this);
		ls.addListener(this);
		us.addListener(this);

		current_distance = 255;
		sensor_value_left = 0;
		sensor_value_right = 0;
	}

	/**
	 * Is called upon by Obstructioncontroller.start. This will run the
	 * controller in a new Thread. If the robot encounters an object within the
	 * given safe distance, the loop will call upon the evasiveManeuver method
	 * to bypass any object.
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (true) {
			MotorController.driveForward();
			if (current_distance < SAFE_DISTANCE) {
				evasiveManeuver();
			}
		}

	}

	/**
	 * This method checks the current distance to the object. Whenever the
	 * object distance is smaller than the safe distance, this method will
	 * perform a 360 degree circle around the found object and stops driving the
	 * circle when the line has been found again.
	 */
	private void evasiveManeuver() {

		MotorController.driveForward();
		boolean noLineFound = true;
		while (noLineFound) {

			if (current_distance < SAFE_DISTANCE) {
				LineFollowController.pauseLineFollowing();
				MotorController.rotate(-90, false);
				MotorController.DriveArc((SAFE_DISTANCE * 10), ARC_DEGREES,
						true);

				while (MotorController.moving()) {

					if (sensor_value_left < MEDIAN // try to find the line
							|| sensor_value_right < MEDIAN)
						MotorController.stop();

				}

				noLineFound = false;

			}

		}

	}

	/**
	 * If the measured value from the ultrasonic sensor changes this method is
	 * called. When called this method checks of the new value is smaller than
	 * the safe value, if that's true the robot plays sounds, displays a message
	 * and starts a evasive maneuver. When the new value is bigger than the save
	 * distance any messages on the display disappear.
	 * 
	 * @see sensors.UltraSonicSensorListener#ultraSonicChanged(sensors.UpdatingSensor,
	 *      float, float)
	 */
	@Override
	public void ultraSonicChanged(UpdatingSensor us, float oldValue,
			float newValue) {

		if (newValue < SAFE_DISTANCE) {
			Sound.beepSequence();
			Sound.buzz();
			Sound.beepSequenceUp();
			gui.showErrorPopUp("Object to close");
			evasiveManeuver();
		} else {
			gui.cancelPopUp();
		}

		current_distance = (int) newValue;

	}

	/**
	 * If the measured value from the lightsensor changes this method is called.
	 * When called this method checks the position of the sensor who called this
	 * method, and sets the attribute corresponding to the position.
	 * 
	 * @see sensors.LightSensorListener#lightSensorChanged(sensors.SensorPosition,
	 *      sensors.UpdatingSensor, float, float)
	 */
	@Override
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {
		if (position == SensorPosition.Left)
			sensor_value_left = (int) newValue;
		else if (position == SensorPosition.Right)
			sensor_value_right = (int) newValue;
	}
}
