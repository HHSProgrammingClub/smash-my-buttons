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
import program.Projectile;
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
	
	private class ProjectileState extends AttackState
	{
		private Projectile chair;
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		private Body chairBody = new Body();
		
		public ProjectileState()
		{
			super("projectile");
			
			Texture texMex = ResourceManager.getResource(Texture.class, "resources/images/chair");
			
			Sprite chairemAnime = new Sprite(texMex);
			chairemAnime.setAnimation("default");
			
			m_hitbox.setDuration(3.0f);
			m_hitbox.setDamage(8);
			m_hitbox.setHitstun(0.2f);
			m_hitbox.setBaseKnockback(new Vector2(getFacing(), 5));
			m_hitbox.setScaledKnockback(new Vector2(2*getFacing(), 6));
			
			m_rect = new Rectangle(0.5, 0.5);
			m_rect.translate(0, 0);
			
			chair = new Projectile(chairemAnime, m_hitbox);
			chair.setCharacter((Character) m_body.getUserData());
			
			m_fixture = new BodyFixture(m_rect);
			
			Transform t = new Transform();
			t.translate(chairBody.getTransform().getTranslation());
			t.translate(1, 4);
			
			chairBody.setTransform(t);
			chairBody.addFixture(m_fixture);
			chairBody.setMass(MassType.NORMAL);
			
			addProjectile(chair);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_fixture.setSensor(false);
			chair.setBody(chairBody);
			chairBody.applyImpulse(new Vector2(4 * getFacing(), -2));
			chairBody.applyTorque(1.5);
			m_world.addBody(chairBody);
		}
		
		protected void onUpdate(float p_delta)
		{
			if(!m_hitbox.isAlive()) {
				chairBody.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				chairBody.removeAllFixtures();
				m_world.removeBody(chairBody);
			}
		}
	}

	@Override
	public void projectile() 
	{
		//someone time this correctly
		pushState(new ProjectileState());
		pushState(new AttackState("projectile", 0.3f));
		pushState(new WaitState(0.2f));
	}

	@Override
	public void signature()
	{
		AttackState suplexSlam = new AttackState("signature_slam", 0.3f)
		{
			@Override
			public void init()
			{
				m_body.setLinearVelocity(0, 25);
			}
			
			@Override
			public void end()
			{
				
			}
		};
		
		AttackState suplexThrow = new AttackState("signature_throw", 0.3f)
		{
			//somehow carry the target
			@Override
			public void init()
			{
				m_body.setLinearVelocity(-5*getFacing(), -15);
			}
			
			@Override
			public void end()
			{
				m_body.setLinearVelocity(0, 0);
			}
		};
		
		AttackState suplexDash = new AttackState("signature_dash", 1f)
		{
			Hitbox m_hitbox = new Hitbox();
			Rectangle m_rect = new Rectangle(1, 2);
			BodyFixture m_fixture;
			
			@Override
			public void init()
			{
				m_body.setLinearVelocity(new Vector2(10*getFacing(), 0));
			
				m_hitbox.setDamage(0);
				m_hitbox.setBaseKnockback(new Vector2(0, 0));
				m_hitbox.setScaledKnockback(new Vector2(0, 0));
				m_hitbox.setDuration(0.5f);
				
				m_rect.translate(0.5, 1.5);
				m_fixture = new BodyFixture(m_rect);
				
				m_body.addFixture(m_fixture);
				m_hitbox.addToFixture(m_fixture);
				addHitbox(m_hitbox);
			}
			
			@Override
			public void interrupt()
			{
				if(m_fixture != null)
					m_body.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				m_body.setLinearVelocity(0, 0);
			}
			
			@Override
			public void end()
			{
				interrupt();
			}
			
			@Override
			protected void onUpdate(float p_delta)
			{
				if(!m_hitbox.isAlive() && getTimer() > 0)
				{
					popState();
					pushState(suplexSlam);
					pushState(new WaitState(0.01f));
					pushState(suplexThrow);
				}
			}
		};
		pushState(suplexDash);
	}

	@Override
	public void recover()
	{
		AttackState recoveryJump = new AttackState("recovery_jump", 0.4f)
		{
			@Override
			public void init()
			{
				m_body.applyImpulse(new Vector2(2*getFacing(), -30));
			}
			
			@Override
			public void end()
			{
				m_body.setLinearVelocity(0, 0);
			}
		};
		
		AttackState recoveryTumble = new AttackState("recovery_tumble", 0.3f)
		{
			@Override
			public void init()
			{
				m_body.setLinearVelocity(0, 0);
			}
		};
		
		AttackState recoverySlam = new AttackState("recovery_slam", 0.3f)
		{
			private Hitbox m_hitbox = new Hitbox();
			private Rectangle m_rect;
			private BodyFixture m_fixture;
			
			@Override
			public void init()
			{
				m_body.setLinearVelocity(0, 0);
				m_body.applyImpulse(new Vector2(0, 40));
				
				m_hitbox.setDuration(0.2f);
				m_hitbox.setDamage(10);
				m_hitbox.setHitstun(0.2f);
				m_hitbox.setBaseKnockback(new Vector2(getFacing(), 20));
				m_hitbox.setScaledKnockback(new Vector2(getFacing(), 30));
			
				m_rect = new Rectangle(1, 0.2);
				m_rect.translate(1, 2);
				
				m_fixture = new BodyFixture(m_rect);
				
				addHitbox(m_hitbox);
				m_hitbox.addToFixture(m_fixture);
				m_body.addFixture(m_fixture);
			}
			
			@Override
			public void interrupt()
			{
				m_body.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
			}
			
			@Override
			protected void onUpdate(float p_delta)
			{
				if(m_body.getLinearVelocity().y == 0)
					m_body.applyImpulse(new Vector2(getFacing(), -15));
				
			}
			
			@Override
			public void end()
			{
				interrupt();
			}
		};
		pushState(recoverySlam);
		pushState(recoveryTumble);
		pushState(recoveryJump);
	}

	@Override
	public String getName()
	{
		return Character.characterNames[3];
	}

}
