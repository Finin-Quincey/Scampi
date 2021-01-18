package scampi;

import uk.co.caprica.picam.*;
import uk.co.caprica.picam.enums.Encoding;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PiCameraFrameSupplier implements FrameSupplier {

	/** A reference to the {@link Camera} object. */
	private Camera camera;

	private final int width, height;

	public PiCameraFrameSupplier(int width, int height) throws CameraException {

		this.width = width;
		this.height = height;

		// Camera setup
		CameraConfiguration cameraConfig = CameraConfiguration.cameraConfiguration()
				.width(width)
				.height(height)
				.encoding(Encoding.BGR24); // Blue-green-red matches the buffered image byte format below

		camera = new Camera(cameraConfig);

	}

	@Override
	public BufferedImage getFrame(){

		BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		try{

			byte[] bytes = camera.takePicture(new ByteArrayPictureCaptureHandler());

			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			ImageIO.read(stream);

		}catch(CaptureFailedException | IOException e){
			e.printStackTrace();
		}

		return frame;
	}

}
