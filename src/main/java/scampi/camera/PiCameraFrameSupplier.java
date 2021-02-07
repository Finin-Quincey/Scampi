package scampi.camera;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import scampi.ScampiUI;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class PiCameraFrameSupplier implements FrameSupplier {

	/** A reference to the {@link RPiCamera} object. */
	private RPiCamera camera;

	private final int width, height;

	private BufferedImage frame;

	public PiCameraFrameSupplier(int width, int height) throws FailedToRunRaspistillException {

		this.width = width;
		this.height = height;

		camera = new RPiCamera().setWidth(width).setHeight(height).turnOffPreview();

		frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	}

	@Override
	public BufferedImage getFrame(){

		try {
			frame = camera.takeBufferedStill();
		}catch(IOException | InterruptedException e){
			e.printStackTrace();
		}

		ScampiUI.logger.info(String.format("Frame: %s", frame));

		return frame; // Keep previous frame if the capture failed
	}

}
