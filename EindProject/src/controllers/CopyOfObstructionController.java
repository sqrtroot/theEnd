package controllers;

import gui.GUI;
import sensors.LightSensorListener;
import sensors.MyColorSensor;
import sensors.MyLightSensor;
import sensors.MyUltraSonicSensor;
import sensors.Position;
import sensors.UltrasonicSensorListener;
import sensors.UpdatingSensor;
import lejos.nxt.Sound;
import motors.MotorController;

/**
 * This class will evade objects
 * 
 * @author Pim van Hespen <Pimvanhespen@gmail.com>
 * @version 1.2
 * @since 01-04-2014
 * 
 */
public class CopyOfObstructionController extends Thread implements
		LightSensorListener, UltrasonicSensorListener {

	private boolean evasive = false;
	private int currentDistance;
	private int sensorValueLeft;
	private int sensorValueRight;

	private final int SAFE_DISTANCE = 20;
	private final int ARC_DEGREES = 360;
	private final int MEDIAN = 50;

	private boolean noLineFound = true;
	private boolean isExecuting = false;

	private GUI gui;

	/**
	 * The constructor for the ObstructionController class
	 * 
	 * @param cs
	 *            The color sensor on the robot
	 * @param ls
	 *            The light sensor on the robot
	 * @param us
	 *            The ultrasonic sensor on the robot
	 * @param gui
	 *            The GUI that is used
	 */

	public CopyOfObstructionController(MyColorSensor cs, MyLightSensor ls,
			MyUltraSonicSensor us, GUI gui) {

		this.gui = gui;

		cs.addListener(this);
		ls.addListener(this);
		us.addListener(this);

		currentDistance = 255;
		sensorValueLeft = 0;
		sensorValueRight = 0;
		this.start();
	}

	public void run() {
		while (true) {
			if (isExecuting) {
				LineFollowController.pauseLineFollower();
				MotorController.setRotateSpeed(100);
				MotorController.rotate(-90, false);
				MotorController.DriveArc((SAFE_DISTANCE * 10), ARC_DEGREES,
						true);
				noLineFound = true;
				while (noLineFound) {
				}
				LineFollowController.continueLineFollower();
				gui.cancelPopUp();
				isExecuting = false;
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

	}

	/**
	 * If the measured value from the ultrasonic sensor changes this method is
	 * called. When called this method checks of the new value is smaller than
	 * the safe value, if that's true the robot plays sounds, displays a message
	 * and starts a evasive maneuver. When the new value is bigger than the save
	 * distance any messages on the display disappear.
	 * 
	 * @see sensors.UltrasonicSensorListener#ultraSonicChanged(sensors.UpdatingSensor,
	 *      float, float)
	 */
	@Override
	public void ultraSonicChanged(UpdatingSensor us, float oldValue,
			float newValue) {
		currentDistance = (int) newValue;
		if (newValue < SAFE_DISTANCE && !isExecuting) {
			gui.showErrorPopUp("Object to close");
			isExecuting = true;
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
	public void lightSensorChanged(Position position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {
		if (isExecuting) {
			if (position == Position.Left && newValue < MEDIAN) {
				noLineFound = false;
			} else if (position == Position.Right && newValue < MEDIAN) {
				noLineFound = false;

			}
		}

	}
}
