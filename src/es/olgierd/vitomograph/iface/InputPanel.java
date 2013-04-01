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

public class InputPanel extends JPanel {

	Tomograph t;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.black);

		g.drawImage(t.getImage(), t.getPicLocation().x, t.getPicLocation().y,
				null);

		ArrayList<Point> detectors = t.getDetectorsLocation();

		for (Point p : detectors) {
			g.fillOval(p.x - 2, p.y - 2, 4, 4);
		}

		g.setColor(Color.red);
		Point lamp = t.getLampLocation();

		g.fillOval(lamp.x - 3, lamp.y - 3, 6, 6);
	}

	public InputPanel(final Tomograph t) {
		this.t = t;
	}

}
