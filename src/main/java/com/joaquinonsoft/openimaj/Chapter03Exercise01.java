package com.joaquinonsoft.openimaj;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

/**
 * 3.1.1. Exercise 1: The PixelProcessor
 * 
 * Rather than looping over the image pixels using two for loops, it is possible to use a 
 * PixelProcessor to accomplish the same task:
 * 
 * <pre>
 * image.processInplace(new PixelProcessor<Float[]>() {
 * 		Float[] processPixel(Float[] pixel) {
 * 			...
 * 		}
 * });
 * </pre>
 * 
 * Can you re-implement the loop that replaces each pixel with its class 
 * centroid using a PixelProcessor?
 * 
 * What are the advantages and disadvantages of using a PixelProcessor?
 */
public class Chapter03Exercise01 {
	public static void main( String[] args ) {
		//Create an image
		MBFImage input = null;
		try {
			input = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(input != null) {    
			System.out.println(input.colourSpace);

			input = ColourSpace.convert(input, ColourSpace.CIE_Lab);

			System.out.println(input.colourSpace);

			FloatKMeans cluster = FloatKMeans.createExact(4);

			float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);

			FloatCentroidsResult result = cluster.cluster(imageData);

			float[][] centroids = result.centroids;
			for (float[] fs : centroids) {
				System.out.println(Arrays.toString(fs));
			}

			HardAssigner<float[],?,?> assigner = result.defaultHardAssigner();
			for (int y=0; y<input.getHeight(); y++) {
				for (int x=0; x<input.getWidth(); x++) {
					float[] pixel = input.getPixelNative(x, y);
					int centroid = assigner.assign(pixel);
					input.setPixelNative(x, y, centroids[centroid]);
				}
			}	

			input = ColourSpace.convert(input, ColourSpace.RGB);
			DisplayUtilities.display(input);

			GreyscaleConnectedComponentLabeler labeler = new GreyscaleConnectedComponentLabeler();
			List<ConnectedComponent> components = labeler.findComponents(input.flatten());

			int i = 0;
			for (ConnectedComponent comp : components) {
				if (comp.calculateArea() < 50)
					continue;
				input.drawText("Point:" + (i++), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 20);
			}

			DisplayUtilities.display(input);
		}
	}
}
