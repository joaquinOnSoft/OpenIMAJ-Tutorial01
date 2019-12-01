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
import org.openimaj.image.pixel.PixelSet;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;


/**
 * Chapter 3. Introduction to clustering, segmentation and connected components
 */
public class App {
	public static void main( String[] args ) {
		//Create an image
		MBFImage input = null;
		try {
			input = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(input != null) {    
			input = ColourSpace.convert(input, ColourSpace.CIE_Lab);

			final FloatKMeans cluster = FloatKMeans.createExact(3, 2);

			final float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);

			final FloatCentroidsResult result = cluster.cluster(imageData);

			final float[][] centroids = result.centroids;
			for (final float[] fs : centroids) {
				System.out.println(Arrays.toString(fs));
			}

			final HardAssigner<float[], ?, ?> assigner = result.defaultHardAssigner();
			for (int y = 0; y < input.getHeight(); y++) {
				for (int x = 0; x < input.getWidth(); x++) {
					final float[] pixel = input.getPixelNative(x, y);
					final int centroid = assigner.assign(pixel);
					input.setPixelNative(x, y, centroids[centroid]);
				}
			}

			input = ColourSpace.convert(input, ColourSpace.RGB);
			DisplayUtilities.display(input);

			final GreyscaleConnectedComponentLabeler labeler = new GreyscaleConnectedComponentLabeler();
			final List<ConnectedComponent> components = labeler.findComponents(input.flatten());

			int i = 0;
			for (final PixelSet comp : components) {
				if (comp.calculateArea() < 50)
					continue;
				input.drawText("Point:" + (i++), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 20);
			}

			DisplayUtilities.display(input);
			
		}
	}
}
