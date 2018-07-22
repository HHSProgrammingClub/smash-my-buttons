import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class DebugBox implements Drawable
{
	public Graphics2D g2;
	public IntRect m_dimensions;
	public Color m_color; //Color.RED
	public float m_opacity;
	//TODO: opacity implementation
	
	/**
	 * Constructor
	 * @param p_dimensions 
	 * @param p_color
	 */
	public DebugBox(IntRect p_dimensions, Color p_color)
	{
		m_dimensions = p_dimensions;
		m_color = p_color;
		m_opacity = 1.0f;
	}
	
	/**
	 * Constructor with option for opacity
	 * @param p_dimensions
	 * @param p_color
	 * @param p_opacity
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
