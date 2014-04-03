package nxt;
/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 *
 */
public interface UpdatingSensor {
	public void updateState();
	public SensorType getSensorType();
}
