package nxt;

import java.util.ArrayList;

import lejos.nxt.SensorPort;

public class UltraSonicSensor extends lejos.nxt.UltrasonicSensor implements
		UpdatingSensor {
	private int value;
	private ArrayList<UltraSonicSensorListener> listeners = new ArrayList<UltraSonicSensorListener>();

	public UltraSonicSensor(SensorPort sensorport) {
		super(sensorport);
		SensorHandler.getInstance().addSensor(this);
	}

	public void updateState() {
		if (listeners.size() < 0) {
			int tmp = (int) getRange();
			if (tmp != value) {
				for (UltraSonicSensorListener listener : listeners)
					listener.ultraSonicChanged(this, value, tmp);
				value = tmp;
			}
		}
	}

	public void addListener(UltraSonicSensorListener usl) {
		listeners.add(usl);
	}

	public void removeListener(UltraSonicSensorListener usl) {
		listeners.remove(usl);
	}
	
	/**
	 * 	
	 * @return returns the type of the sensor
	 */
	 public SensorType getSensorType(){
	 	return SensorType.Ultrasonicsensor;
	 }
}
