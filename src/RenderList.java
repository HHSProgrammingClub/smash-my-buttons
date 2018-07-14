import java.util.ArrayList;

public class RenderList
{
	private ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	
	public void draw(Renderer p_renderer)
	{
		for(Drawable drawable : drawables)
		{
			drawable.draw(p_renderer);
		}
	}
	
	public void addDrawable(Drawable p_drawable)
	{
		drawables.add(p_drawable);
	}
	
	public void addDrawable(Drawable p_drawable, int position)
	{
		drawables.add(position, p_drawable);
	}
	
	//have a better way of doing this, anyone?
	public Drawable removeDrawable(Drawable p_drawable)
	{
		Drawable temp = null;
		
		for(int i = 0; i < drawables.size(); i++)
		{
			if(p_drawable.equals(drawables.get(i)))
			{
				temp = drawables.get(i);
				drawables.remove(i);
				break;
			}
		}
		return temp;
	}
	
	public Drawable removeDrawable(int position)
	{
		return drawables.remove(position);
	}
}
