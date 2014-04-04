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
	private boolean leftBlack;								//Boolean that's true when the left sensor senses black and false when sensor senses white
	private boolean rightBlack;								//Boolean that's true when the right sensor senses black and false when sensor senses white
	
	private int turnSpeed = 100;
	private int travelSpeed = 50;							//Speed that the motors turn when not making a corner
	private int rightTravelSpeed;							//Speed that the right motor turns, used when making corners
	private int leftTravelSpeed;							//Speed that the left motor turns, used when making corners
	
	private static boolean pause = false;					//Boolean used to pause the following of the line
	private final int TRESHOLD = 50;						//Beneath this amount the sensor senses black, above this amount the sensor senses white
	
	private final int NUMBER_OF_SAMPLES = 20;				//Amount of samples taken to determine the average light value
	
	private LightSensor ls;									//The lightsensor that's on the robot
	private ColorSensor cs;									//The colorsensor that's on the robot
	
	/**
	 * Constructor of LineFollowController(LFC), sets the attributes lightSensor and colorSensor,
	 * makes LFC Listener of colorSensor and lightSensor, sets the travelspeed and starts run()
	 * @param cs The colorsensor that's on the robot
	 * @param ls The lightsensor that's on the robot
	 */
	public LineFollowController(ColorSensor cs, LightSensor ls) {
		this.cs = cs;
		this.ls = ls;
		cs.addListener(this);
		ls.addListener(this);
		MotorController.setTravelSpeed(travelSpeed);
		this.start();
	}
	/**
	 * checks, while the the boolean pause is false, if any of the sensors senses black, and if one does so, 
	 * the speed of the motor opposite to the sensor gets rised. if none of the sensors is black the speed
	 * gets back to normal.
	 * 
	 */
	public void run() {
		MotorController.setRotateSpeed(turnSpeed);
		while (true) {
			if (!pause) {
				
				if (leftBlack) {
					leftTravelSpeed = travelSpeed + 400;
					MotorController.setIndividiualTravalSpeed(leftTravelSpeed, travelSpeed);
					MotorController.driveForward();
					//MotorController.turnOnPlace(-10);
				} else if (rightBlack) {
					rightTravelSpeed = travelSpeed + 400;
					MotorController.setIndividiualTravalSpeed(travelSpeed, rightTravelSpeed);
					MotorController.driveForward();
					
					//MotorController.turnOnPlace(10);
				}
				else {
					MotorController.setTravelSpeed(travelSpeed);
					MotorController.driveForward();
				}
			}
		}
	}
	/**
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
	
	/**
	 * 
	 * @return
	 */
	public int avgLightValue() {
		 
		int sum = 0;
		for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
			sum += ls.getLightValue();
			sum += cs.getLightValue();
			
			
		}
		return sum / (NUMBER_OF_SAMPLES * 2);
	}

}