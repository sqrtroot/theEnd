package nxt;

import java.util.ArrayList;

import lejos.nxt.SensorPort;
/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 *
 */
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
		float tmp = this.getLightValue();
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

	@Override
	public int getLow() {
		return zero;
	}

	@Override
	public int getHigh() {
		return hundred;
	}

	@Override
	public void setHigh(int value) {
		hundred = value;
	}

	@Override
	public void setLow(int value) {
		zero = value;
	}

	/**
	 * 
	 * @return returns the type of the sensor
	 */
	public SensorType getSensorType() {
		return SensorType.Colorsensor;
	}
}
