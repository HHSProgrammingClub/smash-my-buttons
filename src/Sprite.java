
public class Sprite implements Drawable
{
	private Texture m_texture;
	private Animation m_animation;
	private Rect m_position;
	private float m_rotation; //???
	private Clock m_clock;
	
	public Sprite(Texture p_texture, Animation p_animation)
	{
		m_texture = p_texture;
		m_animation = p_animation;
	}
	
	public void draw(Renderer p_renderer)
	{
		p_renderer.drawTexture(m_texture, m_animation.getFrame(), m_position);
	}
	
	public void setTexture(Texture p_texture)
	{
		m_texture = p_texture;
	}
	
	public Texture getTexture()
	{
		return m_texture;
	}
	
	public void setAnimation(String p_animationName)
	{
		m_animation = m_texture.getAnimation(p_animationName);
	}
	
	public void setAnimation(Animation p_animation)
	{
		m_animation = p_animation;
	}
	
	public Animation getAnimation()
	{
		return m_animation;
	}
	
	public void playAnimation()
	{
		m_clock.start();
	}
	
	public void pauseAnimation()
	{
		m_clock.pause();
	}
	
	public void stopAnimation()
	{
		m_clock.stop();
	}
	
	public void restartAnimation()
	{
		m_clock.restart();
	}
	
	public void setPosition(Rect p_position)
	{
		m_position = p_position;
	}
	
	public Rect getPosition()
	{
		return m_position;
	}
}
