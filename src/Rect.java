import org.dyn4j.geometry.Vector2;


public class Rect 
{
	/** Coordinates of rectangle, width, and height*/
	public int x, y, w, h;

	/** 
	 * Constructor.
	 * @param p_x x-coordinate (top-left corner)
	 * @param p_y y-coordinate (top-left corner)
	 * @param p_w width
	 * @param p_h height
	 */
	public Rect(int p_x, int p_y, int p_w, int p_h)
	{
		x = p_x;
		y = p_y;
		w = p_w;
		h = p_h;
	}
	
	/**
	 * Checks if two Rects overlap each other
	 * @param p_rect the Rect to check overlap
	 * @return whether two Rects overlap
	 */
	public boolean collidesWith(Rect p_rect)
	{
		return (x <= (p_rect.x + p_rect.w) && 
				x+w >= p_rect.x &&
				y <= (p_rect.y + p_rect.h) &&
				p_rect.y <= (p_rect.y + h));
	}
	
	/**
	 * Returns the coordinates of the center of the Rect
	 * @return the coordinates of the center
	 */
	public Vector2 getMiddle()
	{
		//check me on this -Cathy
		return new Vector2(x + w/2, y + h/2);
	}
	
	public void scale(int scalar)
	{
		w *= scalar;
		h *= scalar;
	}
}
