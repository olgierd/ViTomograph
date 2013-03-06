package es.olgierd.vitomograph.device;

import java.util.ArrayList;

public class DetectorArray {

	private ArrayList<Detector> detectorList;
	
	
	
	public DetectorArray(int numberOfDetectors) {
		detectorList = new ArrayList<Detector>();
		
		
		for (int i = 0; i < numberOfDetectors; i++) {
			detectorList.add(new Detector());
		}
		
	}
	
	
}
