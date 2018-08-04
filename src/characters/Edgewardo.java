package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import characters.characterStates.CharacterState;
import graphics.Sprite;
import graphics.Texture;
import program.Hitbox;

public class Edgewardo extends Character
{
	private static double position = 0;
	private float length = 1;
	private float height = 2;

	public Edgewardo()
	{
		Body emo = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		emo.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(1, 2);
		rect.translate(1, 1); // Set to topleft
		emo.addFixture(rect);
		emo.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(emo);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/edgewardo");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	private class JabState extends CharacterState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public JabState()
		{
			super("jab");
			
			m_hitbox.setDuration(0.2f);
			m_hitbox.setDamage(5);
			m_hitbox.setHitstun(0.3f);
			m_hitbox.setBaseKnockback(new Vector2(1.5 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(getFacing(), -1));
		
			m_rect = new Rectangle(0.5, 1);
			m_rect.translate(length + 0.25 * getFacing(), 1);
			
			m_fixture = new BodyFixture(m_rect);
		}
		
		protected void init()
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
	
	@Override
	public void jab() 
	{
		interruptStates(new CharacterState("jab", 0.1f));
		addState(new JabState());
	}
	
	//tilt is kinda weird

	@Override
	public void tilt() 
	{
		// TODO Auto-generated method stub
		
	}
	
	private class SmashState extends CharacterState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public SmashState()
		{
			super("smash");
			
			m_hitbox.setDuration(0.4f);
			m_hitbox.setDamage(15);
			m_hitbox.setHitstun(0.4f);
			m_hitbox.setBaseKnockback(new Vector2(0.5 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(4 * getFacing(), -1));
			
			m_rect = new Rectangle(0.5, 1);
			m_rect.translate(length + 0.5 * getFacing(), 1.25);
		
			m_fixture = new BodyFixture(m_rect);
		}
		
		protected void init()
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

	@Override
	public void smash()
	{
		interruptStates(new CharacterState("smash", 0.1f));
		addState(new SmashState());
	}

	@Override
	public void projectile()
	{
		// TODO Auto-generated method stub
		m_sprite.setAnimation("projectile");
	}

	@Override
	public void signature()
	{
		// TODO Auto-generated method stub
		m_sprite.setAnimation("signature");
	}

	@Override
	public void recover() 
	{
		// TODO Auto-generated method stub
		m_sprite.setAnimation("recovery");
	}

	@Override
	public String getName()
	{
		return Character.characterNames[4];
	}

}
