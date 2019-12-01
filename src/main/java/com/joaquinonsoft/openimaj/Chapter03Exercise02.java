package com.joaquinonsoft.openimaj;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.segmentation.FelzenszwalbHuttenlocherSegmenter;
import org.openimaj.image.segmentation.SegmentationUtilities;

/**
 * 3.1.2. Exercise 2: A real segmentation algorithm
 * 
 * The segmentation algorithm we just implemented can work reasonably well, 
 * but is rather naïve. OpenIMAJ contains an implementation of a popular 
 * segmentation algorithm called the FelzenszwalbHuttenlocherSegmenter.
 * 
 * Try using the FelzenszwalbHuttenlocherSegmenter for yourself and see 
 * how it compares to the basic segmentation algorithm we implemented. 
 * You can use the SegmentationUtilities.renderSegments() static method 
 * to draw the connected components produced by the segmenter.
 */
public class Chapter03Exercise02 {
	public static void main( String[] args ) {
		//Create an image
		MBFImage input = null;
		try {
			input = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(input != null) {    
			//input = ColourSpace.convert(input, ColourSpace.CIE_Lab);

			FelzenszwalbHuttenlocherSegmenter<MBFImage> segmenter = new FelzenszwalbHuttenlocherSegmenter<MBFImage>();
			List<ConnectedComponent> components = segmenter.segment(input);
			
						
			MBFImage segImage = SegmentationUtilities.renderSegments(input, components);
			
			System.out.println(segImage.getColourSpace());
			DisplayUtilities.display(segImage, "Chapter 03 - Exercise 02");
		}
		
		System.out.println("Done");

	}
}
