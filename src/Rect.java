import org.dyn4j.geometry.Vector2;


public class Rect 
{
	/** Coordinates of rectangle, width, and height*/
	public float x, y, w, h;

	/** 
	 * Constructor.
	 * @param p_x x-coordinate (top-left corner)
	 * @param p_y y-coordinate (top-left corner)
	 * @param p_w width
	 * @param p_h height
	 */
	public Rect(float p_x, float p_y, float p_w, float p_h)
	{
		x = p_x;
		y = p_y;
		w = p_w;
		h = p_h;
	}
	
	public boolean collidesWith(Rect p_rect)
	{
		return (x <= (p_rect.x + p_rect.w) && 
				x+w >= p_rect.x &&
				y <= (p_rect.y + p_rect.h) &&
				p_rect.y <= (p_rect.y + h));
	}
	
	public Vector2 getMiddle()
	{
		//check me on this -Cathy
		return new Vector2(x + w/2, y + h/2);
	}
	
	public void scale(float scalar)
	{
		w *= scalar;
		h *= scalar;
	}
}
