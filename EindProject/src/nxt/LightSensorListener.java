package nxt;
/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 *@version 1.0
 */
public interface LightSensorListener {
	/**
	 * if implemented gets called when one of the lightsensor (colorsensor/lightsensor) has changed
	 * 
	 * @param position the position of the sensor used
	 * @param updatingsensor the sensor that is been updated
	 * @param oldValue	the value before the change
	 * @param newValue	the value after the change
	 */
	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue);
}
