package nxt;

import lejos.nxt.LCD;

public class LineFollowController extends Thread implements LightSensorListener {
	private boolean leftOnRoute;
	private boolean rightOnRoute;
	private boolean nothingInTheWay;
	private static float TRESHOLD = 20;

	private static boolean pause = false;

	public LineFollowController(ColorSensor cs, LightSensor ls) {
		System.out.println("initialized");
		cs.addListener(this);
		ls.addListener(this);
		start();

	}

	public void run() {
		while (true) {

			if (!pause) {
				if (!leftOnRoute) {
					LCD.drawString(""+leftOnRoute, 0, 0);
					LCD.drawString(""+rightOnRoute, 0, 2);
					LCD.drawString("ERROR", 0, 5);
					MotorController.turnOnPlace(-5, false);
				} else if (!rightOnRoute) {
					LCD.drawString(""+leftOnRoute, 0, 0);
					LCD.drawString(""+rightOnRoute, 0, 2);
					LCD.drawString("ERROR", 0, 5);
					MotorController.turnOnPlace(5, false);
				} else {
					LCD.drawString(""+leftOnRoute, 0, 0);
					LCD.drawString(""+rightOnRoute, 0, 2);
					LCD.drawString("Forward", 0, 5);
					MotorController.driveForward();
				}
			} else {
				MotorController.driveForward();
			}
		}

	}

	@Override
	public void lightSensorChanged(Position position,
			UpdatingSensor updatingsensor, float oldValue, float newValue) {
		LCD.drawInt((int) newValue, 0, 7);
		if (position == Position.Left) {
			if (newValue > TRESHOLD ) {
				leftOnRoute = false;
			} else {
				leftOnRoute = true;
			}
		}
		if (position == Position.Right) {
			if (newValue > TRESHOLD) {
				rightOnRoute = false;
			} else {
				rightOnRoute = true;
			}
		}
	}
}
