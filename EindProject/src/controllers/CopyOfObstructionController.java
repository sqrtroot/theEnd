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
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.2
 * @since 01-04-2014
 * 
 */
public class CopyOfObstructionController implements LightSensorListener,
		UltrasonicSensorListener, Runnable {

	private boolean first = true;

	private final int SAFE_DISTANCE = 20;
	private final int MEDIAN = 50;

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

	public CopyOfObstructionController(MyColorSensor cs, MyLightSensor ls,
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
		MotorController.setRotateSpeed(100);
		if (first) {
			MotorController.rotate(-40, false);
		} else {
			MotorController.rotate(-20, false);
		}
		MotorController.driveArc(SAFE_DISTANCE * 10, false);
		noLineFound = true;
		while (noLineFound) {

		}
		LineFollowController.continueLineFollower();
		gui.cancelPopUp();
		first = true;
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
		if (newValue < SAFE_DISTANCE && !isExecuting) {
			startEvasion();
		} else if (newValue < SAFE_DISTANCE) {
			resetEvasion();
		}
	}

	/**
	 * this starts the evasion thread for the first time.
	 */
	private void startEvasion() {
		first = true;
		thread = new Thread(this);
		thread.start();

	}

	/**
	 * this resets the thread and starts it again.
	 */
	private void resetEvasion() {
		thread.interrupt();
		thread = null;
		first = false;
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
				System.out.println("restart");
				noLineFound = false;

			}
		}

	}
}
