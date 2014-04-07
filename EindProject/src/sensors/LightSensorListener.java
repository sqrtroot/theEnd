package sensors;


/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 */
public interface LightSensorListener {
	/**
	 * 
	 * When this class is being implemented, this class will be called upon when
	 * one of the lightsensors' state changes.
	 * 
	 * @param position
	 *            the position of the sensor used
	 * @param updatingsensor
	 *            the sensor that is been updated
	 * @param oldValue
	 *            the value before the change
	 * @param newValue
	 *            the value after the change
	 */
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue);
}
