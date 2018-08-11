package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import characters.characterStates.AttackState;
import characters.characterStates.WaitState;
import graphics.Sprite;
import graphics.Texture;
import program.Hitbox;
import resourceManager.ResourceManager;

public class WallTheEncircler extends Character
{
	private static double position = 0;
	private float length = 1;
	private float height = 2.0f;
	
	public WallTheEncircler()
	{
		jumpImpulse = new Vector2(0, -15);
		runForce = new Vector2(15, 0);
		maxRunSpeed = 5.0f;
		
		Body swol = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		swol.setTransform(t);
		position += 1;
		
		Rectangle getRekt = new Rectangle(length, height);
		getRekt.translate(1, 1);
		swol.addFixture(getRekt);
		swol.setMass(MassType.FIXED_ANGULAR_VELOCITY);
		
		setBody(swol);
		
		Texture textile = ResourceManager.getResource(Texture.class, "resources/images/Wall");
	
		Sprite mountainDew = new Sprite(textile);
		mountainDew.setAnimation("idle");
		
		setSprite(mountainDew);
	}
	
	private class JabState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public JabState()
		{
			super("jab");
			
			m_hitbox.setDuration(0.1f);
			m_hitbox.setDamage(2);
			m_hitbox.setHitstun(0.01f);
			m_hitbox.setBaseKnockback(new Vector2(20 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(20 * getFacing(), 0));
			
			m_rect = new Rectangle(1, 1);
			m_rect.translate(length + getFacing(), 1.5);
			
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
	}
	
	@Override
	public void jab() 
	{
		pushState(new JabState());
		pushState(new AttackState("jab", 0.05f));
	}
	
	private class TiltState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public TiltState()
		{
			super("tilt");
			
			m_hitbox.setDuration(0.01f);
			m_hitbox.setDamage(5);
			m_hitbox.setHitstun(0.5f);
			m_hitbox.setBaseKnockback(new Vector2(-10 * getFacing(), -15));
			m_hitbox.setScaledKnockback(new Vector2(-20 * getFacing(), -20));
			
			m_rect = new Rectangle(1, 1);
			m_rect.translate(length + getFacing(), 1.5);
			
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
	}

	@Override
	public void tilt() 
	{
		pushState(new TiltState());
		pushState(new AttackState("idle", 0.3f));
	}
	
	private class SmashState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public SmashState()
		{
			super("smash");
			
			m_body.applyImpulse(alignFacing(new Vector2(10, 0)));
			m_hitbox.setDamage(10);
			m_hitbox.setBaseKnockback(new Vector2(5*getFacing(), -10));
			m_hitbox.setScaledKnockback(new Vector2(5*getFacing(), -20));
			m_hitbox.setDuration(0.5f);
			
			m_rect = new Rectangle(1, 1);
			m_rect.translate(length + 0.5*getFacing(), 1.5);
			
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
	}

	@Override
	public void smash() 
	{
		pushState(new WaitState(1.0f));
		pushState(new SmashState());
	}

	@Override
	public void projectile() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void signature()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recover()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName()
	{
		return Character.characterNames[3];
	}

}
