package graphics;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Vector2;


public class IntRect 
{
	/** Coordinates of rectangle, width, and height*/
	public int x, y, w, h;

	/**
	 * Default constructor.
	 */
	public IntRect()
	{
		x = 0;
		y = 0;
		w = 0;
		h = 0;
	}
	
	/**
	 * Copy Constructor.
	 * @param p_copy Rect to be copied
	 */
	public IntRect(IntRect p_copy)
	{
		x = p_copy.x;
		y = p_copy.y;
		w = p_copy.w;
		h = p_copy.h;
	}
	
	/** 
	 * Constructor.
	 * @param p_x x-coordinate (top-left corner)
	 * @param p_y y-coordinate (top-left corner)
	 * @param p_w width
	 * @param p_h height
	 */
	public IntRect(int p_x, int p_y, int p_w, int p_h)
	{
		x = p_x;
		y = p_y;
		w = p_w;
		h = p_h;
	}
	
	/**
	 * Create Rect from 2 vectors. Components will be directly casted to int.
	 * @param p_offset
	 * @param p_size
	 */
	public IntRect(Vector2 p_offset, Vector2 p_size)
	{
		x = (int)p_offset.x;
		y = (int)p_offset.y;
		w = (int)p_size.x;
		h = (int)p_size.y;
	}
	
	public IntRect(AABB p_boundingBox)
	{
		this(
				(int)(p_boundingBox.getMinX()), (int)(p_boundingBox.getMinY()),
				(int)(p_boundingBox.getMaxX() - p_boundingBox.getMinX()),
				(int)(p_boundingBox.getMaxY() - p_boundingBox.getMinY()));
	}
	
	/**
	 * Checks if two Rects overlap each other
	 * @param p_rect the Rect to check overlap
	 * @return whether two Rects overlap
	 */
	public boolean collidesWith(IntRect p_rect)
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
		return new Vector2(x + w/2, y + h/2);
	}
	
	public void scale(int scalar)
	{
		w *= scalar;
		h *= scalar;
	}
	
	public String toString() {
		//quickndirty
		return "" + x + " " + y + " " + w + " " + h + " ";
	}
}
