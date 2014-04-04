package nxt;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 */
public class MotorController {
	private final static float wheelDiameter = (float) 32;
	private final static float wheelSpace = 200;

	private final static NXTRegulatedMotor leftMotor = Motor.A;
	private final static NXTRegulatedMotor rightMotor = Motor.C;

	private static DifferentialPilot differentialPilot = new DifferentialPilot(
			wheelDiameter, wheelSpace, leftMotor, rightMotor);

	public static void driveArc(float turnRadius) {
		differentialPilot.arcForward(turnRadius);
	}

	public static void driveForward() {
		differentialPilot.forward();
	}

	public static void stop() {
		differentialPilot.stop();
	}

	public static void turnOnPlace(float degrees, boolean moveOn) {
		differentialPilot.rotate(degrees, moveOn);
	}

	public static void turnOnPlace(float degrees) {
		differentialPilot.rotate(degrees);
	}

	public static void driveBackwards() {
		differentialPilot.backward();
	}

	public static void driveDistance(float distance) {
		differentialPilot.travel(distance);
	}

	public static boolean moving() {
		return differentialPilot.isMoving();
	}

	public static void driveArc(float turnRadius, boolean immediateReturn) {
		differentialPilot.arc(turnRadius, 360, immediateReturn);

	}

	public static void DriveArc(int i, int aRC_DEGREES, boolean b) {
		differentialPilot.arc(i, aRC_DEGREES, b);
	}

	public static void rotate(int degrees, boolean immediateReturn) {
		differentialPilot.rotate(degrees, immediateReturn);
	}

	public static void setRotateSpeed(int degrees) {
		differentialPilot.setRotateSpeed(degrees);
	}

	public static void setTravelSpeed(float speed) {
		differentialPilot.setTravelSpeed(speed);
	}
	
	public static void setIndividiualTravalSpeed(int leftMotorSpeed, int rightMotorSpeed){
		leftMotor.setSpeed(leftMotorSpeed);
		rightMotor.setSpeed(rightMotorSpeed);
	}
}
