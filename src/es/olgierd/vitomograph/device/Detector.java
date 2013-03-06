package es.olgierd.vitomograph.device;

public class Detector extends Element {

	public int ID;
	private double value;
	
	public void setValue(double newVal) {
		value = newVal;
	}
	
	public double getValue() {
		return value;
	}
	
}
