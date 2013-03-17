package es.olgierd.vitomograph.utils;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Helpers {

	// returns list of points making line from point A to point B
	public static ArrayList<Point> getLine(Point A, Point B) {

		ArrayList<Point> line = new ArrayList<Point>();

		int dx = A.x - B.x;
		int dy = A.y - B.y;

		double coeff = 0.0;
		int direction = 0;

		if (Math.abs(dx) > Math.abs(dy)) { // X is longer - X will be the slow direction

			coeff = (double) dy / dx;

			if (A.x < B.x)
				direction = 1;
			else
				direction = -1;

			for (int i = 0; i <= Math.abs(dx); i++)
				line.add(new Point(A.x + i * direction, A.y + (int) (direction * i * coeff + 0.5)));

		} else { // Y is longer - Y will be the slow direction

			coeff = (double) dx / dy;

			if (A.y < B.y)
				direction = 1;
			else
				direction = -1;

			for (int i = 0; i <= Math.abs(dy); i++)
				line.add(new Point(A.x + (int) (direction * i * coeff + 0.5), A.y + i * direction));

		}

		return line;
	}
	
	
	// returns list of double points making line from point A to point B
	public static ArrayList<Point2D.Double> getSoftLine(Point A, Point B) {

		ArrayList<Point2D.Double> line = new ArrayList<Point2D.Double>();

		int dx = A.x - B.x;
		int dy = A.y - B.y;

		double coeff = 0.0;
		int direction = 0;

		if (Math.abs(dx) > Math.abs(dy)) { // X is longer - X will be the slow direction

			coeff = (double) dy / dx;

			if (A.x < B.x)
				direction = 1;
			else
				direction = -1;

			for (int i = 0; i <= Math.abs(dx); i++)
				line.add(new Point2D.Double(A.x + i * direction, A.y + (direction * i * coeff + 0.5)));

		} else { // Y is longer - Y will be the slow direction

			coeff = (double) dx / dy;

			if (A.y < B.y)
				direction = 1;
			else
				direction = -1;

			for (int i = 0; i <= Math.abs(dy); i++)
				line.add(new Point2D.Double(A.x +  (direction * i * coeff + 0.5), A.y + i * direction));

		}

		return line;
	}

	
	
	

	public static int rgbToGreyscale(int value) {

		return (value & 0xff) + ((value >> 8) & 0xff) + ((value >> 16) & 0xff);

	}
	
	public static double[] convoluteSignals(double a[], double b[]) {
	    
	    double outputTab[] = new double[a.length + b.length - 1];
	    
	    for(int i=0; i<a.length; i++) {
		for(int j=0; j<b.length; j++) {
		    outputTab[i+j] += a[i] * b[j];
		}
	    }
	    
	    return outputTab;
	}

	public static double[] makeFilter(int length) {

	    double filter[] = new double[11];
//	    double filter[] = { -0.3, 1, -0.3 };
		
		//make filter
		
		for(int x=-5; x<5; x++) {
		    
		    if(x%2 == 1) {
			filter[x+5] = (-4/(Math.PI*Math.PI))/(x*x);
		    }
		    else if (x%2 == 0) {
			filter[x+5] = 0;
		    }
		    
		}
	    filter[6] = 1;
	    
	    return filter;
	}
	
	public static double[] filterSignal(double tab[]) {

	    return convoluteSignals(tab, makeFilter(tab.length));
	    
	}
}

