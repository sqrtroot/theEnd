package nxt;

import java.util.ArrayList;

public class SensorHandler extends Thread {
	private static SensorHandler singleton;
	private ArrayList<UpdatingSensor> sensors = new ArrayList<UpdatingSensor>();
	private final long timeOut = 100;

	private SensorHandler() {
		this.setDaemon(true);
		start();
	}

	public static SensorHandler getInstance() {
		if (singleton == null) {
			singleton = new SensorHandler();
		}
		return singleton;

	}

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

	public void addSensor(UpdatingSensor updatingSensor) {
		sensors.add(updatingSensor);
	}

	public void removeSensor(UpdatingSensor updatingSensor) {
		sensors.remove(updatingSensor);
	}
}
