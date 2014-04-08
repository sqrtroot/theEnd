package sensors;


/**
 * This is the ultrasonic sensor listener interface
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 */
public interface UltrasonicSensorListener {
	/**
	 * When implemented this method can be called to do something when the measured value
	 * from the ultrasonic sensor changes 
	 * 
	 * @param us
	 * 			The sensor from which the value is changed
	 * @param oldValue
	 * 			The old value from the sensor
	 * @param newValue
	 * 			The new value from the sensor
	 */
	public void ultraSonicChanged(UpdatingSensor us, float oldValue,
			float newValue);
}
