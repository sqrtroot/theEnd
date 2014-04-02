package nxt;

import java.util.ArrayList;

import lejos.nxt.Button;

public class CalibreerController {
	// private ArrayList<UpdatingSensor> sensors;

	public CalibreerController() {
		// this.sensors = sensors;
	}

	public ArrayList<UpdatingSensor> calibreer(ArrayList<UpdatingSensor> sensors) {
		ArrayList<UpdatingSensor> returnArrayList = sensors;
		int meetWaarde;
		int position = 0;
		MotorController.turnOnPlace(360, true);

		while (MotorController.moving()) {
			for (UpdatingSensor sensor : returnArrayList) {

				if (sensor.getSensorType() == SensorType.Colorsensor) {
					ColorSensor tmp = (ColorSensor) sensor;
					meetWaarde = tmp.getRawLightValue();
					System.out.println(meetWaarde);
					System.out.println(tmp.getLow());
					if (meetWaarde < tmp.getLow()) {
						tmp.calibrateLow(meetWaarde);
						returnArrayList.set(position, tmp);
					} else if (meetWaarde > tmp.getHigh()) {
						tmp.calibrateHigh(meetWaarde);
						returnArrayList.set(position, tmp);
					}
					System.out.println(tmp.getLow());
					Button.waitForAnyPress();

				}
				if (sensor.getSensorType() == SensorType.Lightsensor) {
					LightSensor tmp = (LightSensor) sensor;
					meetWaarde = tmp.getLightValue();
					if (meetWaarde < tmp.getLow()) {
						tmp.setLow(meetWaarde);
					} else if (meetWaarde > tmp.getHigh()) {
						tmp.setHigh(meetWaarde);
					}
				}
				position++;
			}
		}
		return returnArrayList;
	}
}
