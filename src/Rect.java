/**
 * Rectangle class for various uses.
 * @author Catherine Guevara
 */
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
	
	/**
	 * Checks if two Rects overlap with each other
	 * @param p_rect Rect to check collision
	 * @return whether or not they overlap
	 */
	public boolean collidesWith(Rect p_rect)
	{
		return (x <= (p_rect.x + p_rect.w) && 
				x+w >= p_rect.x &&
				y <= (p_rect.y + p_rect.h) &&
				p_rect.y <= (p_rect.y + h));
	}
	
	/**
	 * Returns the center coordinates of a Rect
	 * @return Vector2
	 */
	public Vector2 getMiddle()
	{
		//check me on this -Cathy
		return new Vector2(x + w/2, y + h/2);
	}
	
	/**
	 * Scales a given Rect by a scalar
	 * @param scalar the scalar
	 */
	public void scale(float scalar)
	{
		w *= scalar;
		h *= scalar;
	}
}
