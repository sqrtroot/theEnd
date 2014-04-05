package nxt;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import lejos.nxt.LCD;

public class GUI {
	private Graphics g;
	private boolean error = false;

	public GUI() {
		g = new Graphics();
		LCD.setAutoRefresh(false);
	}

	public void showError(String errorMessage) {
		error = true;
		Font font = Font.getSmallFont();
		int imageXPosition = (LCD.SCREEN_WIDTH / 2) - Icons.error.getWidth();
		int stringYPosition = Icons.error.getHeight() + 2;
		int stringXPosition = errorMessage.length() / 2;

		g.setFont(font);
		g.drawImage(Icons.error.getIcon(), imageXPosition, 1, 0);
		g.drawString(errorMessage, stringXPosition, stringYPosition, 1);

	}

	public void sensorAlright(SensorPosition position) {
		if (!error) {
			Font font = Font.getSmallFont();
			g.setFont(font);
			Image icon = Icons.ok.getIcon();
			int imageYposition = font.getHeight() + 1;
			if (position == SensorPosition.Left) {
				String message = "Left sensor";

				g.drawString("Left sensor", 0, 0, 1);
				g.drawImage(icon, 0, imageYposition, 1);

			} else {
				String message = "Right Sensor";
				int stringXPosition = 100 - message.length();
				int imageXPosition = 100 - icon.getWidth();

				g.drawString(message, stringXPosition, 0, 1);
				g.drawImage(icon, imageXPosition, imageXPosition, 1);
			}
		}
	}

	public void sensorError(SensorPosition position) {
		if (!error) {
			if (position == SensorPosition.Left) {

			} else {

			}
		}
	}

	public void cancleError() {
		error = false;
		LCD.clear();
	}
}
