package scampi;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class CameraUI {

	public static final Logger logger = Logger.getLogger("scampi");

	public static final boolean DEVELOPMENT_MODE = true;

	public static final int SCREEN_WIDTH = 320;
	public static final int SCREEN_HEIGHT = 240;

	public static final int FRAMERATE = 30;

	public static final Font FONT = new Font("Segoe UI Symbol", Font.PLAIN, 24);

	/** The {@link JFrame} object representing the application window. */
	private final JFrame jFrame;
	/** The {@link Timer} object used to update the UI at regular intervals. */
	private final Timer timer;

	public CameraUI(){

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

	}

	/** Populates the given pane with the contents of the app window. */
	private void initPane(Container pane){

		if(!(pane.getLayout() instanceof BorderLayout)){
			pane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		if(!DEVELOPMENT_MODE){
			// Transparent 16 x 16 pixel cursor image
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			// Create a new blank cursor
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
			// Set the blank cursor to the JFrame
			pane.setCursor(blankCursor);
		}

		JLabel label = new JLabel("Hello world!");
		label.setFont(FONT);
		label.setForeground(new Color(0x00ffff));
		pane.add(label);

		// TODO: Add stuff
	}

	/** Called by the timer to update the window contents. */
	private void update(){



		jFrame.repaint();
	}

	public static void main(String[] args){
		logger.info("Initialising");
		SwingUtilities.invokeLater(CameraUI::new);
	}

}
