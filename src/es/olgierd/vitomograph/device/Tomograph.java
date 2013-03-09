package es.olgierd.vitomograph.device;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import es.olgierd.vitomograph.utils.Helpers;

public class Tomograph {

	private Lamp lamp;
	private DetectorArray detectorarray;
	
	private BufferedImage img;
	private Point picLocation;

	private BufferedImage outputImageRaw;
	private BufferedImage outputImage;

	private double radius;
	private int rotationAngle;
	
	private double[][] resultsTab; 
	private boolean[] calculated;
	
	public Tomograph(BufferedImage img, int numberOfDetectors, double beamWidth) {
		
		this.img = img;
		calculateRadius();
		
		System.out.println(img.getHeight() + " " + img.getWidth());
		
		lamp = new Lamp(radius);
		detectorarray = new DetectorArray(numberOfDetectors, radius, beamWidth);
		
		outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		outputImageRaw = new BufferedImage(numberOfDetectors, 361, BufferedImage.TYPE_INT_RGB);
		
		resultsTab = new double[img.getWidth()][img.getHeight()];
		calculated = new boolean[361];
		
	}

	// sets up radius of whole device + calculates location of picture 
	private void calculateRadius() {
		
		double x = img.getWidth(), y = img.getHeight();
		
		radius = Math.sqrt(x*x + y*y) / 2;
		picLocation = new Point((int)(radius - x/2),  (int)(radius - y/2));
		
	}
	
	// returns list containing locations of all detectors
	public ArrayList<Point> getDetectorsLocation() {
		
		ArrayList<Point> locations = new ArrayList<Point>();
		
		for(Detector det : detectorarray.getDetectors()) {
			locations.add(det.getLocation());
		}
		
		return locations;
	}
	
	// returns location of lamp
	public Point getLampLocation() {
		return lamp.getLocation();
	}
	
	// rotates whole device to a given angle
	public void rotateToAngle(int angle) {
		
		detectorarray.rotateToAngle(angle);
		lamp.setRotationAngle(angle);
		
		rotationAngle = angle;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public Point getPicLocation() {
	    return picLocation;
	}
	
	public BufferedImage getOutputRawImage(){
	    return outputImageRaw;
	}
	
	public BufferedImage getOutputImage(){
	    return outputImage;
	}
	
	
	public int[] getCurrentReadout() {
	    
	    
	    int tab[] = new int[detectorarray.getDetectors().size()];
	    
	    
	    
	    return tab;
	    
	}

	public void getLine() {
	    
	    int tab[] = new int[detectorarray.getDetectors().size()];
	    
	    int i=0;
	    for(Detector d : detectorarray.getDetectors()) {
		calculateSingleDetector(d);
		tab[i] = (int)d.getValue();
		drawToResultTable(lamp.getLocation(), d.getLocation(), d.getValue());
		i++;
	    }

	    int max = -1;
	    
	    for(i=0; i<tab.length; i++)
		if(tab[i] > max) max = tab[i];
	    
	    if(max == 0) max = 1;
	    
	    for(i=0; i<tab.length; i++) {
		tab[i] = (tab[i]*255)/max;
	    }
	    
	    for(i=0; i<tab.length; i++){
		outputImageRaw.setRGB(i, rotationAngle, tab[i] + (tab[i]<<8) + (tab[i]<<16));
		
	    }
	    
	    calculated[rotationAngle] = true;

	}
	
	public void calculateSingleDetector(Detector d) {
	    
	    Point from, to;
	    
	    from = lamp.getLocation();
	    to = d.getLocation();
	    
	    ArrayList<Point> points = Helpers.getLine(from, to);
	    
	    int i, j;
	    
	    d.reset();
	    
	    for(Point p : points) {
		i = p.x - picLocation.x;
		j = p.y - picLocation.y;
		
		if( i > 0 && j > 0 && i < img.getWidth() && j < img.getHeight()) {
		    d.addValue(Helpers.rgbToGreyscale(img.getRGB(i, j)));
		}
		
	    }
	    
	    
//	    drawLine(outputImage, lamp.getLocation(), d.getLocation(), (int)d.getValue());
	    
	}
	
	public void drawLine(BufferedImage pic, Point from, Point to, int color) {
	    
	    ArrayList<Point> points = Helpers.getLine(from, to);
	    
	    int i, j;
	    
	    for(Point p : points) {
		i = p.x - picLocation.x;
		j = p.y - picLocation.y;
		
		if( i > 0 && j > 0 && i < pic.getWidth() && j < pic.getHeight()) {
		    resultsTab[i][j] += color;
		    pic.setRGB(i, j, color);
		}
		
	    }
	    
	}

	public void drawToResultTable(Point from, Point to, double color) {

	    if(calculated[rotationAngle]) return;
	    
	    ArrayList<Point> points = Helpers.getLine(from, to);
	    
	    int i, j;
	    
	    for(Point p : points) {
		i = p.x - picLocation.x;
		j = p.y - picLocation.y;
		
		if( i > 0 && j > 0 && i < outputImage.getWidth() && j < outputImage.getHeight()) {
		    resultsTab[i][j] += color;
		}
		
	    }
	    
	    
	}
	
	
	public void makeOutputImage() {
	    
	    double max = -1;
	    
	    for(int i=0; i<img.getWidth(); i++) {
		for(int j=0; j<img.getHeight(); j++) {
		    if(resultsTab[i][j] > max) max = resultsTab[i][j]; 
		}
	    }
	    
	    int val;
	    
	    for(int i=0; i<img.getWidth(); i++) {
		for(int j=0; j<img.getHeight(); j++) {
		    val = (int)((resultsTab[i][j]*255)/max);
		    outputImage.setRGB(i, j, val + (val<<8) + (val<<16));
		}
	    }
	}
	
	public void doFullCapture() {

	    long start, loopstart, loopend=0;
	    
	    start = System.currentTimeMillis();
	    
	    for(int i=0; i<361; i++){
		loopstart = System.currentTimeMillis();
		
		rotateToAngle(i);
		getLine();
		
		loopend = System.currentTimeMillis();
		System.out.println((int)(100*i/360)+ "% # Processing time (" + i + "/360): " + (loopend - loopstart));
	    }
	    System.out.println("Processing took: " + (loopend - start));
	}
	
}
