package es.olgierd.vitomograph.iface;

import java.awt.Graphics;

import javax.swing.JPanel;

import es.olgierd.vitomograph.device.Tomograph;

public class OutputPanel extends JPanel {

	Tomograph t;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(t.getOutputImage(), 0, 0, null);
	}

	public OutputPanel(final Tomograph t) {
		this.t = t;
	}

}
