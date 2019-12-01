package com.joaquinonsoft.openimaj;

import java.io.IOException;
import java.net.URL;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.shape.Ellipse;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
    	//Create an image
    	MBFImage image = null;
		try {
			image = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(image != null) {
			System.out.println(image.colourSpace);
	        
			DisplayUtilities.display(image);
			DisplayUtilities.display(image.getBand(0), "Red Channel");		
			
			MBFImage clone = image.clone();
			for (int y=0; y<image.getHeight(); y++) {
				for(int x=0; x<image.getWidth(); x++) {
					clone.getBand(1).pixels[y][x] = 0;
					clone.getBand(2).pixels[y][x] = 0;
				}
			}
			DisplayUtilities.display(clone, "Clone");		
			
			MBFImage clone2 = image.clone();
			clone2.getBand(1).fill(0f);
			clone2.getBand(2).fill(0f);
			DisplayUtilities.display(clone2, "Clone 2");		
			
			
			image.processInplace(new CannyEdgeDetector());
			
			image.drawShapeFilled(new Ellipse(700f, 450f, 20f, 10f, 0f), RGBColour.WHITE);
			image.drawShapeFilled(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.WHITE);
			image.drawShapeFilled(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.WHITE);
			image.drawShapeFilled(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.WHITE);
			image.drawText("OpenIMAJ is", 425, 300, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
			image.drawText("Awesome", 425, 330, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
			
			DisplayUtilities.display(image, "Edge detection.");
		}
    }
}
