package es.olgierd.vitomograph.iface;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.olgierd.vitomograph.device.Element;
import es.olgierd.vitomograph.device.Tomograph;

public class UserInterface extends JPanel {

	Tomograph t;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		
		g.drawImage(t.getImage(), t.getPicLocation().x, t.getPicLocation().y, null);
		
		ArrayList<Point> detectors = t.getDetectorsLocation();
		
		for(Point p : detectors) {
			g.fillOval(p.x-2, p.y-2, 4, 4);
		}

		g.setColor(Color.red);
		Point lamp = t.getLampLocation();
		
		g.fillOval(lamp.x-3, lamp.y-3, 6, 6);
		
		

		g.drawImage(t.getOutputRawImage(), 200, 400, null);
		g.drawImage(t.getOutputImage(), 900, 10, null);
		
	}
	
	public UserInterface(final Tomograph t) {

		this.t = t;
		
		setLayout(null);
		
		Button test = new Button("test");
		test.setBounds(500, 80, 100, 30);		
		add(test);
		
		test.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			t.makeOutputImage();
			repaint();
		    }
		});
		
		
		Button fullCapture = new Button("FULL");
		fullCapture.setBounds(590, 80, 100, 30);		
		add(test);
		
		fullCapture.addActionListener(new ActionListener() {
		    
		    @Override
		    public void actionPerformed(ActionEvent arg0) {
			t.doFullCapture();
			t.makeOutputImage();
			repaint();
		    }
		});
		
		add(fullCapture);
		
		
		final JSlider rotationSlider = new JSlider(0, 360);
		rotationSlider.setBounds(430, 10, 400, 40);
		rotationSlider.setValue(0);
		
		rotationSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				t.rotateToAngle(rotationSlider.getValue());
//				t.drawLine();
				t.getLine();
				repaint();
			}
		});
		
		
		add(rotationSlider);
		
		
		
//		repaint();
		
	}
	
}
