package es.olgierd.vitomograph.device;

import java.awt.Point;

public abstract class Element {

	private double x, y; 
	private double angularLocation;
	private double rotationAngle;
	private double radius;
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public Point getLocation() {
		return new Point((int)(x+0.5), (int)(y+0.5));
	}
	
	public void calculateLocation() {
	
		
		double radianAngle = Math.toRadians(angularLocation + rotationAngle);
		
		x = Math.sin(radianAngle) * radius;
		y = Math.cos(radianAngle) * radius;
		
		x += radius;
		y += radius;
//		
	}
	
	public double getAngularRotation() {
		return angularLocation;
	}
	
	public void setAngularLocation(double angle) {
		
		angularLocation = angle;
		calculateLocation();
	}
	
	public void setRotationAngle(double angle) {
		rotationAngle = angle;
		calculateLocation();
	}
	
}
