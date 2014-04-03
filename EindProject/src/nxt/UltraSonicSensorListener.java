package nxt;
/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 *
 */
public interface UltraSonicSensorListener {
	public void ultraSonicChanged(UpdatingSensor us, float oldValue,
			float newValue);
}
