package gui;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import sensors.Position;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;

/**
 * 
 * @author Robert Bezem <robert.bezem@student.hu.nl>
 * @version 1.0
 * @since 01-04-2014
 */
public class GUI {
	private Graphics g;
	private boolean popUp = false;

	private static final Font FONT = Font.getDefaultFont();

	private static final int MESSAGE_BOX_TO_PADDING = 5;
	private static final int MESSAGE_BOX_LEFT_PADDING = 2;
	private static final int FONT_WIDTH = 6;
	private static final int BORDER_X_POSITION = 0;
	private static final int BORDER_Y_POSITION = 0;
	private static final int SCREEN_WIDTH = 100;
	private static final int IMAGE_Y_POSITION = FONT.getHeight() + 1;

	/**
	 * the constructor for the GUI-class
	 */
	public GUI() {
		g = new Graphics();
	}

	/**
	 * shows an error Pop-up with the given string 'errorMessage'
	 * 
	 * @param errorMessage
	 *            the string to be displayed
	 */
	public void showErrorPopUp(String errorMessage) {
		popUp = true;

		int borderHeight = Icons.error.getHeight() + FONT.getHeight()
				+ MESSAGE_BOX_TO_PADDING;
		int borderWidth;
		if (errorMessage.length() * FONT_WIDTH < Icons.error.getWidth()) {
			borderWidth = Icons.error.getWidth() + MESSAGE_BOX_LEFT_PADDING;
		} else {
			borderWidth = errorMessage.length() * FONT_WIDTH
					+ MESSAGE_BOX_LEFT_PADDING;
		}

		int imageXPosition = (borderWidth / 2) - (Icons.error.getWidth() / 2);
		int imageYPosition = BORDER_Y_POSITION + MESSAGE_BOX_TO_PADDING;

		int stringYPosition = imageYPosition + Icons.error.getHeight();
		int stringXPosition = BORDER_X_POSITION + MESSAGE_BOX_LEFT_PADDING;

		g.setFont(FONT);
		g.clear();
		g.drawRect(BORDER_X_POSITION, BORDER_Y_POSITION, borderWidth,
				borderHeight);
		g.drawImage(Icons.error.getIcon(), imageXPosition, 1, 0);
		g.drawString(errorMessage, stringXPosition, stringYPosition, 0);

	}

	/**
	 * shows a Pop-up with the given string 'message' displayed
	 * 
	 * @param message
	 *            the message to be displayed
	 */
	public void showPopUp(String message) {
		popUp = true;
		Sound.setVolume(Sound.VOL_MAX);

		int borderHeight = FONT.getHeight() + MESSAGE_BOX_TO_PADDING;
		int borderWidth = message.length() * FONT_WIDTH
				+ MESSAGE_BOX_LEFT_PADDING;

		int stringYPosition = BORDER_Y_POSITION + MESSAGE_BOX_TO_PADDING;
		int stringXPosition = BORDER_X_POSITION + MESSAGE_BOX_LEFT_PADDING;

		g.setFont(FONT);
		g.clear();
		g.drawRect(BORDER_X_POSITION, BORDER_Y_POSITION, borderWidth,
				borderHeight);
		g.drawString(message, stringXPosition, stringYPosition, 0);

	}

	public void showAlrightPopUp(String message) {
		popUp = true;

		int borderHeight = Icons.ok.getHeight() + FONT.getHeight()
				+ MESSAGE_BOX_TO_PADDING;
		int borderWidth;
		if (message.length() * FONT_WIDTH < Icons.error.getWidth()) {
			borderWidth = Icons.ok.getWidth() + MESSAGE_BOX_LEFT_PADDING;
		} else {
			borderWidth = message.length() * FONT_WIDTH
					+ MESSAGE_BOX_LEFT_PADDING;
		}

		int imageXPosition = (borderWidth / 2) - (Icons.error.getWidth() / 2);
		int imageYPosition = BORDER_Y_POSITION + MESSAGE_BOX_TO_PADDING;

		int stringYPosition = imageYPosition + Icons.error.getHeight();
		int stringXPosition = BORDER_X_POSITION + MESSAGE_BOX_LEFT_PADDING;

		g.setFont(FONT);
		g.clear();
		g.drawRect(BORDER_X_POSITION, BORDER_Y_POSITION, borderWidth,
				borderHeight);
		g.drawImage(Icons.ok.getIcon(), imageXPosition, 1, 0);
		g.drawString(message, stringXPosition, stringYPosition, 0);

	}

	/**
	 * shows if the sensor is facing a light surface
	 * 
	 * @param position
	 *            the position of the sensor, left or right, on the robot
	 * @deprecated use {@link showAlrightPopUp(String message)} instead
	 */
	public void lightSensorAlright(Position position) {
		if (!popUp) {
			g.setFont(FONT);
			Image icon = Icons.ok.getIcon();

			g.clear();

			if (position == Position.Left) {
				String message = "Left";
				g.drawString(message, 0, 0, 0);
				g.drawImage(icon, 0, IMAGE_Y_POSITION, 0);

			} else {
				String message = "Right";
				int stringXPosition = SCREEN_WIDTH - message.length()
						* FONT_WIDTH;
				int imageXPosition = SCREEN_WIDTH - icon.getWidth();

				g.drawString(message, stringXPosition, 0, 0);
				g.drawImage(icon, imageXPosition, IMAGE_Y_POSITION, 0);
			}
		}
	}

	/**
	 * shows if a sensor is facing a dark surface
	 * 
	 * @param position
	 *            the position of the sensor, left or right, on the robot
	 * @deprecated use {@link showErrorPopUp(String message)} instead
	 */
	public void lightensorError(Position position) {
		if (!popUp) {
			g.setFont(FONT);
			Image icon = Icons.error.getIcon();

			g.clear();

			if (position == Position.Left) {
				String message = "Left";
				g.drawString(message, 0, 0, 0);
				g.drawImage(icon, 0, IMAGE_Y_POSITION, 0);

			} else {
				String message = "Right";
				int stringXPosition = SCREEN_WIDTH - message.length()
						* FONT_WIDTH;
				int imageXPosition = SCREEN_WIDTH - icon.getWidth();

				g.drawString(message, stringXPosition, 0, 0);
				g.drawImage(icon, imageXPosition, IMAGE_Y_POSITION, 0);
			}
		}
	}

	/**
	 * cancel the pop-up and clear the screen
	 */
	public void cancelPopUp() {
		if (popUp) {
			popUp = false;
			g.clear();
		}
	}
}
