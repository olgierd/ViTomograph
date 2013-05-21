package es.olgierd.vitomograph.utils;

import java.awt.image.BufferedImage;

public class ImageProxy {

    private char[][] rawImageData;
    private BufferedImage internalImage;
    
    public void loadImage(BufferedImage img) {
	
	internalImage = img;
	rawImageData = new char[img.getWidth()][img.getHeight()];
	
	for(int i=0; i<img.getWidth(); i++) {
	    for(int j=0; j<img.getHeight(); j++) {
		rawImageData[i][j] = (char) Helpers.rgbToGreyscale(img.getRGB(i, j));
	    }
	}
	
	
    }

    public double getWidth() {
	return getInternalImage().getWidth();
    }

    public double getHeight() {
	return getInternalImage().getHeight();
    }

    public char getRGB(int i, int j) {
	return	rawImageData[i][j]; 
    }

    public BufferedImage getInternalImage() {
	return internalImage;
    }

    
    
    
    
    
    
    
}
