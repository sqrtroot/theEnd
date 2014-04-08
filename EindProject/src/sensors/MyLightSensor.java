package sensors;

import java.util.ArrayList;

import lejos.nxt.SensorPort;

/**
 * This class holds all the utilities to control the light sensor.
 * Also this is a boundary class for the actual light sensor
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 */
public class MyLightSensor extends lejos.nxt.LightSensor implements
		UpdatingSensor {
	
	private float value;
	
	private static final SensorType SENSORTYPE = SensorType.Lightsensor;
	
	private ArrayList<LightSensorListener> listeners = new ArrayList<LightSensorListener>();
	private SensorPosition position;

	/**
	 * the constructor for the light sensor
	 * 
	 * @param sensorport
	 *            the port the light sensor is attached to on the NXT
	 * @param position
	 *            the position the NXT
	 */
	public MyLightSensor(SensorPort sensorport, SensorPosition position) {
		super(sensorport);
		this.position = position;
		SensorHandler.getInstance().addSensor(this);
	}

	/**
	 * used to update the sensors value and if the have changed pass them to
	 * the listeners
	 */
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

	/**
	 * adds the listener to the list of listeners 
	 * 
	 * @param listener
	 *            the listener that is added
	 */
	public void addListener(LightSensorListener listener) {
		listeners.add(listener);
	}

	/**
	 * deletes the listener from the list of listeners
	 * 
	 * @param listener
	 *            the listener that has to be deleted
	 */
	public void deleteListener(LightSensorListener listener) {
		boolean tmp = listeners.remove(listener);

		if (tmp) {
			System.out.println("removed");
		} else {
			System.err.print("not removed");
		}
	}

	/**
	 * returns the type of sensor
	 * 
	 * @return SENSORTYPE returns the type of the sensor
	 */
	public SensorType getSensorType() {
		return SENSORTYPE;
	}

}
