package scampi.camera;

import scampi.ScampiUI;

import java.awt.image.BufferedImage;

public class PiCameraFrameSupplier implements FrameSupplier {



	private final int width, height;

	private BufferedImage frame;

	public PiCameraFrameSupplier(int width, int height){

		this.width = width;
		this.height = height;

		frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	}

	@Override
	public BufferedImage getFrame(){


		ScampiUI.logger.info(String.format("Frame: %s", frame));

		return frame; // Keep previous frame if the capture failed
	}

}
