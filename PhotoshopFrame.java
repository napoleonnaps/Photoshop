/*
 * Noah Alonso-Torres
 * Photoshop
 * Frame Class
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PhotoshopFrame implements ActionListener{
	private static int BUTTONPANELHEIGHT = 80;

	private PhotoshopDrawer drawer;
	private String filename = "swing.jpeg";  // Image File Here.
	private Color[][] array = ImageReader.getImage(filename);
	private int width = array[0].length;
	private int height = array.length;

	private JButton resetButton = new JButton("Reset");
	private JButton sepiaButton = new JButton("Sepia");
	private JButton blurButton = new JButton("Blur");
	private JButton flipVertButton = new JButton("Flip Vert");
	private JButton flipHorizButton = new JButton("Flip Horiz");
	private JButton greenTintButton = new JButton("Green Tint");
	private JButton saturateButton = new JButton("Saturate");
	private JButton greyButton = new JButton("Grey");

	public PhotoshopFrame(){
		JFrame frame = new JFrame("Photoshop v0.001");
		frame.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel(new GridLayout(2,4));
		buttonPanel.add(resetButton);
		buttonPanel.add(sepiaButton);
		buttonPanel.add(blurButton);
		buttonPanel.add(flipVertButton);
		buttonPanel.add(flipHorizButton);
		buttonPanel.add(greenTintButton);
		buttonPanel.add(saturateButton);
		buttonPanel.add(greyButton);

		resetButton.addActionListener(this);
		sepiaButton.addActionListener(this);
		blurButton.addActionListener(this);
		flipVertButton.addActionListener(this);
		flipHorizButton.addActionListener(this);
		greenTintButton.addActionListener(this);
		saturateButton.addActionListener(this);
		greyButton.addActionListener(this);

		drawer = new PhotoshopDrawer(height, width, array);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(drawer, BorderLayout.CENTER);

		frame.add(buttonPanel, BorderLayout.NORTH);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height+BUTTONPANELHEIGHT);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == resetButton) reset();
		if (event.getSource() == sepiaButton) sepia();
		if (event.getSource() == blurButton) blur();
		if (event.getSource() == flipVertButton) flipVert();
		if (event.getSource() == flipHorizButton) flipHoriz();
		if (event.getSource() == greenTintButton) greenTint();
		if (event.getSource() == saturateButton) saturate();
		if (event.getSource() == greyButton) grey();
	}

	public void reset() {
		array = ImageReader.getImage(filename);
		drawer.swapArray(array);
		drawer.repaint();
	}

	public void sepia() {
		for(int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				//each element is a variable of type color
				int red = array[row][col].getRed();
				int green = array[row][col].getGreen();
				int blue = array[row][col].getBlue();
				
				int redPrime = (int)(.393*red + .769*green + .189*blue);
				int greenPrime = (int)(.349*red + .686*green + .168*blue);
				int bluePrime = (int)(.272*red + .534*green + .131*blue);
				if (redPrime > 255) redPrime = 255;
				if (greenPrime > 255) greenPrime = 255;
				if (bluePrime > 255) bluePrime = 255;

				array[row][col] = new Color(redPrime,greenPrime,bluePrime); //RGB
			}
		}
		drawer.repaint();
	}

	public void blur() {
		for(int row = 1; row < height-1; ++row) {
			for (int col = 1; col < width-1; ++col) {
				//IN A GIVEN CELL
				int valRow = -1, valCol = -1;
				int j = 1 , sumRed = 0, sumGreen = 0, sumBlue = 0;
				//DO THIS 3 TIMES
				for (int i = 0; i < 3; i++) {
					if (i < 3) valCol = -1;
					else if (i >= 3 && i < 6) valCol = 0;
					else if (i >= 6) valCol = 1;
					//DO THIS 9 TIMES
					for (int k = 0; k < 3; k++) {
						if (j == 1) valRow = -1;
						else if (j == 2) valRow = 0;
						else if (j == 3) { valRow = 1; j = 0; }
						j++;
						
						int red = array[row - valRow][col-valCol].getRed();
						int green = array[row - valRow][col-valCol].getGreen();
						int blue = array[row - valRow][col-valCol].getBlue();
						
						sumRed += red;
						sumGreen += green;
						sumBlue += blue;
					}
					
				}
	
				int avgRed = sumRed/9;
				int avgGreen = sumGreen/9;
				int avgBlue = sumBlue/9;
				
				array[row][col] = new Color(avgRed,avgGreen,avgBlue); //RGB
			}
		}
		drawer.repaint();
	}

	public void flipVert() {
		Color[][] tempArray = new Color[height][width];

		for (int row = 0; row < height; row++) {
			tempArray[row] = array[height- row -1];
		}
		array = tempArray;
		drawer.swapArray(array);
		drawer.repaint();
	}

	public void flipHoriz() {
		Color[][] tempArray = new Color[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				tempArray[row][col] = array[row][width-col-1];
			}
		}
		array = tempArray;
		drawer.swapArray(array);
		drawer.repaint();
	}

	public void greenTint() {
		for(int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				//each element is a variable of type color
				int red = array[row][col].getRed();
				int green = array[row][col].getGreen();
				int blue = array[row][col].getBlue();
				
				int greenPrime = (int)(Math.pow(green, .5) + green);
				if (greenPrime > 255) greenPrime = 255;

				array[row][col] = new Color(red, greenPrime, blue); //RGB
			}
		}
		drawer.repaint();
	}
	
	public void saturate() {
		for(int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				//each element is a variable of type color
				int red = array[row][col].getRed();
				int green = array[row][col].getGreen();
				int blue = array[row][col].getBlue();
				
				if (red > (255/2)) red = 255;
				else if (red < (255/2)) red = 0;
				
				if (green > (255/2)) green = 255;
				else if (green < (255/2)) green = 0;
				
				if (blue > (255/2)) blue = 255;
				else if (blue < (255/2)) blue = 0;

				array[row][col] = new Color(red, green, blue); //RGB
			}
		}
		drawer.repaint();
	}

	public void grey() {
		// want to go in, get RGB and then convert to some shade of grey avg value of RGB
		// and we are going to use that to get the intensity(brightness) of each pixel

		for(int row = 0; row < height; ++row) {
			for (int col = 0; col < width; ++col) {
				// each element is a variable of type color
				int red = array[row][col].getRed();
				int green = array[row][col].getGreen();
				int blue = array[row][col].getBlue();

				int avg = (red+green+blue)/3;

				array[row][col] = new Color(avg,avg,avg); 
				// gets rid of old color and replaces it with greyscale
			}
		}
		drawer.repaint();
	}

	public static void main(String[] args) {
		PhotoshopFrame photoGo = new PhotoshopFrame();
	}
}
