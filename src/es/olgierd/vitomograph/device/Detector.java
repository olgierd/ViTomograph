package es.olgierd.vitomograph.device;

public class Detector extends Element {

	public int ID;
	private double value;
	
	public void setValue(double newVal) {
	    value = newVal;
	}
	
	public void addValue(double val) {
	    value += val;
	}
	
	public double getValue() {
	    return value;
	}
	
	public void reset() {
	    value = 0;	    
	}
	
	
}
