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
			t.makeLine();
			repaint();
		    }
		});
		
		
		final JSlider rotationSlider = new JSlider(0, 360);
		rotationSlider.setBounds(400, 10, 250, 40);
		
		rotationSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				t.rotateToAngle(rotationSlider.getValue());
				repaint();
			}
		});
		
		
		
		add(rotationSlider);
		
		
		
//		repaint();
		
	}
	
}
