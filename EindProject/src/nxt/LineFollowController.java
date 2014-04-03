package nxt;

import lejos.nxt.LCD;
/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 *
 */
public class LineFollowController extends Thread implements
		LightSensorListener, UltraSonicSensorListener {

	private boolean leftIsDark;
	private boolean rightIsDark;
	private int distanceToObject;

	private final int THRESHOLD = 50;

	public LineFollowController(ColorSensor cs, LightSensor ls,
			UltraSonicSensor us) {
		us.addListener(this);
		cs.addListener(this);
		ls.addListener(this);
		start();
	}

	public void run() {
		
		
		
	}

	@Override
	public void lightSensorChanged(Position position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {

		if (position == Position.Left) {

			if (newValue > THRESHOLD)
				leftIsDark = false;
			else
				leftIsDark = true;
		}
		if (position == Position.Right) {

			if (newValue > THRESHOLD)
				rightIsDark = false;
			else
				rightIsDark = true;

		}
	}

	@Override
	public void ultraSonicChanged(UpdatingSensor us, int oldValue, int newValue) {
		distanceToObject = newValue;
		
	}

}
