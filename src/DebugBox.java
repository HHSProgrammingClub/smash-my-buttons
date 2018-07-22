import java.awt.Color;

/**
 * Visualization of hitboxes for testing purposes
 * @author Catherine Guevara
 */
public class DebugBox implements Drawable
{
	private IntRect m_dimensions;
	private Color m_color;
	private float m_opacity;
	
	private int m_thickness; //THICC
	
	public DebugBox() {}
	
	/**
	 * Create a solid, opaque rectangle
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
	 * Create a solid rectangle with an option for opacity
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

	/**
	 * Create a rectangle border
	 * @param p_dimensions x, y, width, height
	 * @param p_color Color.RED, BLUE, WHITE, etc
	 * @param p_opacity 0.0f (transparent) -- 1.0f (opaque)
	 * @param p_thickness pixel thickness of the border
	 */
	public DebugBox(IntRect p_dimensions, Color p_color, float p_opacity, int p_thickness)
	{
		m_dimensions = p_dimensions;
		m_color = p_color;
		m_opacity = p_opacity;
		m_thickness = p_thickness;
	}
	
	/**
	 * Draws the box according to whether it's filled or a border
	 */
	@Override
	public void draw(Renderer p_renderer)
	{
		if(m_thickness > 0)
			p_renderer.drawRect(m_dimensions, m_color, m_opacity, m_thickness);
		else
			p_renderer.drawRect(m_dimensions, m_color, m_opacity);
	}
}
