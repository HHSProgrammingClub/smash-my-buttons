
public class Rect 
{
	public float x, y, w, h;

	public Rect(float p_x, float p_y, float p_w, float p_h)
	{
		x = p_x;
		y = p_y;
		w = p_w;
		h = p_h;
	}
	
	//checks if two Rects overlap each other
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
	
	//scales the height and width of the rectangle
	public void scale(float scalar)
	{
		w *= scalar;
		h *= scalar;
	}
}
