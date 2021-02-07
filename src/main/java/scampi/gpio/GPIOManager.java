package scampi.gpio;

import java.awt.*;

public interface GPIOManager {

	/**
	 * Sets the status LED to the given RGB colour.
	 * @param colour The colour to set (alpha will have no effect)
	 */
	void setStatusColour(Color colour);

	/**
	 * Sets the state of the shutter button LED.
	 * @param on True to turn the shutter LED on, false to turn it off.
	 */
	void setShutterLEDState(boolean on);

	/**
	 * Queries the state of the shutter button.
	 * @return True if the shutter button is pressed, false if not.
	 */
	boolean isShutterPressed();

}
