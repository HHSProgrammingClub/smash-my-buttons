package graphics.pages;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JComponent;
import javax.swing.JPanel;

import graphics.DoubleRect;
import graphics.IntRect;
import graphics.Texture;

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

	private float m_scale = 1;
	
	private Stack<AffineTransform> m_transformStack = new Stack<AffineTransform>();
	
	private Lock m_frameSwapLock = new ReentrantLock();
	
	/**
	 * Creates renderer for displaying images and shapes
	 */
	public Renderer()
	{
		//get the dimensions of the screen
		m_width = Toolkit.getDefaultToolkit().getScreenSize().width;
		m_height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		//calculate the scale according to the screen size
		m_scale = (m_width * m_scale) / 800;
		
		//create the buffers to draw stuff on
		m_frontBuffer = new BufferedImage(m_width, m_height, BufferedImage.TYPE_INT_ARGB);
		m_backBuffer = new BufferedImage(m_width, m_height, BufferedImage.TYPE_INT_ARGB);
		m_graphics = m_backBuffer.createGraphics();
		
		//create the JPanel that holds the canvas
		m_panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g)
			{
				m_frameSwapLock.lock();
				try {
					super.paintComponent(g);
					g.drawImage(m_frontBuffer, 0, 0, this);
					repaint();
				} finally {
					m_frameSwapLock.unlock();
				}
			}
		};
		
		m_panel.setFocusable(true);
	}
	
	/**
	 * Ensures the current frame is being displayed
	 */
	public void display()
	{
		m_frameSwapLock.lock();
		try {
			BufferedImage temp = m_backBuffer;
			m_backBuffer = m_frontBuffer;
			m_frontBuffer = temp;
			
			m_graphics = m_backBuffer.createGraphics();
		} finally {
			m_frameSwapLock.unlock();
		}
	}
	
	/**
	 * Clears the screen before anything is drawn every frame
	 */
	public void clear()
	{
		m_graphics.setColor(Color.BLACK);
		m_graphics.fillRect(0, 0, m_width, m_height);
	}
	
	/**
	 * Add a transform to the stack.
	 * @param p_transform This matrix will be multiplied by the previous transform.
	 */
	public void pushTransform(AffineTransform p_transform)
	{
		if (m_transformStack.isEmpty())
			m_transformStack.push(new AffineTransform(p_transform));
		else
		{
			AffineTransform t = new AffineTransform(m_transformStack.peek());
			t.concatenate(p_transform);
			m_transformStack.push(t);
		}
		m_graphics.setTransform(m_transformStack.peek());
	}
	
	/**
	 * Converts a dyn4j transform to a graphics transform and adds it to the stack.
	 * @param p_transform
	 */
	public void pushTransform(org.dyn4j.geometry.Transform p_transform)
	{
		AffineTransform t = new AffineTransform();
		double[] values = p_transform.getValues();
		t.setTransform(values[0], values[3], values[1], values[4], values[2], values[5]);
		pushTransform(t);
	}
	
	/**
	 * Pops a transform from the stack.
	 * The current transform will the be the previous transform.
	 */
	public void popTransform()
	{
		m_transformStack.pop();
		if (m_transformStack.isEmpty())
			m_graphics.setTransform(new AffineTransform());
		else
			m_graphics.setTransform(m_transformStack.peek());
	}
	
	/**
	 * Set the scale in which only the size of images are affected.
	 * This allows you to compensate for transform scaling.
	 * @param p_factor
	 */
	public void setSizeScale(float p_factor)
	{
		m_scale = p_factor;
	}
	
	/**
	 * Draws a texture
	 * @param p_texture the texture to be drawn
	 * @param p_frame the current frame to draw
	 * @param p_dest destination of the drawn image
	 */
	public void drawTexture(Texture p_texture, IntRect p_frame)
	{	
		//reset opacity
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		//scale image according to the current scale
		AffineTransform scaleTransform = new AffineTransform();
		scaleTransform.scale(m_scale, m_scale);
		pushTransform(scaleTransform);
		
		//draw the scaled image to desired destination
		m_graphics.drawImage(p_texture.getImage(), 0, 0, p_frame.w, p_frame.h,
					 p_frame.x, p_frame.y, p_frame.x + p_frame.w, p_frame.y + p_frame.h, m_panel);
	
		popTransform();
	}
	
	/**
	 * Draws a solid rectangle
	 * @param p_rect the dimensions of the rectangle
	 * @param p_color the color of the rectangle (Color.RED, Color.BLUE, etc)
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 */
	public void drawRect(DoubleRect p_rect, Color p_color, float p_opacity)
	{
		m_graphics.setColor(p_color);
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		m_graphics.draw(new Rectangle2D.Double(p_rect.x, p_rect.y, p_rect.w, p_rect.h));
	}
	
	/**
	 * Draws a rectangle border
	 * @param p_rect the dimensions of the rectangle
	 * @param p_color the color of the rectangle (Color.RED, Color.BLUE, etc.)
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 * @param p_thickness THICCness in pixels
	 */
	public void drawRect(DoubleRect p_rect, Color p_color, float p_opacity, float p_thickness)
	{
		m_graphics.setColor(p_color);
		m_graphics.setStroke(new BasicStroke(p_thickness));
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		m_graphics.draw(new Rectangle2D.Double(p_rect.x, p_rect.y, p_rect.w, p_rect.h));
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
	public void drawText(String p_text, String p_font, Color p_color, int p_size, float p_opacity)
	{
		m_graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		
		Font font = new Font(p_font, Font.PLAIN, p_size);
		
		m_graphics.setColor(p_color);
		m_graphics.setFont(font);
		m_graphics.drawString(p_text, 0, 0);
	}
	
	/**
	 * Cover the canvas with a color
	 * @param p_color The color to overlay the screen
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 */
	public void addScreenOverlay(Color p_color, float p_opacity)
	{
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		
		m_graphics.setColor(p_color);
		m_graphics.fillRect(0, 0, m_width, m_height);
	}
	
	public void drawLine(double x1, double y1, double x2, double y2, Color p_color, float p_thickness, float p_opacity)
	{
		m_graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p_opacity));
		m_graphics.setColor(p_color);
		m_graphics.setStroke(new BasicStroke(p_thickness));
		m_graphics.draw(new Line2D.Double(x1, y1, x2, y2));
	}
	
	public void drawPath(Path2D.Double p_polygon, Color p_color)
	{
		
	}
	
	public int getWidth()
	{
		return m_width;
	}
	
	public int getHeight()
	{
		return m_height;
	}

	@Override
	public JComponent getComponent()
	{
		return m_panel;
	}
	
	//TODO: custom boxes from sprite sheet?
}
