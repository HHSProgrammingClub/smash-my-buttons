package program;
import java.util.Iterator;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Transform;

import graphics.Drawable;
import graphics.Sprite;
import graphics.pages.Renderer;
import characters.Character;

public class Projectile implements Drawable
{
	public Projectile() {}
	
	public Projectile(Sprite p_sprite)
	{
		setSprite(p_sprite);
	}
	
	public Projectile(Sprite p_sprite, Hitbox p_hitbox)
	{
		setSprite(p_sprite);
		setHitbox(p_hitbox);
	}
	
	public void setSprite(Sprite p_sprite)
	{
		m_sprite = p_sprite;
	}
	
	//we could change this if projectile ever have more than one hitbox
	public void setHitbox(Hitbox p_hitbox)
	{
		m_hitbox = p_hitbox;
	}
	
	public Hitbox getHitbox() { return m_hitbox; }
	
	public void setBody(Body p_body)
	{
		//add the hitbox to all BodyFixtures in the body -- can change
		Iterator<BodyFixture> fixtures = p_body.getFixtureIterator();
		
		while(fixtures.hasNext())
		{
			BodyFixture f = (BodyFixture)fixtures.next();
			m_hitbox.addToFixture(f);
		}
		
		p_body.setUserData(this);
		m_body = p_body;
	}
	
	public Body getBody()
	{
		return m_body;
	}
	
	public void setCharacter(Character p_character)
	{
		m_character = p_character;
	}
	
	public Character getCharacter()
	{
		return m_character;
	}
	
	public Sprite getSprite()
	{
		return m_sprite;
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		Transform t = m_body.getTransform();
		
		m_sprite.setPosition(t.getTranslationX(), t.getTranslationY());
		m_sprite.setRotation(t.getRotation());
		m_sprite.draw(p_renderer);
	}
	
	public void setDuration(float p_duration)
	{
		m_duration = p_duration;
		m_timer = m_duration;
	}
	
	public boolean update(float p_delta)
	{
		if(m_duration < 0)
			return true;
		m_timer -= p_delta;
		return m_timer > 0;
	}
	
	private Hitbox m_hitbox;
	private Sprite m_sprite;
	private Body m_body;
	private Character m_character;
	protected float m_duration = -1;
	protected float m_timer;
}
