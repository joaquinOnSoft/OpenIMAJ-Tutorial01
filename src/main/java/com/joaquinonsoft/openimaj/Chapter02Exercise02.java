package com.joaquinonsoft.openimaj;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

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
public class Chapter02Exercise02 {
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
			
			image.processInplace(new CannyEdgeDetector());

			image.drawShapeFilled(new Ellipse(700f, 450f, 20f, 10f, 0f), RGBColour.WHITE);
			image.drawShape(new Ellipse(700f, 450f, 20f, 10f, 0f), RGBColour.BLUE);
			
			image.drawShapeFilled(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.WHITE);
			image.drawShape(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.BLUE);
			
			image.drawShapeFilled(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.WHITE);
			image.drawShape(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.BLUE);
			
			image.drawShapeFilled(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.WHITE);
			image.drawShape(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.BLUE);

			image.drawText("OpenIMAJ is", 425, 300, HersheyFont.ASTROLOGY, 20, RGBColour.BLUE);
			image.drawText("Awesome", 425, 330, HersheyFont.ASTROLOGY, 20, RGBColour.BLUE);
			
			DisplayUtilities.display(image, "Chapter 02, Exercise 02");
		}
	}
}
