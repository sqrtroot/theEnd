package nxt;

import nxt.Position;

/**
 * @author Pim van Hespen <PimvanHespen@gmail.com>
 * @version 1.3
 * @since 04-04-2014
 * 
 *        This class will guide a Lego NXT Robot via a black trail on a white
 *        surface.
 * 
 */
public class FollowTheLine extends Thread implements LightSensorListener {

	private boolean rightIsDark;
	private boolean leftIsDark;
	private boolean active;
	private boolean headedTowardsLine;

	private Position mostRecentDark;
	private Position line;
	private Position direction;

	private final int ROTATION_PER_TURN = 2;
	private final int MOTOR_ROTATION_SPEED = 80;
	private final int BASE_SPEED_FORWARD = 80;
	private final int INCREASED_SPEED_FORWARD = 120;
	private final int THRESHOLD = 50;

	public FollowTheLine(ColorSensor cs, LightSensor ls) {

		cs.addListener(this);
		ls.addListener(this);

		initVariables();

		this.start();
	}

	private void initVariables() {

		active = true;
		leftIsDark = false;
		rightIsDark = false;
		headedTowardsLine = true;
		mostRecentDark = Position.Right;
	}

	@Override
	public void run() {

		MotorController.setTravelSpeed(BASE_SPEED_FORWARD);
		MotorController.setRotateSpeed(MOTOR_ROTATION_SPEED);
		MotorController.driveForward();

		while (active) {

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

	public void setActive(boolean incomingValue) {
		active = incomingValue;
	}

	private void steerLeft() {
		headedTowardsLine = true;
		MotorController.rotate(-ROTATION_PER_TURN, true);
	}

	private void steerRight() {
		headedTowardsLine = true;
		MotorController.rotate(ROTATION_PER_TURN, true);
	}

	private void forward(Position pos) {

		if (pos != mostRecentDark) {
			
			if(pos == Position.Left){
				line = Position.Left;
			} else {
				line = Position.Right;
			}
			
			headedTowardsLine = false;
			mostRecentDark = pos;
		}

		System.out.println(headedTowardsLine + "\t" + direction);

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

	@Override
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {

		if (position == SensorPosition.Left) {

			if (newValue < THRESHOLD) {
				leftIsDark = true;
			} else {
				leftIsDark = false;
			}
		}
		if (position == SensorPosition.Right) {

			if (newValue < THRESHOLD) {

				rightIsDark = true;
			} else {
				rightIsDark = false;
			}
		}
	}

}