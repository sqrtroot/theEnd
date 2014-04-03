package nxt;

import lejos.nxt.LCD;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 0.2
 */
public class LineFollowController extends Thread implements LightSensorListener {
	private boolean leftBlack;
	private boolean rightBlack;
	private static boolean pause = false;
	private final int TRESHOLD = 50;

	private final int MINIMUM_SAFE_DISTANCE = 30;

	public LineFollowController(ColorSensor cs, LightSensor ls) {
		cs.addListener(this);
		ls.addListener(this);
		MotorController.setTravelSpeed(200);
		this.start();
	}

	public void run() {
		while (true) {
			if (!pause) {
				if (!leftBlack) {
					MotorController.turnOnPlace(-1);
				} else if (!rightBlack) {
					MotorController.turnOnPlace(1);
				} else {
					MotorController.driveForward();
				}
			}
		}
	}

	@Override
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {
		if (position == SensorPosition.Left) {
			LCD.drawString("" + newValue, 0, 0);
			if (newValue < TRESHOLD) {
				leftBlack = true;
			} else {
				leftBlack = false;
			}
		}
		if (position == SensorPosition.Right) {
			LCD.drawString("" + newValue, 0, 1);
			if (newValue < TRESHOLD) {

				rightBlack = true;
			} else {
				rightBlack = false;
			}
		}
	}

	public static void pauseLineFollowing() {
		pause = true;
	}

	public static void continueLineFollowing() {
		pause = false;
	}

}