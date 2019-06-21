package graphics.pages;


import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;

import graphics.DoubleRect;
import graphics.IntRect;
import graphics.Texture;

/**
 * Renders all the sprites, GUI, and additional shapes to the screen
 * @author Catherine Guevara
 */
public class Renderer
{
	public Graphics m_graphics;
	
	private float m_pixelScale = 1;
	
	public class Transformer
	{
		private Graphics m_graphics;
		
		private Transformer(Graphics p_graphics)
		{
			m_graphics = p_graphics;
		}
		
		public Transformer translate(double x, double y)
		{
			// The rest of the code-base keeps swapping between
			// doubles and floats so this will just take a double
			// (that can take both) and cast it to the desired float.
			m_graphics.translate((float)x, (float)y);
			return this;
		}
		
		public Transformer translate(Vector2 p_vec)
		{
			return translate(p_vec.x, p_vec.y);
		}
		
		public Transformer scale(double xy)
		{
			m_graphics.scale((float)xy, (float)xy);
			return this;
		}
		
		public Transformer scale(double x, double y)
		{
			m_graphics.scale((float)x, (float)y);
			return this;
		}
		
		public Transformer scale(Vector2 p_vec)
		{
			m_graphics.scale((float)p_vec.x, (float)p_vec.y);
			return this;
		}
		
		public Transformer rotateDeg(double p_degrees)
		{
			m_graphics.rotate(0, 0, (float)p_degrees);
			return this;
		}
		
		public Transformer rotateRad(double p_radians)
		{
			m_graphics.rotate(0, 0, (float)p_radians * 57.2974f);
			return this;
		}
	}
	
	/**
	 * Creates renderer for displaying images and shapes
	 */
	public Renderer(Graphics p_graphics, float p_pixelScale)
	{
		m_graphics = p_graphics;
		m_pixelScale = p_pixelScale;
		//m_graphics.setLineWidth(p_pixelScale);
	}
	
	/**
	 * Add a transform to the stack.
	 * @param p_transform This matrix will be multiplied by the previous transform.
	 */
	public Transformer pushTransform()
	{
		m_graphics.pushTransform();
		return new Transformer(m_graphics);
	}
	
	/**
	 * Converts a dyn4j transform to a graphics transform and adds it to the stack.
	 * @param p_transform
	 */
	public void pushTransform(org.dyn4j.geometry.Transform p_transform)
	{
		pushTransform()
			.translate(p_transform.getTranslationX(), p_transform.getTranslationY())
			.rotateRad(p_transform.getRotation());
	}
	
	/**
	 * Pops a transform from the stack.
	 * The current transform will the be the previous transform.
	 */
	public void popTransform()
	{
		m_graphics.popTransform();
	}
	
	/**
	 * Set the scale in which only the size of images are affected.
	 * This allows you to compensate for transform scaling.
	 * @param p_factor
	 */
	public void setSizeScale(float p_factor)
	{
		m_pixelScale = p_factor;
	}
	
	/**
	 * Draws a texture
	 * @param p_texture the texture to be drawn
	 * @param p_frame the current frame to draw
	 * @param p_dest destination of the drawn image
	 */
	public void drawTexture(Texture p_texture, IntRect p_frame)
	{	
		//scale image according to the current scale
		pushTransform()
			.scale(m_pixelScale, m_pixelScale);
		
		float x = (float)p_frame.w;
		float y = (float)p_frame.h;
		float w = (float)p_frame.w;
		float h = (float)p_frame.h;
		
		//draw the scaled image to desired destination
		m_graphics.drawImage(p_texture.getImage(), 0.f, 0.f, w, h,
					 x, y, x + w, y + h);
	
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
		m_graphics.draw(new Rectangle((float)p_rect.x, (float)p_rect.y, (float)p_rect.w, (float)p_rect.h));
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
		m_graphics.draw(new Ellipse((float)p_rect.x, (float)p_rect.y, (float)p_rect.w, (float)p_rect.h));
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
		java.awt.Font font = new java.awt.Font(p_font, java.awt.Font.PLAIN, p_size);
		UnicodeFont sfont = new UnicodeFont(font);
		
		m_graphics.setColor(p_color);
		m_graphics.setFont(sfont);
		m_graphics.drawString(p_text, 0, 0);
	}
	
	/**
	 * Cover the canvas with a color
	 * @param p_color The color to overlay the screen
	 * @param p_opacity 0.0f transparent to 1.0f (opaque)
	 */
	public void addScreenOverlay(Color p_color, float p_opacity)
	{
		m_graphics.setColor(p_color);
		m_graphics.fillRect(0, 0, 10000, 10000);
	}
	
	public void drawLine(float x1, float y1, float x2, float y2, Color p_color, float p_thickness, float p_opacity)
	{
		m_graphics.setColor(p_color);
		m_graphics.drawLine(x1, y1, x2, y2);
	}
}
