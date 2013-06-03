package es.olgierd.vitomograph.iface;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.olgierd.vitomograph.device.*;

public class ViTomograph {

	static Tomograph t;

	static JFrame inputImageWindow;
	static JFrame outputImageWindow;
	static JFrame rawImageWindow;
	static JFrame controlImageWindow;
	static JPanel inputImagePanel;
	static JPanel outputImagePanel;
	static JPanel rawImagePanel;
	static JPanel controlImagePanel;

	static int imgWidth;
	static int imgHeight;
	static int numberOfDetectors = 800;
	static int angle = 160;
	static boolean dsp = true;

	private static void initGui() {
		inputImageWindow = new JFrame();
		outputImageWindow = new JFrame();
		rawImageWindow = new JFrame();
		controlImageWindow = new JFrame();

		Insets ins;
		Dimension d;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();

		inputImagePanel = new InputPanel(t);
		inputImageWindow.add(inputImagePanel);

		inputImageWindow.setTitle("Input image");
		inputImageWindow.setVisible(true);
		ins = inputImageWindow.getInsets();
		d = inputImagePanel.getSize();
		inputImageWindow.setResizable(true);
		inputImageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputImageWindow.setSize(d.width + ins.left + ins.right, d.height
				+ ins.top + ins.bottom);
		inputImageWindow.setLocation(
				screenSize.width - inputImageWindow.getSize().width, 0);

		outputImagePanel = new OutputPanel(t);
		outputImageWindow.add(outputImagePanel);

		outputImageWindow.setTitle("Output image");
		outputImageWindow.setVisible(true);
		ins = outputImageWindow.getInsets();
		outputImageWindow.setResizable(true);
		outputImageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		outputImageWindow.setSize(imgWidth + ins.left + ins.right, imgHeight
				+ ins.top + ins.bottom);
		outputImageWindow
				.setLocation(screenSize.width / 2
						- outputImageWindow.getSize().width / 2, 0);

		rawImagePanel = new RawPanel(t);
		rawImageWindow.add(rawImagePanel);

		rawImageWindow.setTitle("Raw");
		rawImageWindow.setVisible(true);
		ins = rawImageWindow.getInsets();
		rawImageWindow.setSize(numberOfDetectors, 361 + 260 + 10);
		rawImageWindow.setResizable(true);
		rawImageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		rawImageWindow.setLocation(0, 0);

		controlImagePanel = new ControlPanel(t);
		controlImageWindow.add(controlImagePanel);

		controlImageWindow
				.setTitle("ViTomograph by Olgierd Pilarczyk & Krzysztof Surdyk");
		controlImageWindow.setVisible(true);
		ins = controlImageWindow.getInsets();
		controlImageWindow.setResizable(true);
		controlImageWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlImageWindow.setSize(500, 300);
		controlImageWindow.setLocation(screenSize.width / 2
				- controlImageWindow.getSize().width / 2,
				outputImageWindow.getSize().height + 50);
	}

	static void repaintWindows() {
		inputImageWindow.repaint();
		outputImageWindow.repaint();
		rawImagePanel.repaint();
	}

	static void wrongArgument(String msg) {
		System.out.println("Poda³eœ z³e argumenty: " + msg);
		System.exit(0);
	}

	public static void main(String args[]) throws IOException {

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-d")) {
				try {
					numberOfDetectors = Integer.parseInt(args[i + 1]);
				} catch (Exception e) {
					wrongArgument("liczba detektorów musi byæ liczb¹.");
				}
			} else if (args[i].equals("-a")) {
				try {
					angle = Integer.parseInt(args[i + 1]);
				} catch (Exception e) {
					wrongArgument("k¹t musi byæ liczb¹.");
				}
			} else if (args[i].equals("-n")) {
				dsp = false;
			}
		}

		BufferedImage inputImg = null;

		String userPath = System.getProperty("user.home");
		JFileChooser imageChooser = new JFileChooser(userPath);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Image file", "jpg", "jpeg", "png", "gif", "bmp");
		imageChooser.setFileFilter(filter);

		int returnValue = JFileChooser.ERROR_OPTION;
		File f;

		while (true) {
			returnValue = imageChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				break;
			}
			if (returnValue == JFileChooser.CANCEL_OPTION) {
				System.exit(0);
			}
		}

		f = imageChooser.getSelectedFile();

		inputImg = ImageIO.read(f);

		// inputImg = ImageIO.read(new File("/home/oli/tomo.png"));
		// inputImg = ImageIO.read(new File("/home/oli/tomo2.png"));
		// inputImg = ImageIO.read(new File("/home/oli/dsc02249.png"));
		// inputImg = ImageIO.read(new File("/home/oli/carbonare.jpg"));
		// inputImg = ImageIO.read(new
		// File("C:\\Users\\op798739\\Desktop\\MISC\\op798739.jpg"));
		// inputImg = ImageIO.read(new File("/home/oli/tomo3.png"));
		// inputImg = ImageIO.read(new File("/home/oli/phantom.gif"));
		// inputImg = ImageIO.read(new File("/home/lincoln2491/tm.jpg"));

		imgWidth = inputImg.getWidth();
		imgHeight = inputImg.getHeight();

		t = new Tomograph(inputImg, numberOfDetectors, angle, 150, dsp);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				initGui();
			}
		});

	}

}
