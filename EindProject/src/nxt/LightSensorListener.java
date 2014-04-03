package nxt;
/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 *
 */
public interface LightSensorListener {

	public void lightSensorChanged(Position position,
			UpdatingSensor updatingsensor, float oldValue, float newValue);
}
