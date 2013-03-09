package es.olgierd.vitomograph.iface;

import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.olgierd.vitomograph.device.*;



public class ViTomograph  {

	static Tomograph t;
	static JFrame window;
	static JPanel gui;
	
	private static void initGui() {
		window = new JFrame();
		Container content = window.getContentPane();
		
		gui = new UserInterface(t);
		content.add(gui);
		
		window.setTitle("ViTomograph by Olgierd Pilarczyk & Krzysztof Surdyk");
		window.setSize(2000, 1300);
		window.setResizable(false);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String args[]) throws IOException {
		
		System.out.println("Welcome!");
		
		BufferedImage inputImg = null;
		
//		inputImg = ImageIO.read(new File("/home/oli/tomo.png"));
//		inputImg = ImageIO.read(new File("/home/oli/tomo2.png"));
//		inputImg = ImageIO.read(new File("/home/oli/dsc02249.png"));
		inputImg = ImageIO.read(new File("/home/oli/carbonare.jpg"));

		t = new Tomograph(inputImg, 1500, 270);

		initGui();

	}
	
	
}
