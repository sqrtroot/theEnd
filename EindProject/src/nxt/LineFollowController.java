package nxt;

import lejos.nxt.Battery;
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
	
	private int travelSpeed = 200;
	private int rightTravelSpeed;
	private int leftTravelSpeed;
	
	private static boolean pause = false;
	private final int TRESHOLD = 50;

	private final int MINIMUM_SAFE_DISTANCE = 30;
	
	private final int NUMBER_OF_SAMPLES = 20;
	
	private LightSensor ls;
	private ColorSensor cs;

	public LineFollowController(ColorSensor cs, LightSensor ls) {
		this.cs = cs;
		this.ls = ls;
		cs.addListener(this);
		ls.addListener(this);
		MotorController.setTravelSpeed(travelSpeed);
		this.start();
	}

	public void run() {
		while (true) {
			if (!pause) {
				MotorController.driveForward();
				if (leftBlack) {
					rightTravelSpeed = travelSpeed + 100;
					MotorController.setIndividiualTravalSpeed(travelSpeed, rightTravelSpeed);
					MotorController.driveForward();
				} else if (rightBlack) {
					leftTravelSpeed = travelSpeed + 100;
					MotorController.setIndividiualTravalSpeed(leftTravelSpeed, travelSpeed);
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
	
	public int avgLightValue() {
		 
		int sum = 0;
		for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
			sum += ls.getLightValue();
			sum += cs.getLightValue();
			
			
		}
		return sum / (NUMBER_OF_SAMPLES * 2);
	}

}