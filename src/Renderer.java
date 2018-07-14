
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
	
	/**
	 * Creates the GUI of the application
	 * @param p_width width of the window
	 * @param p_height height of the window
	 */
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
	
	/**
	 * Clears the screen before anything is drawn every frame
	 */
	public void clear()
	{
		g2.fillRect(0, 0, width, height);
	}
	
	/**
	 * Draws a texture onto m_canvas
	 * @param p_texture the texture to be drawn
	 * @param p_frame the current frame to draw
	 * @param p_dest destination of the drawn image
	 */
	public void drawTexture(Texture p_texture, Rect p_frame, Rect p_dest)
	{
		//please correct me on this
		g2.drawImage(p_texture.getImage(), p_dest.x, p_dest.y, p_dest.x + p_dest.w, p_dest.y + p_dest.h,
				p_frame.x, p_frame.y, p_frame.x + p_frame.w, p_frame.y + p_frame.h, panel);
	}
}
