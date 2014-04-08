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
public class ObstructionController implements LightSensorListener,
		UltraSonicSensorListener {

	private int currentDistance;
	private int sensorValueLeft;
	private int sensorValueRight;

	private final int SAFE_DISTANCE = 20;
	private final int ARC_DEGREES = 360;
	private final int MEDIAN = 50;

	private boolean isExecuting = false;

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

		currentDistance = 255;
		sensorValueLeft = 0;
		sensorValueRight = 0;
	}

	/**
	 * This method checks the current distance to the object. Whenever the
	 * object distance is smaller than the safe distance, this method will
	 * perform a 360 degree circle around the found object and stops driving the
	 * circle when the line has been found again.
	 */
	private void evasiveManeuver() {

		boolean noLineFound = true;
		while (noLineFound) {

			FollowTheLine.pauseLineFollowing();
			MotorController.rotate(-90, false);
			MotorController.DriveArc((SAFE_DISTANCE * 10), ARC_DEGREES, true);

			while (MotorController.moving()) {

				if (sensorValueLeft < MEDIAN || sensorValueRight < MEDIAN)
					MotorController.stop();

			}

			noLineFound = false;
		}

		FollowTheLine.continueLineFollowing();
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

		currentDistance = (int) newValue;
		
		if (!isExecuting) {
			if (newValue < SAFE_DISTANCE) {
				Sound.beepSequence();
				Sound.buzz();
				Sound.beepSequenceUp();
				gui.showErrorPopUp("Object to close");
				evasiveManeuver();
			} else {
				gui.cancelPopUp();
			}

			
		}
	}

	/**
	 * If the measured value from the light sensor changes this method is
	 * called. When called this method checks the position of the sensor who
	 * called this method, and sets the attribute corresponding to the position.
	 * 
	 * @see sensors.LightSensorListener#lightSensorChanged(sensors.SensorPosition,
	 *      sensors.UpdatingSensor, float, float)
	 */
	@Override
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {
		if (position == SensorPosition.Left)
			sensorValueLeft = (int) newValue;
		else if (position == SensorPosition.Right)
			sensorValueRight = (int) newValue;
	}
}
