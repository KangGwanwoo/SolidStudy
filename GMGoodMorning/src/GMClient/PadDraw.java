package GMClient;

import java.awt.Color;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JComponent;
import javax.xml.bind.ParseConversionEvent;

public class PadDraw extends JComponent{
	/**
		 * 
		 */
	private static final long serialVersionUID = 1234L;

	public Image image;

	public Graphics2D graphics2D;

	// this is what we'll be using to draw on
	public int currentX, currentY, oldX, oldY;
	
	public int getOldX,getOldY,getCurrentX,getCurrentY;
	
	private String valRight="20";
	// Now for the constructors
	public PadDraw() {
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(valRight.equals("10")){
				oldX = e.getX();
				oldY = e.getY();
				}
				
			}
		});
		// if the mouse is pressed it sets the oldX & oldY
		// coordinates as the mouses x & y coordinates
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if(valRight.equals("10")){
				currentX = e.getX();
				currentY = e.getY();
				if (graphics2D != null)
					graphics2D.drawLine(oldX, oldY, currentX, currentY);
				repaint();

				oldX = currentX;
				oldY = currentY;
				}
				

			}

		});
		// while the mouse is dragged it sets currentX & currentY as the mouses
		// x and y
		// then it draws a line at the coordinates
		// it repaints it and sets oldX and oldY as currentX and currentY
	}

	 
	public void OtherUserMousePressed(int X, int Y) {
		getOldX = X;
		getOldY = Y;
	}

	public void paintOtherUser(int X, int Y) {
		getCurrentX=X;
		getCurrentY =Y;
		if (graphics2D != null)
			graphics2D.drawLine(getOldX, getOldY, getCurrentX, getCurrentY);
		repaint();
		getOldX = getCurrentX;
		getOldY = getCurrentY;
	}
 
	
	public void paintComponent(Graphics g) {
		if (image == null) {
			image = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D) image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			clear();

		}
		g.drawImage(image, 0, 0, null);
	}

	// this is the painting bit
	// if it has nothing on it then
	// it creates an image the size of the window
	// sets the value of Graphics as the image
	// sets the rendering
	// runs the clear() method
	// then it draws the image

	public void clear() {
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();

	}

	// this is the clear
	// it sets the colors as white
	// then it fills the window with white
	// thin it sets the color back to black
	public void red() {
		graphics2D.setPaint(Color.red);
		repaint();
	}

	// this is the red paint
	public void black() {
		graphics2D.setPaint(Color.black);
		repaint();
	}

	// black paint
	public void magenta() {
		graphics2D.setPaint(Color.magenta);
		repaint();
	}

	// magenta paint
	public void blue() {
		graphics2D.setPaint(Color.blue);
		repaint();
	}

	// blue paint
	public void green() {
		graphics2D.setPaint(Color.green);
		repaint();

	}
	
	public void setGraphics(Graphics2D graphic){
		this.graphics2D = graphic;
		repaint();
	}
	
	public Graphics2D getGraphics(){
		return this.graphics2D;
	}

	public void setRight(String valGiveRight) {
		// TODO Auto-generated method stub
		this.valRight=valGiveRight;
	}
}
	// green paint


// good job, you've made your paint program =]

