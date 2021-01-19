package scampi;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.enums.Encoding;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class PiCameraFrameSupplier implements FrameSupplier {

	/** A reference to the {@link RPiCamera} object. */
	private RPiCamera camera;

	private final int width, height;

	public PiCameraFrameSupplier(int width, int height) throws FailedToRunRaspistillException {

		this.width = width;
		this.height = height;

		camera = new RPiCamera().setWidth(width).setHeight(height).setEncoding(Encoding.BMP);

	}

	@Override
	public BufferedImage getFrame(){

		BufferedImage frame = null;

		try {
			frame = camera.takeBufferedStill(width, height);
		}catch(IOException | InterruptedException e){
			e.printStackTrace();
		}

		return frame;
	}

}
