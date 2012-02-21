/*
 * Noah Alonso-Torres
 * Photoshop
 * Drawer Class
 */

import java.awt.*;
import javax.swing.*;

public class PhotoshopDrawer extends JComponent {
	private Color[][] array;
	private int height = 0;
	private int width = 0;
	
	public PhotoshopDrawer(int pHeight, int pWidth, Color[][] pArray) {
		height = pHeight;
		width = pWidth;
		array = pArray;
		setPreferredSize(new Dimension(width, height));
	}
	
	public void swapArray(Color[][] pArray) {
		array = pArray;
	}
	
	public void paintComponent(Graphics graphics) {
		graphics.setColor(Color.YELLOW);
		graphics.fillRect(0,0, width, height);
		
		for (int row = 0; row < array.length; ++row){
			for (int col = 0; col < array[0].length; ++col){
				graphics.setColor(array[row][col]);
				graphics.drawLine(col, row, col, row);
			}
		}
	}
}
