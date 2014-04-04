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
				if (leftBlack) {
					
					 int avgLight = avgLightValue();
					 System.out.println(avgLight);
					 rightTravelSpeed = travelSpeed + 200*(TRESHOLD-avgLight)/((ls.getHigh()-ls.getLow()+cs.getHigh()-cs.getLow())/2);
			         System.out.println(rightTravelSpeed);
					 if (rightTravelSpeed > Battery.getVoltage()*100)
			            rightTravelSpeed = (int) (Battery.getVoltage()*100);
			         if (rightTravelSpeed < 0)
			            rightTravelSpeed = 0;			         
			         
					 leftTravelSpeed = travelSpeed - 200*(TRESHOLD-avgLight)/((ls.getHigh()-ls.getLow()+cs.getHigh()-cs.getLow())/2);
			         if (leftTravelSpeed > Battery.getVoltage()*100)
			            leftTravelSpeed = (int) (Battery.getVoltage()*100);
			         if (leftTravelSpeed < 0)
			            leftTravelSpeed = 0;
			         MotorController.setIndividiualTravalSpeed(leftTravelSpeed, rightTravelSpeed);
			         MotorController.driveForward();
				} else if (rightBlack) {
					 int avgLight = avgLightValue();
					 rightTravelSpeed = travelSpeed - 200*(TRESHOLD-avgLight)/((ls.getHigh()-ls.getLow()+cs.getHigh()-cs.getLow())/2);
			         if (rightTravelSpeed > Battery.getVoltage()*100)
			            rightTravelSpeed = (int) (Battery.getVoltage()*100);
			         if (rightTravelSpeed < 0)
			            rightTravelSpeed = 0;
					
					 
					 leftTravelSpeed = travelSpeed + 200*(TRESHOLD-avgLight)/((ls.getHigh()-ls.getLow()+cs.getHigh()-cs.getLow())/2);
			         if (leftTravelSpeed > Battery.getVoltage()*100)
			            leftTravelSpeed = (int) (Battery.getVoltage()*100);
			         if (leftTravelSpeed < 0)
			            leftTravelSpeed = 0;
			         MotorController.setIndividiualTravalSpeed(leftTravelSpeed, rightTravelSpeed);
			         MotorController.driveForward();
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
	
	public int avgLightValue() {
		 
		int sum = 0;
		for (int i = 0; i < NUMBER_OF_SAMPLES; i++) {
			sum += ls.getLightValue();
			sum += cs.getLightValue();
			
			
		}
		return sum / (NUMBER_OF_SAMPLES * 2);
	}

}