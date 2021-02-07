package scampi;

import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import scampi.gpio.GPIOManager;
import scampi.gpio.PiGPIOManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class ScampiUI {

	public static final Logger logger = Logger.getLogger("scampi");

	/** True to show the cursor and window borders, false to make it fullscreen (for use on the touchscreen). */
	public static final boolean DEVELOPMENT_MODE = false;

	/** Width of the LCD screen in pixels */
	public static final int SCREEN_WIDTH = 320;
	/** Height of the LCD screen in pixels */
	public static final int SCREEN_HEIGHT = 240;

	/** Camera sensor width in pixels */
	public static final int CAMERA_WIDTH = 320;
	/** Camera sensor height in pixels */
	public static final int CAMERA_HEIGHT = 240;

	public static final int FRAMERATE = 10;

	public static final Font FONT = new Font("Segoe UI Symbol", Font.PLAIN, 24);

	/** The {@link JFrame} object representing the application window. */
	private final JFrame jFrame;
	/** The {@link Timer} object used to update the UI at regular intervals. */
	private final Timer timer;

	/** A {@link JLabel} that holds the video feed itself. This label's icon image is updated each frame to display the
	 * video feed. */
	private JLabel videoContainer;

	private FrameSupplier frameSupplier;
	// Hardware interfaces
	private GPIOManager gpioManager;

	public ScampiUI(){

		// Create and set up the window
		jFrame = new JFrame("Camera Interface");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);

		// Initialise the window contents
		initPane(jFrame.getContentPane());

		// Display the window
		if(!DEVELOPMENT_MODE){
			jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			jFrame.setUndecorated(true);
		}
		jFrame.pack();
		jFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		jFrame.setVisible(true);

		// Set up and start the timer
		timer = new Timer(1000/FRAMERATE, e -> this.update());
		timer.setInitialDelay(50);
		timer.start();

		try {
			frameSupplier = new PiCameraFrameSupplier(CAMERA_WIDTH, CAMERA_HEIGHT);
		}catch(FailedToRunRaspistillException e){
			e.printStackTrace();
		}
		gpioManager = new PiGPIOManager();

	}

	/** Populates the given pane with the contents of the app window. */
	private void initPane(Container pane){

		if(!(pane.getLayout() instanceof BorderLayout)){
			pane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		pane.setBackground(Color.DARK_GRAY);

		if(!DEVELOPMENT_MODE){
			// Transparent 16 x 16 pixel cursor image
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			// Create a new blank cursor
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
			// Set the blank cursor to the JFrame
			pane.setCursor(blankCursor);
		}

		videoContainer = new JLabel((ImageIcon)null); // Will be initialised later
		videoContainer.setVerticalTextPosition(SwingConstants.CENTER);
		videoContainer.setFont(FONT);
		videoContainer.setForeground(new Color(0xff6655));
		videoContainer.setText("Camera error!");

		pane.add(videoContainer);

	}

	/** Called by the timer to update the window contents. */
	private void update(){

		if(frameSupplier != null){

			BufferedImage frame = frameSupplier.getFrame();

			videoContainer.setIcon(new ImageIcon(frame));
			videoContainer.setText(null);
		gpioManager.setStatusColour(Color.getHSBColor((System.currentTimeMillis() % 3000) / 3000f, 1, 1));

		}
		gpioManager.setShutterLEDState(System.currentTimeMillis() % 1000 > 500);

		jFrame.repaint();
	}

	public static void main(String[] args){
		logger.info("Initialising");
		SwingUtilities.invokeLater(ScampiUI::new);
	}

}
