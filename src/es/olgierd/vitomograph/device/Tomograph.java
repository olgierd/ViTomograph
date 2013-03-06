package es.olgierd.vitomograph.device;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tomograph {

	private BufferedImage img;
	private Lamp lamp;
	private DetectorArray detectorarray;
	private double radius;
	
	public Tomograph(BufferedImage img, int numberOfDetectors, double beamWidth) {
		
		this.img = img;
		getRadius();
		
		
		lamp = new Lamp(radius);
		detectorarray = new DetectorArray(numberOfDetectors, radius, beamWidth);
		
	}

	private double getRadius() {
		
		double x = img.getWidth(), y = img.getHeight();
		
		return Math.sqrt(x*x + y*y) / 2;
		
	}
	
}
