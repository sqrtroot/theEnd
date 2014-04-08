package sensors;

import java.util.ArrayList;

/**
 * This class handles all the sensors and requests updates of each sensor every
 * set period of time
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 * 
 */
public class SensorHandler extends Thread {
	private static SensorHandler singleton;
	private ArrayList<UpdatingSensor> sensors = new ArrayList<UpdatingSensor>();
	private final long timeOut = 5;

	/**
	 * constructor for SensorHandler
	 */
	private SensorHandler() {
		this.setDaemon(true);
		start();
	}

	/**
	 * gives instance to the SensorHandler
	 * 
	 * @return SensorHandler returns the instance of SensorHandler
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
	 * adds 'updatingSensor' to the list of sensors registered to this handler
	 * 
	 * @param updatingSensor
	 *            the sensor that has to be added
	 */
	public void addSensor(UpdatingSensor updatingSensor) {
		sensors.add(updatingSensor);
	}

	/**
	 * removes 'updatingSensor' to the list of sensors registered to this
	 * handler
	 * 
	 * @param updatingSensor
	 *            the sensor that has to be removed
	 */
	public void removeSensor(UpdatingSensor updatingSensor) {
		sensors.remove(updatingSensor);
	}
}
