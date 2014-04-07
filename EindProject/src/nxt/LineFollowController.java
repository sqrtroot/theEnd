package nxt;

import lejos.nxt.LCD;

/**
 * 
 * @author Jacob Visser <Jacob.Visser@student.hu.nl>
 * @version 0.3
 * @since 01-04-2014
 */
public class LineFollowController extends Thread implements LightSensorListener {
	private boolean leftBlack; // Boolean that's true when the left sensor
								// senses black and false when sensor senses
								// white
	private boolean rightBlack; // Boolean that's true when the right sensor
								// senses black and false when sensor senses
								// white

	private int turnSpeed = 100;
	private int travelSpeed = 50; // Speed that the motors turn when not making
									// a corner
	private int rightTravelSpeed; // Speed that the right motor turns, used when
									// making corners
	private int leftTravelSpeed; // Speed that the left motor turns, used when
									// making corners

	private static boolean pause = false; // Boolean used to pause the following
											// of the line
	public final int THRESHOLD = 50; // Beneath this amount the sensor senses
										// black, above this amount the sensor
										// senses white

	/*
	 * private final int NUMBER_OF_SAMPLES = 20; // Amount of samples taken to
	 * // determine the average light // value // unnecessary
	 */
	private GUI gui;

	/**
	 * Constructor of LineFollowController(LFC), sets the attributes lightSensor
	 * and colorSensor, makes LFC Listener of colorSensor and lightSensor, sets
	 * the travelspeed and starts run()
	 * 
	 * @param cs
	 *            The colorsensor installed on the robots' frame
	 * @param ls
	 *            The lightsensor installed on the robots' frame
	 */
	public LineFollowController(MyColorSensor cs, MyLightSensor ls, GUI gui) {
		this.gui = gui;
		cs.addListener(this);
		ls.addListener(this);
		MotorController.setTravelSpeed(travelSpeed);
		this.start();
	}

	/**
	 * checks, while the the boolean pause is false, if any of the sensors
	 * senses black, and if one does so, the speed of the motor opposite to the
	 * sensor gets increased. if none of the sensors is black the speed gets
	 * back to normal.
	 * 
	 */
	public void run() {
		MotorController.setRotateSpeed(turnSpeed);
		while (true) {
			if (!pause) {

				if (leftBlack) {
					leftTravelSpeed = travelSpeed + 400;
					MotorController.setIndividiualTravalSpeed(leftTravelSpeed,
							travelSpeed);
					MotorController.driveForward();
					// MotorController.turnOnPlace(-10);
				} else if (rightBlack) {
					rightTravelSpeed = travelSpeed + 400;
					MotorController.setIndividiualTravalSpeed(travelSpeed,
							rightTravelSpeed);
					MotorController.driveForward();

					// MotorController.turnOnPlace(10);
				} else {
					MotorController.setTravelSpeed(travelSpeed);
					MotorController.driveForward();
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @param positition
	 * @param updatingsensor
	 * @param oldValue
	 * @value newValue
	 */
	@Override
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {
		if (position == SensorPosition.Left) {
			LCD.drawString("" + newValue, 0, 0);
			if (newValue < THRESHOLD) {
				leftBlack = true;
				gui.lightensorError(position);
			} else {
				leftBlack = false;
				gui.lightSensorAlright(position);
			}
		}
		if (position == SensorPosition.Right) {
			LCD.drawString("" + newValue, 0, 1);
			if (newValue < THRESHOLD) {
				rightBlack = true;
				gui.lightensorError(position);
			} else {
				rightBlack = false;
				gui.lightSensorAlright(position);
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