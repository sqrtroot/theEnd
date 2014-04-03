package nxt;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 0.2
 */
public class LineFollowController extends Thread implements LightSensorListener {
	private boolean leftOnRoute;
	private boolean rightOnRoute;
	private static boolean pause = false;
	private final int TRESHOLD = 50;

	private final int MINIMUM_SAFE_DISTANCE = 30;

	public LineFollowController(ColorSensor cs, LightSensor ls) {
		cs.addListener(this);
		ls.addListener(this);
		this.start();
	}

	public void run() {
		while (true) {
			if (!pause) {
				if (!leftOnRoute) {
					MotorController.turnOnPlace(-5);
				} else if (!rightOnRoute) {
					MotorController.turnOnPlace(5);
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
			if (newValue < TRESHOLD) {
				leftOnRoute = false;
			} else {
				leftOnRoute = true;
			}
		}
		if (position == SensorPosition.Right) {
			if (newValue < TRESHOLD) {
				rightOnRoute = false;
			} else {
				rightOnRoute = true;
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