package gui;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import sensors.SensorPosition;
import lejos.nxt.Button;
import lejos.nxt.LCD;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 0.6
 * @since 01-04-2014
 */
public class GUI {
	private Graphics g;
	private boolean popUp = false;
	private static final int messageBoxTopPadding = 5;
	private static final int messageBoxLeftPadding = 2;
	private static final int fontPixalWidth = 6;
	private static final Font font = Font.getDefaultFont();
	private static final int borderXPosition = 0;
	private static final int borderYPosition = 0;

	/**
	 * the constructor for the gui class
	 */
	public GUI() {
		g = new Graphics();
	}

	/**
	 * shows a error Pop-up with the given string
	 * 
	 * @param errorMessage
	 *            the string to be displayed
	 */
	public void showErrorPopUp(String errorMessage) {
		popUp = true;

		int borderHeight = Icons.error.getHeight() + font.getHeight()
				+ messageBoxTopPadding;
		int borderWidth;
		if (errorMessage.length() * fontPixalWidth < Icons.error.getWidth()) {
			borderWidth = Icons.error.getWidth() + messageBoxLeftPadding;
		} else {
			borderWidth = errorMessage.length() * fontPixalWidth
					+ messageBoxLeftPadding;
		}

		int imageXPosition = (borderWidth / 2) - (Icons.error.getWidth()/2);
		int imageYPosition = borderYPosition + messageBoxTopPadding;

		int stringYPosition = imageYPosition + Icons.error.getHeight();
		int stringXPosition = borderXPosition + messageBoxLeftPadding;

		g.setFont(font);
		g.clear();
		g.drawRect(borderXPosition, borderYPosition, borderWidth, borderHeight);
		g.drawImage(Icons.error.getIcon(), imageXPosition, 1, 0);
		g.drawString(errorMessage, stringXPosition, stringYPosition, 0);

	}

	/**
	 * shows a normal pop-up with the given string displayed
	 * 
	 * @param message
	 *            the message to be displayed
	 */
	public void showPopUp(String message) {
		popUp = true;

		int borderHeight = font.getHeight() + messageBoxTopPadding;
		int borderWidth = message.length() * fontPixalWidth
				+ messageBoxLeftPadding;

		int stringYPosition = borderYPosition + messageBoxTopPadding;
		int stringXPosition = borderXPosition + messageBoxLeftPadding;

		g.setFont(font);
		g.clear();
		g.drawRect(borderXPosition, borderYPosition, borderWidth, borderHeight);
		g.drawString(message, stringXPosition, stringYPosition, 0);

	}

	/**
	 * shows if a sensor is alright
	 * 
	 * @param position
	 *            the position on the vehicle of the sensor
	 */
	public void lightSensorAlright(SensorPosition position) {
		if (!popUp) {
			g.setFont(font);
			Image icon = Icons.ok.getIcon();
			int imageYposition = font.getHeight() + 1;

			g.clear();

			if (position == SensorPosition.Left) {
				String message = "Left";
				g.drawString(message, 0, 0, 0);
				g.drawImage(icon, 0, imageYposition, 0);

			} else {
				String message = "Right";
				int stringXPosition = 100 - message.length()*fontPixalWidth;
				int imageXPosition = 100 - icon.getWidth();

				g.drawString(message, stringXPosition, 0, 0);
				g.drawImage(icon, imageXPosition, imageYposition, 0);
			}
		}
	}

	/**
	 * shows if a sensor is not on path
	 * 
	 * @param position
	 *            the position on the vehicle of the sensor
	 */
	public void lightensorError(SensorPosition position) {
		if (!popUp) {
			g.setFont(font);
			Image icon = Icons.error.getIcon();
			int imageYposition = font.getHeight() + 1;

			g.clear();

			if (position == SensorPosition.Left) {
				String message = "Left";
				g.drawString(message, 0, 0, 0);
				g.drawImage(icon, 0, imageYposition, 0);

			} else {
				String message = "Right";
				int stringXPosition = 100 - message.length()*fontPixalWidth;
				int imageXPosition = 100 - icon.getWidth();

				g.drawString(message, stringXPosition, 0, 0);
				g.drawImage(icon, imageXPosition, imageYposition, 0);
			}
		}
	}

	/**
	 * cancel the pop-up and show the normal screen again
	 */
	public void cancelPopUp() {
		if (popUp) {
			popUp = false;
			g.clear();
		}
	}
}
