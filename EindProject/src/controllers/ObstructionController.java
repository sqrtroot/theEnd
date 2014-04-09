package controllers;

import gui.GUI;
import sensors.LightSensorListener;
import sensors.MyColorSensor;
import sensors.MyLightSensor;
import sensors.MyUltraSonicSensor;
import sensors.Position;
import sensors.UltrasonicSensorListener;
import sensors.UpdatingSensor;
import motors.MotorController;

/**
 * This class will evade objects
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.2
 * @since 01-04-2014
 * 
 */
public class ObstructionController implements LightSensorListener,
		UltrasonicSensorListener, Runnable {

	private boolean firstEvasion = true;

	private final int ROTATE_SPEED = 100;
	private final int SAFE_DISTANCE = 20;
	private final int MEDIAN = 50;
	private final int RADIUS = 500;
	private final int FIRST_TURN_ANGLE = -240;
	private final int SECOND_TURN_ANGLE = -50;

	private boolean noLineFound = true;
	private boolean isExecuting = false;

	private Thread thread;

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

	public ObstructionController(MyColorSensor cs, MyLightSensor ls,
			MyUltraSonicSensor us, GUI gui) {
		System.out.println("linefollowcontroller");
		this.gui = gui;

		cs.addListener(this);
		ls.addListener(this);
		us.addListener(this);
	}

	public void run() {
		isExecuting = true;
		LineFollowController.pauseLineFollower();
		MotorController.setRotateSpeed(ROTATE_SPEED);
		if (firstEvasion) {
			MotorController.rotate(FIRST_TURN_ANGLE, false);
		} else {
			MotorController.rotate(SECOND_TURN_ANGLE, false);
		}
		MotorController.driveArc(RADIUS, true);
		noLineFound = true;
		while (noLineFound) {

		}
		LineFollowController.continueLineFollower();
		gui.cancelPopUp();
		firstEvasion = true;
		isExecuting = false;

	}

	/**
	 * If the measured value from the ultrasonic sensor changes this method is
	 * called. When called this method checks of the new value is smaller than
	 * the safe value, if that's true the robot displays a message and starts a
	 * evasive maneuver. When the new value is bigger than the save distance any
	 * messages on the display disappear. *
	 * 
	 * @see sensors.UltrasonicSensorListener#ultraSonicChanged(sensors.UpdatingSensor,
	 *      float, float)
	 */
	@Override
	public void ultraSonicChanged(UpdatingSensor us, float oldValue,
			float newValue) {
		if (newValue < SAFE_DISTANCE) {
			if (!isExecuting) {
				startEvasion();
			} else {
				resetEvasion();
			}
		}
	}

	/**
	 * this starts the evasion thread for the first time.
	 */
	private void startEvasion() {
		firstEvasion = true;
		thread = new Thread(this);
		thread.start();
		gui.showErrorPopUp("Object to close");

	}

	/**
	 * this resets the thread and starts it again.
	 */
	private void resetEvasion() {
		thread.interrupt();
		thread = null;
		firstEvasion = false;
		thread = new Thread(this);
		thread.start();
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
