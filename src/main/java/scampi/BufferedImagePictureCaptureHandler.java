package scampi;

import uk.co.caprica.picam.PictureCaptureHandler;

import java.awt.image.BufferedImage;

public class BufferedImagePictureCaptureHandler implements PictureCaptureHandler<BufferedImage> {

	@Override
	public void begin() throws Exception{

	}

	@Override
	public int pictureData(byte[] bytes) throws Exception{
		return 0;
	}

	@Override
	public void end() throws Exception{

	}

	@Override
	public BufferedImage result(){
		return null;
	}
}
