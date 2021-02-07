package scampi.camera;

import java.awt.image.BufferedImage;

public class DummyFrameSupplier implements FrameSupplier {

	@Override
	public BufferedImage getFrame(){
		return null;
	}

}
