package sensors;

import java.util.ArrayList;

import lejos.nxt.SensorPort;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 */
public class MyColorSensor extends lejos.nxt.ColorSensor implements
		UpdatingSensor {

	private float value;
	private SensorPosition position;

	private static final SensorType SENSORTYPE = SensorType.Colorsensor;

	private ArrayList<LightSensorListener> listeners = new ArrayList<LightSensorListener>();

	private int zero = 1023;
	private int hundred = 0;

	/**
	 * The constructor for the MyColorSensor
	 * 
	 * @param sensorport
	 *            the port on which the color sensor is attached to the NXT
	 * @param position
	 *            the position the NXT
	 * @see SensorPort
	 * @see SensorPosition
	 */
	public MyColorSensor(SensorPort sensorport, SensorPosition position) {
		super(sensorport);
		this.position = position;
		setFloodlight(Color.RED);
		setFloodlight(true);
		SensorHandler.getInstance().addSensor(this);
	}

	/**
	 * This method updates the currently read light value from the surface and
	 * forwards it to all listeners
	 */
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

	/**
	 * adds the listener to the list of listeners
	 * 
	 * @param listener
	 *            the listener that needs to be added
	 * @see LightSensorListener
	 */
	public void addListener(LightSensorListener listener) {
		listeners.add(listener);
	}

	/**
	 * deletes the listener from the list of listeners
	 * 
	 * @param listener
	 *            the listener that has to be deleted
	 * @see LightSensorListener
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
	 * returns the calibrated light value
	 * 
	 * @return integer ranging from 0-100
	 */
	@Override
	public int getLightValue() {
		if (hundred == zero)
			return 0;
		return 100 * (getRawLightValue() - zero) / (hundred - zero);
	}

	/**
	 * returns the lowest value that has been used to calibrate the sensor
	 * 
	 * @return zero the value set as 0%
	 */
	@Override
	public int getLow() {
		return zero;
	}

	/**
	 * returns the highest value that has been used to calibrate the sensor
	 * 
	 * @return hundred the value set as 100%
	 */
	@Override
	public int getHigh() {
		return hundred;
	}

	/**
	 * sets the highest value used for calibration
	 * 
	 * @param value
	 *            the highest value
	 */
	@Override
	public void setHigh(int value) {
		hundred = value;
	}

	/**
	 * sets the lowest value used for calibration
	 * 
	 * @param value
	 *            the lowest value
	 */
	@Override
	public void setLow(int value) {
		zero = value;
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
