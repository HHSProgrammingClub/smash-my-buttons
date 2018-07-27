package graphics;


import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.geom.Ellipse2D;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Renders all the sprites, GUI, and additional shapes to the screen
 * @author Catherine Guevara
 */
public class Renderer implements Page
{
	public BufferedImage m_frontBuffer, m_backBuffer;
	public Graphics2D m_graphics;
	public JPanel m_panel;
	
	private int m_width, m_height;
	
	private float m_scale;
	private final float DEFAULT_SCALE = 1.25f; 
	
	/**
	 * Creates renderer for displaying images and shapes
	 */
	public Renderer()
	{
		//get the dimensions of the screen
		m_width = Toolkit.getDefaultToolkit().getScreenSize().width;
		m_height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		//calculate the scale according to the screen size
		m_scale = (m_width * DEFAULT_SCALE) / 800;
		
		//create the buffers to draw stuff on
		m_frontBuffer = new BufferedImage(m_width, m_height, BufferedImage.TYPE_INT_ARGB);
		m_backBuffer = new BufferedImage(m_width, m_height, BufferedImage.TYPE_INT_ARGB);
		m_graphics = m_backBuffer.createGraphics();
		
		//create the JPanel that holds the canvas
		m_panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(m_frontBuffer, 0, 0, this);
				repaint();
			}
		};
	}
	
	/**
	 * Ensures the current frame is being displayed
	 */
	public void display()
	{
		BufferedImage temp = m_backBuffer;
		m_backBuffer = m_frontBuffer;
		m_frontBuffer = temp;
		
		m_graphics = m_backBuffer.createGraphics();
	}
	
	/**
	 * Clears the screen before anything is drawn every frame
	 */
	public void clear()
	{
		m_graphics.setColor(Color.WHITE);
		m_graphics.fillRect(0, 0, m_width, m_height);
	}
	
	/**
	 * Draws a texture
	 * @param p_texture the texture to be drawn
	 * @param p_frame the current frame to draw
	 * @param p_dest destination of the drawn image
	 */
	public void drawTexture(Texture p_texture, IntRect p_frame, IntRect p_dest)
	{	
		//draw the scaled image
		m_graphics.drawImage(p_texture.getImage(), p_dest.x, p_dest.y, (int) ((p_dest.x + p_dest.w) * m_scale), (int) ((p_dest.y + p_dest.h) * m_scale),
					 p_frame.x, p_frame.y, p_frame.x + p_frame.w, p_frame.y + p_frame.h, m_panel);
	}
	
	/**
	 * Draws a solid rectangle
	 * @param p_rect the dimensions of the rectangle
	 * @param p_color the color of the rectangle (Color.RED, Color.BLUE, etc)
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 */
	public void drawRect(IntRect p_rect, Color p_color, float p_opacity)
	{
		m_graphics.setColor(p_color);
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		m_graphics.fillRect(p_rect.x, p_rect.y, p_rect.w, p_rect.h);
	}
	
	/**
	 * Draws a rectangle border
	 * @param p_rect the dimensions of the rectangle
	 * @param p_color the color of the rectangle (Color.RED, Color.BLUE, etc.)
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 * @param p_thickness THICCness in pixels
	 */
	public void drawRect(IntRect p_rect, Color p_color, float p_opacity, int p_thickness)
	{
		m_graphics.setColor(p_color);
		m_graphics.setStroke(new BasicStroke(p_thickness));
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		m_graphics.drawRect(p_rect.x, p_rect.y, p_rect.w, p_rect.h);
	}
	
	/**
	 * Draws an ellipse
	 * @param p_rect Major axis (width) and minor axis (height)
	 * @param p_color the color of the ellipse (Color.RED, Color.BLUE, etc.)
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 */
	public void drawEllipse(IntRect p_rect, Color p_color, float p_opacity)
	{
		m_graphics.setColor(p_color);
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		m_graphics.draw(new Ellipse2D.Double(p_rect.x, p_rect.y, p_rect.w, p_rect.h)); 
	}

	/**
	 * Draws text
	 * @param p_text The text to be displayed
	 * @param p_font Exact name of the font (e.g. "Serif", "Comic Sans" etc)
	 * @param p_color Color of the text (Color.RED, Color.BLUE, etc.)
	 * @param p_x x-position of text
	 * @param p_y y-position of text 
	 * @param p_size Font size
	 */
	public void drawText(String p_text, String p_font, Color p_color, int p_x, int p_y, int p_size)
	{
		System.out.println("esdfsdf");
		m_graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font(p_font, Font.PLAIN, p_size);
		m_graphics.setColor(p_color);
		m_graphics.setFont(font);
		m_graphics.drawString(p_text, p_x, p_y);
	}

	@Override
	public JComponent getComponent()
	{
		return m_panel;
	}
	
	//TODO: custom boxes from sprite sheet?
}
