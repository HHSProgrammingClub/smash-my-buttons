
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Renderer 
{
	public BufferedImage canvas;
	public Graphics2D g2;
	
	public JFrame app;
	public JPanel panel;
	
	private int width, height;
	
	public void init(int p_width, int p_height)
	{
		//saving these for safe keeping
		width = p_width;
		height = p_height;
		
		//create the window of the application
		app = new JFrame("AI Fighters"); //or whatever dank title we want
		app.setSize(p_width, p_height);
		app.setResizable(false); //dunno if y'all want this or not
		app.setVisible(true);
		app.setLocationRelativeTo(null);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create the canvas to draw stuff on
		canvas = new BufferedImage(p_width, p_height, BufferedImage.TYPE_INT_ARGB);
		g2 = canvas.createGraphics();
		
		//create the JPanel that holds the canvas
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(canvas, 0, 0, this);
				repaint();
			}
		};
		
		//the most important step
		app.add(panel);
	}
	
	//called every frame before drawing anything
	public void clear()
	{
		g2.fillRect(0, 0, width, height);
	}
	
	//draws things onto the canvas
	public void drawTexture(Texture p_frame, Rect p_destination)
	{
		//shouldn't this take in a BufferedImage of the current animation frame?
		//drawTexture(animation.getFrame()...);
		g2.drawImage(p_frame.getImage(), (int) p_destination.x, (int) p_destination.y, panel);
	}
}
