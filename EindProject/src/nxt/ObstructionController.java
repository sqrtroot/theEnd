package nxt;

public class ObstructionController extends Thread implements
		LightSensorListener, UltraSonicSensorListener {

	private int current_distance = 255;
	private int sensor_value_left = 0;
	private int sensor_value_right = 0;

	public static final int SAFE_DISTANCE = 20;
	private final int ARC_DEGREES = 180;

	private final int LIGHT_HIGH = 70;
	private final int LIGHT_LOW = 30;

	public ObstructionController(ColorSensor cs, LightSensor ls,
			UltraSonicSensor us) {

		cs.addListener(this);
		ls.addListener(this);
		us.addListener(this);
	}

	public void run() {
		while (true) {
			MotorController.driveForward();
			if (current_distance < SAFE_DISTANCE) {
				evasiveManoeuvre();
			}
		}
	}

	private void evasiveManoeuvre() {

		MotorController.driveForward();
		boolean noLineFound = true;
		while (noLineFound) {

			if (current_distance < SAFE_DISTANCE) {
				LineFollowController.pauseLineFollowing();
				MotorController.turnOnPlace(-90);
				MotorController.DriveArc((SAFE_DISTANCE * 10), ARC_DEGREES,
						true); // RADIUS
				// ,
				// ANGLE

				while (MotorController.moving()) {

					if (sensor_value_left < LIGHT_LOW
							|| sensor_value_right < LIGHT_LOW)
						MotorController.stop();

				}

				noLineFound = false;

			}

		}

	}

	/*
	 * Wats deze?? private void evasiveManoeuvre() {
	 * 
	 * MotorController.stop();
	 * MotorController.driveArc((SAFE_DISTANCE*10),true);
	 * 
	 * while(true){ System.out.println(sensor_value_left + " \t" +
	 * sensor_value_right); if(sensor_value_left < LIGHT_LOW){
	 * MotorController.stop();
	 * 
	 * 
	 * } else if(sensor_value_right < LIGHT_LOW){ MotorController.stop();
	 * 
	 * } }
	 */

	@Override
	public void ultraSonicChanged(UpdatingSensor us, float oldValue,
			float newValue) {

		current_distance = (int) newValue;
	}

	@Override
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {
		if (position == SensorPosition.Left)
			sensor_value_left = (int) newValue;
		else if (position == SensorPosition.Right)
			sensor_value_right = (int) newValue;
	}
}
