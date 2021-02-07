package scampi.gpio;

import com.pi4j.io.gpio.*;

import java.awt.*;

public class PiGPIOManager implements GPIOManager {

	// These numbers are WIRING PI numbers! (Despite being called GPIO_XX)
	private static final Pin PIN_WAKE 	= RaspiPin.GPIO_09;
	private static final Pin PIN_R 		= RaspiPin.GPIO_07;
	private static final Pin PIN_G 		= RaspiPin.GPIO_21;
	private static final Pin PIN_B 		= RaspiPin.GPIO_22;
	private static final Pin PIN_L 		= RaspiPin.GPIO_23;
	private static final Pin PIN_BTN 	= RaspiPin.GPIO_25;
	private static final Pin PIN_KEY1 	= RaspiPin.GPIO_01;
	private static final Pin PIN_KEY2 	= RaspiPin.GPIO_04;
	private static final Pin PIN_KEY3 	= RaspiPin.GPIO_05;

	private static final int PWM_RANGE = 100;

	private final GpioController gpio = GpioFactory.getInstance();

	private final GpioPinDigitalInput wakeSwitch;
	private final GpioPinDigitalInput shutterBtn;
	private final GpioPinDigitalInput lowerKey;
	private final GpioPinDigitalInput middleKey;
	private final GpioPinDigitalInput upperKey;

	private final GpioPinPwmOutput redLED;
	private final GpioPinPwmOutput greenLED;
	private final GpioPinPwmOutput blueLED;

	private final GpioPinDigitalOutput shutterLED;

	public PiGPIOManager(){

		// Provision pins
		wakeSwitch 	= gpio.provisionDigitalInputPin(PIN_WAKE, 	"Wake Switch", 		PinPullResistance.OFF);
		shutterBtn 	= gpio.provisionDigitalInputPin(PIN_BTN, 	"Shutter Button", 	PinPullResistance.OFF);
		lowerKey 	= gpio.provisionDigitalInputPin(PIN_KEY1, 	"Lower LCD Key", 		PinPullResistance.PULL_DOWN);
		middleKey 	= gpio.provisionDigitalInputPin(PIN_KEY2, 	"Middle LCD Key", 	PinPullResistance.PULL_DOWN);
		upperKey 	= gpio.provisionDigitalInputPin(PIN_KEY3, 	"Upper LCD Key", 		PinPullResistance.PULL_DOWN);

		redLED		= gpio.provisionSoftPwmOutputPin(PIN_R,		"Status LED Red",		0);
		greenLED	= gpio.provisionSoftPwmOutputPin(PIN_G,		"Status LED Green",	0);
		blueLED		= gpio.provisionSoftPwmOutputPin(PIN_B,		"Status LED Blue",	0);

		shutterLED	= gpio.provisionDigitalOutputPin(PIN_L,		"Shutter LED",		PinState.HIGH);

	}

	@Override
	public void setStatusColour(Color colour){
		// Remember these are inverted because it's a common-anode RGB LED!
		redLED	.setPwm((int)((1 - colour.getRed()   / 255f) * PWM_RANGE));
		greenLED.setPwm((int)((1 - colour.getGreen() / 255f) * PWM_RANGE));
		blueLED	.setPwm((int)((1 - colour.getBlue()  / 255f) * PWM_RANGE));
	}

	@Override
	public void setShutterLEDState(boolean on){
		shutterLED.setState(on);
	}

	@Override
	public boolean isShutterPressed(){
		return shutterBtn.isHigh();
	}

}
