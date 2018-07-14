
public class Sprite implements Drawable
{
	private Texture texture;
	private Animation animation;
	private Rect position;
	private float rotation; //???
	private Clock clock; //I'm not in charge of the clock
	
	public Sprite(Texture p_texture, Animation p_animation)
	{
		texture = p_texture;
		animation = p_animation;
	}
	
	public void draw(Renderer p_renderer)
	{
		p_renderer.drawTexture(texture, position);
	}
	
	public void setTexture(Texture p_texture)
	{
		texture = p_texture;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	
	public void setAnimation(String animationName)
	{
		
	}
	
	public void setAnimation(Animation newAnimation)
	{
		animation = newAnimation;
	}
	
	public Animation getAnimation()
	{
		return animation;
	}
	
	public void playAnimation()
	{
		
	}
	
	public void pauseAnimation()
	{
		
	}
	
	public void stopAnimation()
	{
		
	}
	
	public void restartAnimation()
	{
		
	}
	
	public void setPosition(Rect newPosition)
	{
		position = newPosition;
	}
	
	public Rect getPosition()
	{
		return position;
	}
}
