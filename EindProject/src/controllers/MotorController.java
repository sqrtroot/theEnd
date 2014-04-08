package controllers;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * This class allows other classes to steer the motor
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @author Jacob Visser <jacob.visser@student.hu.nl>
 * @author Pim van Hespen <pimvanhespen@gmail.com>
 * @version 1.5
 * @since 01-04-2014
 */
public class MotorController {
	private final static float WHEEL_DIAMETER = (float) 32;
	private final static float WHEEL_SPACE = 180;

	private final static NXTRegulatedMotor leftMotor = Motor.A;
	private final static NXTRegulatedMotor rightMotor = Motor.C;

	private static DifferentialPilot differentialPilot = new DifferentialPilot(
			WHEEL_DIAMETER, WHEEL_SPACE, leftMotor, rightMotor);

	/**
	 * Will make the robot drive arc with the given radius. Stops when motor is
	 * stopped
	 * 
	 * @param turnRadius
	 *            radius in millimeter
	 */
	public static void driveArc(float turnRadius) {
		differentialPilot.arcForward(turnRadius);
	}

	/**
	 * Will move the robot forward until canceled
	 */
	public static void driveForward() {
		differentialPilot.forward();
	}

	/**
	 * stops all movement.
	 */
	public static void stop() {
		differentialPilot.stop();
	}

	/**
	 * Will move the robot backwards until stopped
	 */
	public static void driveBackwards() {
		differentialPilot.backward();
	}

	/**
	 * Moves the robot for a set amount
	 * 
	 * @param distance
	 *            in millimeters
	 */
	public static void driveDistance(float distance) {
		differentialPilot.travel(distance);
	}

	/**
	 * Tells if the robot is moving (true) or isn't (false)
	 * 
	 * @return if the robot is moving
	 */
	public static boolean moving() {
		return differentialPilot.isMoving();
	}

	/**
	 * Makes the robot drive a circle with the given radius 'turnRadius'
	 * 
	 * @param turnRadius
	 *            radius in millimeters
	 * @param immediateReturn
	 */
	public static void driveArc(float turnRadius, boolean immediateReturn) {
		differentialPilot.arc(turnRadius, 360, immediateReturn);

	}

	/**
	 * @param radius
	 *            of the arc in millimeters
	 * @param degrees
	 *            of the arc to drive
	 * @param immmediateReturn
	 *            if true, immediately returns to the next line of code while
	 *            executing the arc
	 * 
	 */
	public static void DriveArc(int radius, int degrees,
			boolean immmediateReturn) {
		differentialPilot.arc(radius, degrees, immmediateReturn);
	}

	/**
	 * @param degrees
	 *            to turn
	 * @param immmediateReturn
	 *            if true, immediately returns to the next line of code while
	 *            executing the rotation
	 */
	public static void rotate(int degrees, boolean immediateReturn) {
		differentialPilot.rotate(degrees, immediateReturn);
	}

	/**
	 * set the degrees to turn per second
	 * 
	 * @param degrees
	 *            per second
	 */
	public static void setRotateSpeed(int degrees) {
		differentialPilot.setRotateSpeed(degrees);
	}

	/**
	 * sets the travelSpeed
	 * 
	 * @param speed
	 *            in wheel diameter/units per second
	 */
	public static void setTravelSpeed(float speed) {
		differentialPilot.setTravelSpeed(speed);
	}

	/**
	 * set individual motor speeds
	 * 
	 * @param leftMotorSpeed
	 *            in degrees per second
	 * @param rightMotorSpeed
	 *            in degrees per second
	 */
	public static void setIndividiualTravalSpeed(int leftMotorSpeed,
			int rightMotorSpeed) {
		leftMotor.setSpeed(leftMotorSpeed);
		rightMotor.setSpeed(rightMotorSpeed);
	}

	/**
	 * sets speed for the left motor
	 * 
	 * @param speed
	 *            in degrees per second
	 */
	public static void setLeftMotorSpeed(int speed) {
		leftMotor.setSpeed(speed);
	}

	/**
	 * sets speed for the right motor
	 * 
	 * @param speed
	 *            in degrees per second
	 */
	public static void setRightMotorSpeed(int speed) {
		rightMotor.setSpeed(speed);
	}
}
