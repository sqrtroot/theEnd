package controllers;

import sensors.LightSensorListener;
import sensors.MyColorSensor;
import sensors.MyLightSensor;
import sensors.Position;
import sensors.UpdatingSensor;

/**
 * This class will guide a Lego NXT Robot via a black trail on a white surface.
 * 
 * @author Pim van Hespen <PimvanHespen@gmail.com>
 * @version 1.4
 * @since 04-04-2014
 */
public class LineFollowController extends Thread implements LightSensorListener {

	private static boolean pause;
	private boolean rightIsDark = true;
	private boolean leftIsDark = false;
	private boolean active = true;
	private boolean headedTowardsLine = true;

	private Position mostRecentDark;
	private Position direction;

	private final int ROTATION_PER_TURN = 2;
	private final int MOTOR_ROTATION_SPEED = 80;
	
	private final int BASE_SPEED_FORWARD = 160;	
	private final int INCREASED_SPEED_FORWARD = BASE_SPEED_FORWARD + 40;
	
	private final int THRESHOLD = 50;

	public LineFollowController(MyColorSensor cs, MyLightSensor ls) {

		cs.addListener(this);
		ls.addListener(this);

		this.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {

		MotorController.setTravelSpeed(BASE_SPEED_FORWARD);
		MotorController.setRotateSpeed(MOTOR_ROTATION_SPEED);
		MotorController.driveForward();

		while (active) {
			if (!pause) {
				if (leftIsDark && rightIsDark) {
					if (mostRecentDark == Position.Left) {
						steerRight();
					} else {
						steerLeft();
					}
				} else if (leftIsDark) {
					forward(Position.Left);
				} else if (rightIsDark) {
					forward(Position.Right);
				} else {
					if (mostRecentDark == Position.Left) {
						steerLeft();
					} else {
						steerRight();
					}
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException ie) {
				}

			}
		}

	}

	/**
	 * This is the setter for boolean 'active'.
	 * 
	 * @param incomingValue
	 *            the value to set active to.
	 */
	public void setActive(boolean incomingValue) {
		active = incomingValue;
	}

	/**
	 * This method will make the robot turn left.
	 */
	private void steerLeft() {
		headedTowardsLine = true;
		MotorController.rotate(-ROTATION_PER_TURN, true);
	}

	/**
	 * This method will make the robot turn right.
	 */
	private void steerRight() {
		headedTowardsLine = true;
		MotorController.rotate(ROTATION_PER_TURN, true);
	}

	/**
	 * Updates booleans headedTowardsLine and mostRecentDark, then steers the
	 * robot forwards and depending on the current state of headedTowardsLine
	 * the robot will either go straight forward or slightly increase the speed
	 * of one of two motors.
	 * 
	 * @param pos
	 */
	private void forward(Position pos) {

		if (pos != mostRecentDark) {

			headedTowardsLine = false;
			mostRecentDark = pos;
		}

		if (headedTowardsLine) {
			MotorController.setTravelSpeed(BASE_SPEED_FORWARD);
			MotorController.driveForward();
		} else {
			if (pos == Position.Left) {
				MotorController.setRightMotorSpeed(INCREASED_SPEED_FORWARD);
				MotorController.driveForward();
			} else {
				MotorController.setLeftMotorSpeed(INCREASED_SPEED_FORWARD);
				MotorController.driveForward();
			}
		}
	}

	/**
	 * 
	 * @see sensors.LightSensorListener#lightSensorChanged(sensors.SensorPosition,
	 *      sensors.UpdatingSensor, float, float)
	 */
	@Override
	public void lightSensorChanged(Position position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {

		if (position == Position.Left) {

			if (newValue < THRESHOLD) {
				leftIsDark = true;
			} else {
				leftIsDark = false;
			}
		}
		if (position == Position.Right) {

			if (newValue < THRESHOLD) {

				rightIsDark = true;
			} else {
				rightIsDark = false;
			}
		}
	}

	public static void pauseLineFollower() {
		pause = true;
	}

	public static void continueLineFollower() {
		pause = false;
	}

}