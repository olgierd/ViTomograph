package es.olgierd.vitomograph.device;

public class Lamp extends Element {

	private double lightIntensity;
	
	public Lamp() {
		
		lightIntensity = 0;
		
	}
	
	public void setIntensity(double intensity) {
		lightIntensity = intensity;
	}
	
	
}
