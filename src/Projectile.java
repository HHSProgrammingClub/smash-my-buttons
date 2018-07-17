import java.util.Iterator;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;

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
	
	public void setHitbox(Hitbox p_hitbox)
	{
		m_hitbox = p_hitbox;
	}
	
	public void addToBody(Body p_body)
	{
		//add the hitbox to all BodyFixtures in the body -- can change
		Iterator<BodyFixture> fixtures = p_body.getFixtureIterator();
		
		while(fixtures.hasNext())
		{
			BodyFixture f = (BodyFixture)fixtures.next();
			m_hitbox.addToFixture(f);
		}
		
		p_body.setUserData(this);
	}
	
	private Hitbox m_hitbox;
	//i don't know what exactly to do with this
	private Sprite m_sprite;
}
