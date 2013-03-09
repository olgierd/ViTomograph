package es.olgierd.vitomograph.utils;

import java.awt.Point;
import java.util.ArrayList;

public class Helpers {

    // returns list of points making line from point A to point B
    public static ArrayList<Point> getLine(Point A, Point B) {
	
	ArrayList<Point> line = new ArrayList<Point>();
	
	int dx = A.x - B.x;
	int dy = B.x - B.y;
	
	
	double coeff = 0.0;
	int direction = 0;
	
	if(Math.abs(dx) > Math.abs(dy)) {	// X is longer - X will be the slow direction
	    
	    coeff = dx / dy;
	    
	    
	    if(A.x < B.x) coeff = 1;
	    else coeff = -1;
	    
	    
	    
	    for(int i = 0; i <= dx; i += coeff) {
		
		line.add(new Point (
					A.x+i,
					2
					)
				    );
		
		
	    }
	    
		
	    
	    
	    
	    
	} else {     // Y is longer - Y will be the slow direction
	    
	    coeff = dy / dx;
	    
	    
	    
	    
	    
	}
	
	
	
	
	
	
	
	
	
	return line;
	
    }
    
    
    
}
