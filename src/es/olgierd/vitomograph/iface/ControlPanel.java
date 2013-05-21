package es.olgierd.vitomograph.iface;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.olgierd.vitomograph.device.Tomograph;

public class ControlPanel extends JPanel {
	Tomograph t;
	private JSlider contrastSub;
	private JSlider contrastMul;

	public ControlPanel(final Tomograph t) {
		this.t = t;

		setLayout(new GridLayout(7, 1));

		add(new JLabel("Sterowanie tomografem"));

		createButtons(t);

		createRotationSlider(t);

		add(new JLabel("Sterowanie obrazem wynikowym"));

		createContrastMulSlider(t);

		createConstrastSubSlider(t);

		createFixContrastButton(t, contrastSub, contrastMul);
	}

	private void createConstrastSubSlider(final Tomograph t) {
		contrastSub = new JSlider(-1000, 1000);

		contrastSub.setValue(0);

		contrastSub.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				t.fixOutputContrast(contrastSub.getValue(),
						contrastMul.getValue() / 150.0);
				ViTomograph.repaintWindows();
			}
		});

		add(contrastSub);
	}

	private void createContrastMulSlider(final Tomograph t) {
		contrastMul = new JSlider(1, 255);

		contrastMul.setValue(1);

		contrastMul.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				t.fixOutputContrast(contrastSub.getValue(),
						contrastMul.getValue() / 150.0);
				ViTomograph.repaintWindows();
			}
		});
		add(contrastMul);
	}

	private void createFixContrastButton(final Tomograph t,
			final JSlider contrastSub, final JSlider contrastMul) {
		JPanel tmpPanel;
		tmpPanel = new JPanel();
		tmpPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(tmpPanel);

		Button applyContrast = new Button("Fix contrast");

		applyContrast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				t.fixOutputContrast(contrastSub.getValue(),
						contrastMul.getValue() / 150.0);
				ViTomograph.repaintWindows();
			}
		});

		tmpPanel.add(applyContrast);
	}

	private void createRotationSlider(final Tomograph t) {
		final JSlider rotationSlider = new JSlider(0, 360);
		rotationSlider.setBounds(430, 10, 400, 40);
		rotationSlider.setValue(0);

		rotationSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				t.rotateToAngle(rotationSlider.getValue());
				// t.drawLine();
				t.getLine();
				t.makeOutputImage();
				ViTomograph.repaintWindows();
			}
		});

		add(rotationSlider);
	}

	private void createButtons(final Tomograph t) {
		JPanel tmpPanel = new JPanel();
		tmpPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(tmpPanel);

		Button test = new Button("test");

		test.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				t.makeOutputImage();
				ViTomograph.repaintWindows();
			}
		});
		tmpPanel.add(test);

		Button fullCapture = new Button("FULL");
		fullCapture.setBounds(590, 80, 100, 30);

		fullCapture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				t.doFullCapture();
				t.makeOutputImage();
				ViTomograph.repaintWindows();
			}
		});
		tmpPanel.add(fullCapture);
	}
}