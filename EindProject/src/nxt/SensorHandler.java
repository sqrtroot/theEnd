package nxt;

import java.util.ArrayList;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * 
 */
public class SensorHandler extends Thread {
	private static SensorHandler singleton;
	private ArrayList<UpdatingSensor> sensors = new ArrayList<UpdatingSensor>();
	private final long timeOut = 100;

	/**
	 * private constructor for sensorhandler
	 */
	private SensorHandler() {
		this.setDaemon(true);
		start();
	}

	/**
	 * 
	 * @return SensorHandler returns the version of sensorhandler
	 */
	public static SensorHandler getInstance() {
		if (singleton == null) {
			singleton = new SensorHandler();
		}
		return singleton;

	}

	/**
	 * updates all the added sensors
	 */
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					for (UpdatingSensor sensor : sensors)
						sensor.updateState();
				}
				Thread.sleep(timeOut);
			} catch (InterruptedException exception) {
			}
		}
	}

	/**
	 * add's a sensor the the listeners
	 * 
	 * @param updatingSensor
	 *            the to be added listener
	 */
	public void addSensor(UpdatingSensor updatingSensor) {
		sensors.add(updatingSensor);
	}

	/**
	 * removes a sensor from the handler list of sensors
	 * 
	 * @param updatingSensor
	 *            the sensor that has to be removed
	 */
	public void removeSensor(UpdatingSensor updatingSensor) {
		sensors.remove(updatingSensor);
	}
}
