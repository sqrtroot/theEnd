package nxt;
/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 *@version 1.0
 */
public interface LightSensorListener {

	public void lightSensorChanged(SensorPosition position,
			UpdatingSensor updatingsensor, float oldValue, float newValue);
}
