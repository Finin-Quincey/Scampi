package scampi;

import java.awt.image.BufferedImage;

/**
 * An object capable of returning a frame as a {@link BufferedImage}. This allows picam-specific stuff to be
 * substituted for a dummy implementation when on a development machine (i.e. not a Raspberry Pi) so the rest of the
 * code can be tested.
 *
 * @author Finin Quincey
 */
public interface FrameSupplier {

	/** Retrieves and returns the current frame from this image supplier, as a {@link BufferedImage}. */
	BufferedImage getFrame();

}
