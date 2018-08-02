package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Sprite;
import graphics.Texture;
import program.Hitbox;

//A sample character.

public class Jack extends Character
{	
	private static double position = 0;
	public Jack() 
	{
		//attribute editing
		jumpImpulse = new Vector2(0, -15);
		force_L = new Vector2(-8, 0);
		force_R = force_L.getNegative();
		
		Body tushie = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		tushie.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(1, 2);
		rect.translate(1, 1); // Set to topleft
		tushie.addFixture(rect);
		tushie.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(tushie);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/jack");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	//TODO: ascending and descending jump sprites for Jack
	
	private class JabState extends CharacterState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		JabState()
		{
			super("jab");
			
			m_hitbox.setDuration(0.1f);
			m_hitbox.setDamage(2);
			m_hitbox.setBaseKnockback(new Vector2(-0.05, 0));
			m_hitbox.setScaledKnockback(new Vector2(-4, -2));
			
			m_rect = new Rectangle(0.8, 0.3);
			m_rect.translate(-0.4, 1);
			
			m_fixture = new BodyFixture(m_rect);
		}
		
		protected void moreStart()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_body.addFixture(m_fixture);
		}
		
		public void interrupt()
		{
			m_body.removeFixture(m_fixture);
			removeHitbox(m_hitbox);
		}
		
		public void end()
		{
			m_body.removeFixture(m_fixture);
			removeHitbox(m_hitbox);
		}
		
		
	};
	
	public void jab()
	{
		
	}
	
	public void tilt()
	{
		
	}
	
	public void smash()
	{
		
	}
	
	public void projectile()
	{
		
	}
	
	public void signature()
	{
		
	}
	
	public void recover()
	{
		
	}

	@Override
	public String getName() 
	{
		return Character.characterNames[0];
	}
}
