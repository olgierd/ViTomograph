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

}
