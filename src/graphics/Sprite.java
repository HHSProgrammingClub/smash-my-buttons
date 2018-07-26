package graphics;
import org.dyn4j.geometry.Vector2;

import program.Animation;
import program.Clock;

public class Sprite implements Drawable
{
	private Texture m_texture;
	private Animation m_animation;
	private Vector2 m_position = new Vector2(0, 0);
	private Vector2 m_scale = new Vector2(1, 1);
	private double m_rotation;
	private Clock m_clock = new Clock();
	
	public Sprite()
	{
	}
	
	public Sprite(Texture p_texture)
	{
		setTexture(p_texture);
		setAnimation("default:default");
	}
	
	public Sprite(Texture p_texture, String p_animation)
	{
		setTexture(p_texture);
		setAnimation(p_animation);
	}
	
	public void draw(Renderer p_renderer)
	{
		IntRect frame;
		if (m_animation.getFrameCount() > 1
				&& m_animation.getInterval() > 0)
		{
			// Looped
			int frameIdx = (int)(m_clock.getElapse() / m_animation.getInterval());
			frame = m_animation.getFrame(frameIdx);
		}
		else
			// Not looped
			frame = m_animation.getFrame();
		IntRect destination = new IntRect(m_position, new Vector2(frame.w*m_scale.x, frame.h*m_scale.y));
		p_renderer.drawTexture(m_texture, frame, destination);
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
	
	public void setPosition(double p_x, double p_y)
	{
		m_position.set(p_x, p_y);
	}
	
	public void setPosition(Vector2 p_position)
	{
		m_position.set(p_position);
	}
	
	public Vector2 getPosition()
	{
		return new Vector2(m_position);
	}
	
	public void setScale(double p_x, double p_y)
	{
		m_scale.set(p_x, p_y);
	}
	
	public void setScale(Vector2 p_scale)
	{
		m_scale.set(p_scale);
	}
	
	public Vector2 getScale()
	{
		return new Vector2(m_scale);
	}
}
