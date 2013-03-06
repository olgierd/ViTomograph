package es.olgierd.vitomograph.device;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Tomograph {

	private BufferedImage img;
	private Lamp lamp;
	private DetectorArray detectorarray;
	private double radius;
	private BufferedImage outputImage;
	
	
	public Tomograph(BufferedImage img, int numberOfDetectors, double beamWidth) {
		
		this.img = img;
		calculateRadius();
		
		System.out.println(img.getHeight() + " " + img.getWidth());
		
		lamp = new Lamp(radius);
		detectorarray = new DetectorArray(numberOfDetectors, radius, beamWidth);
		
	}

	private void calculateRadius() {
		
		double x = img.getWidth(), y = img.getHeight();
		
		radius = Math.sqrt(x*x + y*y) / 2;
		
	}
	
	public ArrayList<Point> getDetectorsLocation() {
		
		ArrayList<Point> locations = new ArrayList<Point>();
		
		for(Detector det : detectorarray.getDetectors()) {
			locations.add(det.getLocation());
		}
		
		return locations;
	}
	
	public Point getLampLocation() {
		return lamp.getLocation();
	}
	
	public void rotateToAngle(double angle) {
		
		detectorarray.rotateToAngle(angle);
		lamp.setRotationAngle(angle);
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public double getRadius() {
		return radius;
	}
}
