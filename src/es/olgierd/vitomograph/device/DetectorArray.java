package es.olgierd.vitomograph.device;

import java.util.ArrayList;

public class DetectorArray {

	private ArrayList<Detector> detectorList;
	
	
	
	public DetectorArray(int numberOfDetectors, double radius, double beamWidth) {
		
		detectorList = new ArrayList<Detector>();
		
		Detector temp;
		double temp_angle;
		
		for (int i = 0; i < numberOfDetectors; i++) {
			
			temp = new Detector();
			temp.ID = i;
			
			temp_angle = (180 - ((float)i / numberOfDetectors) * beamWidth) - beamWidth/2;
			temp.setAngularLocation(temp_angle);
			temp.calculateLocation();
			
			detectorList.add(temp);
		}
		
	}
	
	
}
