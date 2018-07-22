import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class DebugBox implements Drawable
{
	private Graphics2D g2;
	private IntRect m_dimensions;
	private Color m_color; //Color.RED
	private float m_opacity;
	
	public DebugBox() {}
	
	/**
	 * Constructor
	 * @param p_dimensions x, y, width, height
	 * @param p_color Color.RED, BLUE, WHITE, etc.
	 */
	public DebugBox(IntRect p_dimensions, Color p_color)
	{
		m_dimensions = p_dimensions;
		m_color = p_color;
		m_opacity = 1.0f; //opaque
	}
	
	/**
	 * Constructor with option for opacity
	 * @param p_dimensions x, y, width, height
	 * @param p_color Color.RED, BLUE, WHITE, etc.
	 * @param p_opacity 0.0f (transparent) -- 1.0f (opaque) 
	 */
	public DebugBox(IntRect p_dimensions, Color p_color, float p_opacity)
	{
		m_dimensions = p_dimensions;
		m_color = p_color;
		m_opacity = p_opacity;
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		p_renderer.drawRect(m_dimensions, m_color, m_opacity);
	}
}
