package es.olgierd.vitomograph.device;

public class Lamp extends Element {

	private double lightIntensity;
	
	public Lamp(double radius) {
		lightIntensity = 0;
		setRadius(radius);
		setAngularLocation(1);
	}
	
	public void setIntensity(double intensity) {
		lightIntensity = intensity;
	}
	
}
