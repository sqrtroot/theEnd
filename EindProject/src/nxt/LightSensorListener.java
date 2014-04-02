package nxt;

public interface LightSensorListener {

	public void lightSensorChanged(Position position,
			UpdatingSensor updatingsensor, float oldValue, float newValue);
}
