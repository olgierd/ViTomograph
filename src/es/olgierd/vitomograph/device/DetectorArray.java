package es.olgierd.vitomograph.device;

import java.util.ArrayList;

public class DetectorArray {

	private ArrayList<Detector> detectorList;
	
	
	public DetectorArray(int numberOfDetectors, double radius, double beamWidth) {
		
		detectorList = new ArrayList<Detector>();
		
		Detector temp;
		double temp_angle;
		double angularDistance = beamWidth / numberOfDetectors;
		
		for (int i = 0; i < numberOfDetectors; i++) {
			
			temp = new Detector();
			temp.ID = i;
			
			temp.setRadius(radius);
			
			temp_angle = 180 - beamWidth/2 + i*angularDistance +angularDistance/2;
			
			temp.setAngularLocation(temp_angle);
			temp.calculateLocation();
			
			detectorList.add(temp);
		}
		
	}
	
	public ArrayList<Detector> getDetectors() {
		return detectorList;
	}
	
	public void rotateToAngle(double angle) {
		
		for(Detector d : detectorList) {
			d.setAngularLocation(d.getAngularRotation());
		}
		
	}
	
}
