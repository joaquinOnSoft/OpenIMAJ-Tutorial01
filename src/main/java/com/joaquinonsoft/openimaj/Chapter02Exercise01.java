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
 * 2.1.1. Exercise 1: DisplayUtilities
 * 
 * Opening lots of windows can waste time and space 
 * (for example if you wanted to view images on every iteration of a process
 * within a loop). In OpenIMAJ we provide a facility to open a named display so 
 * that was can reuse the display referring to it by name. Try to do this with 
 * all the images we display in this tutorial. Only 1 window should open for 
 * the whole tutorial.
 */
public class Chapter02Exercise01 {
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
	        
			JFrame frame =DisplayUtilities.createNamedWindow("window", "OpenIMJ Chapter 02");

			for(int i=0; i<5; i++) {
				switch (i) {
				case 0:
					frame.setTitle("Original");
					DisplayUtilities.display(image, frame);
					break;
				case 1:
					frame.setTitle("Red Channel");
					DisplayUtilities.display(image.getBand(0), frame);		
					break;
				case 2:
					MBFImage clone = image.clone();
					for (int y=0; y<image.getHeight(); y++) {
						for(int x=0; x<image.getWidth(); x++) {
							clone.getBand(0).pixels[y][x] = 0;
							clone.getBand(2).pixels[y][x] = 0;
						}
					}

					frame.setTitle("Clone");
					DisplayUtilities.display(clone, frame);					
					break;
				case 3:
					MBFImage clone2 = image.clone();
					clone2.getBand(1).fill(0f);
					clone2.getBand(2).fill(0f);

					frame.setTitle("Clone II");
					DisplayUtilities.display(clone2, frame);			
					break;
				case 4:
					MBFImage clone3 = image.clone();
					clone3.processInplace(new CannyEdgeDetector());
					
					clone3.drawShapeFilled(new Ellipse(700f, 450f, 20f, 10f, 0f), RGBColour.WHITE);
					clone3.drawShapeFilled(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.WHITE);
					clone3.drawShapeFilled(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.WHITE);
					clone3.drawShapeFilled(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.WHITE);
					clone3.drawText("OpenIMAJ is", 425, 300, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
					clone3.drawText("Awesome", 425, 330, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
					
					frame.setTitle("Edge detection.");
					DisplayUtilities.display(clone3, frame);
				}
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
    }
}
