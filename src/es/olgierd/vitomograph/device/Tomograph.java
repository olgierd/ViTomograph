package es.olgierd.vitomograph.device;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import es.olgierd.vitomograph.utils.Helpers;
import es.olgierd.vitomograph.utils.ImageProxy;

public class Tomograph {

    private Lamp lamp;
    private DetectorArray detectorarray;

    private ImageProxy img;
    private Point picLocation;

    private BufferedImage outputImageRaw;
    private BufferedImage outputImage;
    private BufferedImage currentReadoutImage;
    private BufferedImage clearImage;

    private double radius;
    private int rotationAngle;

    private double[][] resultsTab;
    private boolean[] calculated;

    // addidionals

    public Tomograph(BufferedImage img, int numberOfDetectors, double beamWidth, int distance) {

//	this.img = img;
	
	this.img = new ImageProxy();
	
	this.img.loadImage(img);
	
	calculateRadius(distance);

	lamp = new Lamp(radius);
	detectorarray = new DetectorArray(numberOfDetectors, radius, beamWidth);

	outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
	outputImageRaw = new BufferedImage(numberOfDetectors, 361, BufferedImage.TYPE_INT_RGB);

	currentReadoutImage = new BufferedImage(numberOfDetectors, 260, BufferedImage.TYPE_INT_RGB);
	clearImage = new BufferedImage(numberOfDetectors, 260, BufferedImage.TYPE_INT_RGB);

	resultsTab = new double[img.getWidth() + 2][img.getHeight() + 2];
	calculated = new boolean[361];

    }

    // sets up radius of whole device + calculates location of picture
    private void calculateRadius(double distance) {

	double x = img.getWidth(), y = img.getHeight();

	radius = Math.sqrt(x * x + y * y) / 2;
	radius += distance;
	picLocation = new Point((int) (radius - x / 2), (int) (radius - y / 2));

    }

    // returns list containing locations of all detectors
    public ArrayList<Point> getDetectorsLocation() {

	ArrayList<Point> locations = new ArrayList<Point>();

	for (Detector det : detectorarray.getDetectors()) {
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
	return img.getInternalImage();
    }

    public double getRadius() {
	return radius;
    }

    public Point getPicLocation() {
	return picLocation;
    }

    public BufferedImage getOutputRawImage() {
	return outputImageRaw;
    }
    
    public BufferedImage getOutputImage() {
	return outputImage;
    }

    public BufferedImage getCurrentImage() {
	return currentReadoutImage;
    }

    public int[] getCurrentReadout() {

	int tab[] = new int[detectorarray.getDetectors().size()];

	return tab;

    }

    public void getLine() {

	// integer table, used to create preview
	int tab[] = new int[detectorarray.getDetectors().size()];
	
	// double table, used to print to resulting table
	double vals[] = new double[detectorarray.getDetectors().size()];

	int i = 0;
	for (Detector d : detectorarray.getDetectors()) {
	    calculateSingleDetector(d);
	    vals[i] = (int) d.getValue();
	    i++;
	}

	vals = Helpers.filterSignal(vals, 21, 1);
	
	double min = Helpers.getMinimal(vals);
	
	if (min < 0) {
	    for (i = 0; i < vals.length; i++)
		vals[i] -= min;
	}
	
	double max = Helpers.getMaximal(vals);
	
	for (int j = 0; j < vals.length; j++) {
	    tab[j] = (int)((255*vals[j]) / (max));
	    if(tab[j] > 255) tab[j] = 255;
	}

	i=0;
	
	for (Detector d : detectorarray.getDetectors()) {
	    drawToResultTable(lamp.getLocation(), d.getLocation(), vals[i]);
	    i++;
	}

	for (i = 0; i < tab.length; i++) {
	    outputImageRaw.setRGB(i, rotationAngle, tab[i] + (tab[i] << 8) + (tab[i] << 16));
	}
	
	currentReadoutImage.setData(clearImage.getData());
	
	for (i = 0; i < tab.length; i++) {
	    currentReadoutImage.setRGB(i, 255-tab[i], 0xffffff);
	}

	calculated[rotationAngle] = true;

    }

    public void calculateSingleDetector(Detector d) {

	Point from, to;

	from = lamp.getLocation();
	to = d.getLocation();

	ArrayList<Point2D.Double> points = Helpers.getSoftLine(from, to);

	double i, j;

	d.reset();

	double value;

	for (Point2D.Double p : points) {
	    i = p.x - picLocation.x;
	    j = p.y - picLocation.y;

	    value = 0;

	    if (i > 0 && j > 0 && i < img.getWidth() && j < img.getHeight()) {

		if (i % 1 == 0 && (int) i < img.getWidth() - 1 && (int) j < img.getHeight() - 1) {
		    value = (img.getRGB((int) i, (int) j)) * (1 - (j % 1));
		    value += (img.getRGB((int) i, (int) j + 1)) * (j % 1);
		}

		if (j % 1 == 0 && (int) i < img.getWidth() - 1 && (int) j < img.getHeight() - 1) {
		    value = (img.getRGB((int) i, (int) j)) * (1 - (i % 1));
		    value += (img.getRGB((int) i + 1, (int) j)) * (i % 1);
		}
	    }

	    if ((int) i < img.getWidth() && (int) j < img.getHeight()) {
		d.addValue(value);
	    }

	}

	// drawLine(outputImage, lamp.getLocation(), d.getLocation(),
	// (int)d.getValue());

    }

    public void drawLine(BufferedImage pic, Point from, Point to, int color) {

	ArrayList<Point> points = Helpers.getLine(from, to);

	int i, j;

	for (Point p : points) {
	    i = p.x - picLocation.x;
	    j = p.y - picLocation.y;

	    if (i > 0 && j > 0 && i < pic.getWidth() && j < pic.getHeight()) {
		resultsTab[i][j] += color;
		pic.setRGB(i, j, color);
	    }

	}

    }

    // integer-based
    // public void drawToResultTable(Point from, Point to, double color) {
    //
    // if (calculated[rotationAngle])
    // return;
    //
    // ArrayList<Point> points = Helpers.getLine(from, to);
    //
    // int i, j;
    //
    // for (Point p : points) {
    // i = p.x - picLocation.x;
    // j = p.y - picLocation.y;
    //
    // if (i > 0 && j > 0 && i < outputImage.getWidth() && j <
    // outputImage.getHeight()) {
    // resultsTab[i][j] += color;
    // }
    //
    // }
    //
    // }

    // double-based
    public void drawToResultTable(Point from, Point to, double color) {

	if (calculated[rotationAngle])
	    return;

	// ArrayList<Point> points = Helpers.getLine(from, to);

	ArrayList<Point2D.Double> points = Helpers.getSoftLine(from, to);

	double i, j;

	for (Point2D.Double p : points) {
	    i = p.x - picLocation.x;
	    j = p.y - picLocation.y;

	    if (i > 0 && j > 0 && i < outputImage.getWidth() && j < outputImage.getHeight()) {

		if (i % 1 == 0) {
		    resultsTab[(int) i][(int) j] += color * (1 - (j % 1));
		    resultsTab[(int) i][(int) j + 1] += color * (j % 1);
		}

		if (j % 1 == 0) {
		    resultsTab[(int) i][(int) j] += color * (1 - (i % 1));
		    resultsTab[(int) i + 1][(int) j] += color * (i % 1);
		}

	    }

	}

    }

    public void makeOutputImage() {

	double max = Double.MIN_VALUE;
	double min = Double.MAX_VALUE;

	for (int i = 0; i < img.getWidth(); i++) {
	    for (int j = 0; j < img.getHeight(); j++) {
		if (resultsTab[i][j] > max)
		    max = resultsTab[i][j];
		if(resultsTab[i][j] < min)
		    min = resultsTab[i][j];
	    }
	}

	max = max - min;
	
	int val;

	for (int i = 0; i < img.getWidth(); i++) {
	    for (int j = 0; j < img.getHeight(); j++) {
		
		val = (int) (((resultsTab[i][j] - min) * 255) / max);
		
		val = val + (val << 8) + (val << 16);
		outputImage.setRGB(i, j, val);

	    }
	}
    }

    public void doFullCapture() {

	long start, loopstart, loopend = 0;

	start = System.currentTimeMillis();

	for (int i = 0; i < 361; i += 1) {
	    loopstart = System.currentTimeMillis();

	    rotateToAngle(i);
	    getLine();

	    loopend = System.currentTimeMillis();
	    System.out.println((int) (100 * i / 360) + "% # (" + i + "/360) # Processing time: " + (loopend - loopstart) + "ms #  ETA: " + (loopend - loopstart) * (360 - i) / 1000.0 + " seconds");
	}

	System.out.println("Processing took: " + (loopend - start));
    }

    public void fixOutputContrast(int sub, double mul) {

	makeOutputImage();

	int val;

	for (int i = 0; i < img.getWidth(); i++) {
	    for (int j = 0; j < img.getHeight(); j++) {
		val = Helpers.rgbToGreyscale(outputImage.getRGB(i, j));

		val -= sub;
		val = (int) (val * mul);

		if (val > 255)
		    val = 255;
		if (val < 0)
		    val = 0;

		val = val + (val << 8) + (val << 16);

		outputImage.setRGB(i, j, val);

	    }
	}

    }

}
