package nxt;

/**
 * A trial version for a new line follow protocol.
 * Later evoluated into the FollowTheLine-class
 * 
 * @author Pim van Hespen <PimvanHespen@gmail.com>
 * @version 1.0
 * @since 02-04-2014
 *
 */
public class LineKing extends Thread implements LightSensorListener,
		UltraSonicSensorListener {
	private boolean leftOnRoute;
	private boolean rightOnRoute;
	private static boolean pause;
	private final int THRESHOLD = 50;

	private final int MINIMUM_SAFE_DISTANCE = 30;
	private boolean rightIsDark;
	private boolean leftIsDark;
	private float distanceToObject;

	public LineKing(MyColorSensor cs, MyLightSensor ls, UltraSonicSensor us) {

		cs.addListener(this);
		ls.addListener(this);
		us.addListener(this);

		this.start();
	}

	public void run() {
		
		//MotorController.
		
		while (true) {
			if(rightIsDark || leftIsDark){
				MotorController.driveForward();
			}
		}
	}

	@Override
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {
		if (position == SensorPosition.Left) {
			if (newValue < THRESHOLD) {
				leftIsDark = false;
			} else {
				leftIsDark = true;
			}
		} else if (position == SensorPosition.Right) {
			if (newValue < THRESHOLD) {
				rightIsDark = false;
			} else {
				rightIsDark = true;
			}
		}
	}

	@Override
	public void ultraSonicChanged(UpdatingSensor us, float oldValue,
			float newValue) {
		distanceToObject = newValue;

	}
}
