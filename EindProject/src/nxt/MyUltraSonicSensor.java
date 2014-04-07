package nxt;

import java.util.ArrayList;
import lejos.nxt.SensorPort;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 */
public class MyUltraSonicSensor extends lejos.nxt.UltrasonicSensor implements
		UpdatingSensor {
	private int value;
	private ArrayList<UltraSonicSensorListener> listeners = new ArrayList<UltraSonicSensorListener>();

	/**
	 * Constructor for UltraSonicSensorListener
	 * 
	 * @param sensorport
	 *            the given sensor port
	 */
	public MyUltraSonicSensor(SensorPort sensorport) {
		super(sensorport);
		SensorHandler.getInstance().addSensor(this);
	}

	/**
	 * @see nxt.UpdatingSensor#updateState()
	 */
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

	/**
	 * adds the UltraSonicSensorListener to the listeners array
	 * 
	 * @param usl
	 *            the given UltraSonicSensorListener
	 */
	public void addListener(UltraSonicSensorListener usl) {
		listeners.add(usl);
	}

	/**
	 * removes the UltraSonicSensorListener to the listeners array
	 * 
	 * @param usl
	 *            the given UltraSonicSensorListener
	 */
	public void removeListener(UltraSonicSensorListener usl) {
		listeners.remove(usl);
	}

	/**
	 * Method to get the sensortype
	 * 
	 * @return returns the type of the sensor
	 */
	public SensorType getSensorType() {
		return SensorType.Ultrasonicsensor;
	}
}
