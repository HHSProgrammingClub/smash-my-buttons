package gameObject;

import java.awt.geom.AffineTransform;

import org.dyn4j.geometry.Vector2;

import graphics.Animation;
import graphics.IntRect;
import graphics.Texture;
import graphics.pages.Renderer;
import program.Clock;

public class SpriteComponent extends Component
{
	private Texture m_texture;
	private Animation m_animation;
	private Vector2 m_position = new Vector2(0, 0);
	private Vector2 m_scale = new Vector2(1, 1);
	private double m_rotation;
	private Clock m_clock = new Clock();
	
	public SpriteComponent(GameObject p_object)
	{
		super(p_object);
	}
	
	public SpriteComponent(GameObject p_object, Texture p_texture)
	{
		super(p_object);
		setTexture(p_texture);
		setAnimation("default:default");
	}
	
	public SpriteComponent(GameObject p_object, Texture p_texture, String p_animation)
	{
		super(p_object);
		setTexture(p_texture);
		setAnimation(p_animation);
	}
	
	public void update()
	{
		m_position = getObject().getTransform().getTranslation();
	}
	
	private void draw(Renderer p_renderer)
	{
		if(m_animation == null)
			return;
		
		IntRect frame;
		if (m_animation.getFrameCount() > 1
				&& m_animation.getInterval() > 0)
		{
			// Animation
			int frameIdx = (int)(m_clock.getElapse() / m_animation.getInterval());
			frame = m_animation.getFrame(frameIdx);
		}
		else
			// Not animation
			frame = m_animation.getFrame();
		
		update();
		
		AffineTransform t = new AffineTransform();
		t.translate(m_position.x, m_position.y);
		t.rotate(m_rotation);
		t.scale(m_scale.x, m_scale.y);
		p_renderer.pushTransform(t);
		p_renderer.drawTexture(m_texture, frame);
		//for sprite visualization
		//p_renderer.drawRect(destination, Color.MAGENTA, 1.f, 2);
		p_renderer.popTransform();
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
		if (m_animation == null || !p_animationName.equals(m_animation.getName()))
		{
			m_animation = m_texture.getAnimation(p_animationName);
			m_clock.restart();
		}
	}
	
	public void setAnimation(Animation p_animation)
	{
		if (m_animation == null || m_animation != p_animation)
		{
			m_animation = p_animation;
			m_clock.restart();
		}
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
	
	public void setRotation(double p_rads)
	{
		m_rotation = p_rads; // i think its in rads
	}
	
	public double getRotation()
	{
		return m_rotation;
	}
	
	public void onRecieveMessage(OnRender p_message)
	{
		draw(p_message.getRenderer());
	}
}
