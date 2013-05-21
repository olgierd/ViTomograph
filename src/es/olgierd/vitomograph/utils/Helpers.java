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
	    
	    double[] croppedTab = new double[a.length];
	    
	    for(int i=b.length/2; i<a.length+b.length/2; i++) 
		croppedTab[i-b.length/2] = outputTab[i]; 
	    
	    return croppedTab;
	}

	public static double[] makeFilter(int length, double coeff) {

	    double filter[] = new double[length];
	    
		for(int x=-length/2; x<length/2; x++) {
		    
		    if(x%2 != 0) {
			filter[x+length/2] = coeff * (-4/(Math.PI*Math.PI))/(x*x);
		    }
		    else if (x%2 == 0) {
			filter[x+length/2] = 0;
		    }
		    
		}
	    filter[length/2] = 1;
	    
	    return filter;
	}
	
	
	public static double getMinimal(double[] tab) {
	    
	    double minimal = Double.MAX_VALUE;
	    
	    for (int i = 0; i < tab.length; i++) {
		if(tab[i] < minimal) {
		    minimal = tab[i];
		}
	    }
	    
	    return minimal;
	}
	
	public static double getMaximal(double[] tab) {
	    
	    double maximal = Double.MIN_VALUE;
	    
	    for (int i = 0; i < tab.length; i++) {
		if(tab[i] > maximal) {
		    maximal = tab[i];
		}
	    }
	    
	    return maximal;
	}
	
	public static double[] filterSignal(double tab[], int filterLength, double filterGainCoefficient) {

	    return convoluteSignals(tab, makeFilter(filterLength, filterGainCoefficient));
	    
	}
}

