import java.util.Iterator;

import org.dyn4j.dynamics.*;

public abstract class Character {
	private Hitbox hurtbox;
	private Sprite m_sprite;
	private 
	private String name = "Bartholomew the Glass-Cutter";
	// Methods from projectile
	public void setSprite(Sprite p_sprite) { m_sprite = p_sprite;}
	public void setHurtbox(Hitbox p_hurtbox) { hurtbox = p_hurtbox; }
	public String getName() {return name; }
	public void addToBody(Body p_body)
	{
		//add the hitbox to all BodyFixtures in the body -- can change
		Iterator<BodyFixture> fixtures = p_body.getFixtureIterator();
		while(fixtures.hasNext()) {
			BodyFixture f = (BodyFixture)fixtures.next();
			hurtbox.addToFixture(f);
		}
		p_body.setUserData(this);
	}
}
