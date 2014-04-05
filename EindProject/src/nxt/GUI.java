package nxt;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import lejos.nxt.LCD;

public class GUI {
	private Graphics g;
	private boolean errorPopUp = false;
	private int black = 0x00000000;
	private int white = 0x00999999;

	public GUI() {
		g = new Graphics();
		LCD.setAutoRefresh(false);
	}

	public void showErrorPopUp(String errorMessage) {
		errorPopUp = true;
		Font font = Font.getSmallFont();
		int imageXPosition = (g.getHeight() / 2) - (Icons.error.getWidth() / 2);
		int stringYPosition = Icons.error.getHeight() + 2;
		int stringXPosition = errorMessage.length() / 2;

		int borderHeight = Icons.error.getHeight() + font.getHeight();
		int borderWidth;
		if (errorMessage.length() < Icons.error.getWidth()) {
			borderWidth = Icons.error.getWidth();
		} else {
			borderWidth = errorMessage.length();
		}

		int borderLeftPosition = (g.getWidth() / 2) - borderWidth;
		int borderUpPosition = imageXPosition - 5;

		g.setFont(font);
		g.clear();
		g.drawRect(borderLeftPosition, borderUpPosition, borderWidth,
				borderHeight);
		g.drawImage(Icons.error.getIcon(), imageXPosition, 1, 0);
		g.drawString(errorMessage, stringXPosition, stringYPosition, 1);

	}

	public void lightSensorAlright(SensorPosition position) {
		if (!errorPopUp) {
			Font font = Font.getSmallFont();
			g.setFont(font);
			Image icon = Icons.ok.getIcon();
			int imageYposition = font.getHeight() + 1;

			g.clear();

			if (position == SensorPosition.Left) {
				String message = "Left sensor";

				g.drawString(message, 0, 0, 1);
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

	public void lightensorError(SensorPosition position) {
		if (!errorPopUp) {
			Font font = Font.getSmallFont();
			g.setFont(font);
			Image icon = Icons.error.getIcon();
			int imageYposition = font.getHeight() + 1;

			g.clear();

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

	public void cancleErrorPopUp() {
		if (errorPopUp) {
			errorPopUp = false;
			g.clear();
		}
	}
}
