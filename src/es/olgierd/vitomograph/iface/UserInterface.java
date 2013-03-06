package es.olgierd.vitomograph.iface;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.olgierd.vitomograph.device.Element;
import es.olgierd.vitomograph.device.Tomograph;

public class UserInterface extends JPanel {

	Tomograph t;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		
		int imgloc = (int) (Math.sqrt(2 * t.getRadius()*t.getRadius()) - t.getRadius());
		
		g.drawImage(t.getImage(), imgloc, imgloc, null);
		
		ArrayList<Point> detectors = t.getDetectorsLocation();
		
		for(Point p : detectors) {
			g.fillOval(p.x-2, p.y-2, 4, 4);
		}

		g.setColor(Color.red);
		Point lamp = t.getLampLocation();
		
		
		g.fillOval(lamp.x-2, lamp.y-2, 4, 4);
	}
	
	public UserInterface(Tomograph t) {

		this.t = t;
		
		setLayout(null);
		
		Button test = new Button("test");
		test.setBounds(10, 10, 100, 30);		
//		add(test);
		
//		repaint();
		
	}
	
}
