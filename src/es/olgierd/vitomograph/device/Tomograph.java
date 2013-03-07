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
	private Point picLocation;
	
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
		
		picLocation = new Point((int)(radius - x/2),  (int)(radius - y/2));
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
	
	public Point getPicLocation() {
	    return picLocation;
	}
	
	public void makeLine() {
	    
	    Point from, to;
	    
	    from = lamp.getLocation();
	    to = detectorarray.getDetectors().get(0).getLocation();
	    
	    int diffx, diffy;
	    
	    diffx = from.x - to.x;
	    diffy = from.y - to.y;


	    int i, j;
	    
	    if(Math.abs(diffx) > Math.abs(diffy)) {
		
		System.out.println("ok");
		
		double direcCoeff = (float)diffy/diffx;
		
		for(int x=0; x<diffx*2; x++) {
		    
		    i = x + from.x - picLocation.x;
		    j = (int)(from.y + direcCoeff * x) - picLocation.y;
		    
		    System.out.println("TRYING @ " + i + " " + j);
		    if( i > 0 && j > 0 && i < img.getWidth() && j < img.getHeight()) {
			img.setRGB(i, j, 0xffff00);
			System.out.println("Drawing @" + i + " " + j);
		    }
		    
		    
		}
		
		
		
	    }
	    
	}
	
	
	
	
}
