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
    static int numberOfDetectors;

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
	inputImageWindow.setResizable(false);
	inputImageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	inputImageWindow.setLocation(screenSize.width - inputImageWindow.getSize().width, 0);
	inputImageWindow.setSize(d.width + ins.left + ins.right, d.height + ins.top + ins.bottom);

	outputImagePanel = new OutputPanel(t);
	outputImageWindow.add(outputImagePanel);

	outputImageWindow.setTitle("Output image");
	outputImageWindow.setVisible(true);
	ins = outputImageWindow.getInsets();
	outputImageWindow.setResizable(false);
	outputImageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	outputImageWindow.setLocation(screenSize.width / 2 - outputImageWindow.getSize().width / 2, 0);
	outputImageWindow.setSize(imgWidth + ins.left + ins.right, imgHeight + ins.top + ins.bottom);

	rawImagePanel = new RawPanel(t);
	rawImageWindow.add(rawImagePanel);

	rawImageWindow.setTitle("Raw");
	rawImageWindow.setVisible(true);
	ins = rawImageWindow.getInsets();
	rawImageWindow.setSize(numberOfDetectors + ins.left + ins.right, 361 + 260 + 10 + ins.top + ins.bottom);
	rawImageWindow.setResizable(false);
	rawImageWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	rawImageWindow.setLocation(0, 0);

	controlImagePanel = new ControlPanel(t);
	controlImageWindow.add(controlImagePanel);

	controlImageWindow.setTitle("ViTomograph by Olgierd Pilarczyk & Krzysztof Surdyk");
	controlImageWindow.setVisible(true);
	ins = controlImageWindow.getInsets();
	controlImageWindow.setResizable(false);
	controlImageWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	controlImageWindow.setLocation(screenSize.width / 2 - controlImageWindow.getSize().width / 2, outputImageWindow.getSize().width + 100);
	controlImageWindow.setSize(500, 300);
    }

    static void repaintWindows() {
	inputImageWindow.repaint();
	outputImageWindow.repaint();
	rawImagePanel.repaint();
    }

    public static void main(String args[]) throws IOException {

	BufferedImage inputImg = null;
	numberOfDetectors = 300;

	/*
	 * JFileChooser imageChooser = new JFileChooser();
	 * FileNameExtensionFilter filter = new FileNameExtensionFilter(
	 * "JPEG file", "jpg", "jpeg", ".png");
	 * imageChooser.addChoosableFileFilter(filter);
	 * 
	 * int returnValue = JFileChooser.ERROR_OPTION; File f;
	 * 
	 * while (returnValue != JFileChooser.APPROVE_OPTION) { returnValue =
	 * imageChooser.showOpenDialog(null); } f =
	 * imageChooser.getSelectedFile();
	 * 
	 * inputImg = ImageIO.read(f);
	 */

	 inputImg = ImageIO.read(new File("/home/oli/tomo.png"));
//	 inputImg = ImageIO.read(new File("/home/oli/tomo2.png"));
//	 inputImg = ImageIO.read(new File("/home/oli/dsc02249.png"));
//	 inputImg = ImageIO.read(new File("/home/oli/carbonare.jpg"));
	// inputImg = ImageIO.read(new
	// File("C:\\Users\\op798739\\Desktop\\MISC\\op798739.jpg"));
//	 inputImg = ImageIO.read(new File("/home/oli/tomo3.png"));
	// inputImg = ImageIO.read(new File("/home/oli/phantom.gif"));
//	inputImg = ImageIO.read(new File("/home/lincoln2491/tm.jpg"));

	t = new Tomograph(inputImg, 250, 160, 150);

	imgWidth = inputImg.getWidth();
	imgHeight = inputImg.getHeight();

	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		initGui();
	    }
	});

    }

}
