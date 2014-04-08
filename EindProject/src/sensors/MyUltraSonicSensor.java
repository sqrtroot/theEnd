package sensors;

import java.util.ArrayList;

import lejos.nxt.SensorPort;

/**
 * This class holds all the utilities to control the ultrasonic sensor. Also
 * this is a boundary class for the actual ultrasonic sensor
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 */
public class MyUltraSonicSensor extends lejos.nxt.UltrasonicSensor implements
		UpdatingSensor {

	private int value;

	private static final SensorType SENSORTYPE = SensorType.Ultrasonicsensor;

	private ArrayList<UltrasonicSensorListener> listeners = new ArrayList<UltrasonicSensorListener>();

	/**
	 * Constructor for UltrasonicSensorListener
	 * 
	 * @param sensorport
	 *            the given sensor port
	 */
	public MyUltraSonicSensor(SensorPort sensorport) {
		super(sensorport);
		SensorHandler.getInstance().addSensor(this);
	}

	/**
	 * @see sensors.UpdatingSensor#updateState()
	 */
	public void updateState() {
		if (listeners.size() > 0) {
			int tmp = (int) getRange();
			if (tmp != value) {
				for (UltrasonicSensorListener listener : listeners)
					listener.ultraSonicChanged(this, value, tmp);
				value = tmp;
			}
		}
	}

	/**
	 * adds the UltrasonicSensorListener to the listeners array
	 * 
	 * @param ultrasonicListener
	 *            the given UltrasonicSensorListener
	 */
	public void addListener(UltrasonicSensorListener ultrasonicListener) {
		listeners.add(ultrasonicListener);
	}

	/**
	 * removes the UltrasonicSensorListener to the listeners array
	 * 
	 * @param ultrasonicListener
	 *            the given UltrasonicSensorListener
	 */
	public void removeListener(UltrasonicSensorListener ultrasonicListener) {
		listeners.remove(ultrasonicListener);
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
