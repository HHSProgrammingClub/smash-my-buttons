package program;
import java.util.Iterator;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

import graphics.Sprite;

public class Projectile {
	
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
	
	private Hitbox m_hitbox;
	private Sprite m_sprite;
	private Body m_body;
}
