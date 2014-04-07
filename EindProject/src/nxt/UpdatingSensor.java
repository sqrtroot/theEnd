package nxt;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 */
public interface UpdatingSensor {
	public void updateState();

	public SensorType getSensorType();
}
