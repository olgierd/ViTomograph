package es.olgierd.vitomograph.iface;

import java.awt.Graphics;

import javax.swing.JPanel;

import es.olgierd.vitomograph.device.Tomograph;

public class RawPanel extends JPanel {

	Tomograph t;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(t.getCurrentImage(), 0, 0, null);
		g.drawImage(t.getOutputRawImage(), 0, 270, null);
	}

	public RawPanel(final Tomograph t) {
		this.t = t;
	}
}
