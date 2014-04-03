package nxt;

import java.util.ArrayList;

import lejos.nxt.SensorPort;
/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 *
 */
public class LightSensor extends lejos.nxt.LightSensor implements
		UpdatingSensor {
	private float value;
	private ArrayList<LightSensorListener> listeners = new ArrayList<LightSensorListener>();
	private Position position;

	public LightSensor(SensorPort sensorport, Position position) {
		super(sensorport);
		this.position = position;
		SensorHandler.getInstance().addSensor(this);
	}

	public void updateState() {
		float tmp = getLightValue();
		if (tmp != value) {
			if (listeners.size() > 0) {
				for (LightSensorListener listener : listeners) {
					listener.lightSensorChanged(position, this, value, tmp);
				}
			}
			value = tmp;
		}

	}

	public void addListener(LightSensorListener listener) {
		listeners.add(listener);
	}

	public void deleteListener(LightSensorListener listener) {
		boolean tmp = listeners.remove(listener);

		if (tmp) {
			System.out.println("removed");
		} else {
			System.err.print("not removed");
		}
	}

	/**
	 * 
	 * @return returns the type of the sensor
	 */
	public SensorType getSensorType() {
		return SensorType.Lightsensor;
	}

}
