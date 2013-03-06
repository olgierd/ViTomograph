package es.olgierd.vitomograph.device;

import java.awt.Point;

public abstract class Element {

	private double x, y; 
	private Point location;
	private double angularLocation;
	private double radius;
	
	public Point getLocation() {
		return new Point((int)(x+0.5), (int)(y+0.5));
	}
	
	public void calculateLocation() {
	
		location = new Point();
		
		double radianAngle = Math.toRadians(angularLocation);
		
		x = Math.sin(radianAngle) * radius;
		y = Math.sin(radianAngle) * radius;
		
		x += radius;
		y += radius;
		
	}
	
	public void setAngularLocation(double angle) {
		
		angularLocation = angle;
		calculateLocation();
	}
	
	
}
