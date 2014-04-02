package nxt;

import java.util.ArrayList;

import lejos.nxt.SensorPort;

public class ColorSensor extends lejos.nxt.ColorSensor implements
		UpdatingSensor {
	private float value;
	private Position position;
	private ArrayList<LightSensorListener> listeners = new ArrayList<LightSensorListener>();
	private int zero = 1023;
	private int hundred = 0;

	public ColorSensor(SensorPort sensorport, Position position) {
		super(sensorport);
		this.position = position;
		setFloodlight(Color.RED);
		setFloodlight(true);
		SensorHandler.getInstance().addSensor(this);
	}

	public void updateState() {
		float tmp = this.getNormalizedLightValue();
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

	@Override
	public int getLightValue() {
		if (hundred == zero)
			return 0;
		return 100 * (getRawLightValue() - zero) / (hundred - zero);
	}

	/**
	 * 
	 * @return returns the type of the sensor
	 */
	public SensorType getSensorType() {
		return SensorType.Colorsensor;
	}
}
