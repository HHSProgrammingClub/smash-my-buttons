package characters;

import java.awt.geom.AffineTransform;

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
import program.CharacterEffect;
import program.Hitbox;
import program.Projectile;
import resourceManager.ResourceManager;

public class Edgewardo extends Character
{
	private static double position = 0;
	private float length = 1;
	private float height = 1.5f;

	public Edgewardo()
	{
		jumpImpulse = new Vector2(0, -12);
		runForce = new Vector2(23, 0);
		maxRunSpeed = 6.5f;
		Body emo = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		emo.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(1, 2);
		rect.translate(1, 1); // Set to topleft
		
		BodyFixture bf = new BodyFixture(rect);
		bf.setDensity(0.75);
		emo.addFixture(bf);
		emo.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(emo);
		
		Texture tex = ResourceManager.getResource(Texture.class, "resources/images/edgewardo");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	private class JabState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public JabState()
		{
			super("jab");
			
			m_hitbox.setDuration(0.2f);
			m_hitbox.setDamage(1);
			m_hitbox.setHitstun(0.75f);
			m_hitbox.setBaseKnockback(new Vector2(1.5 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(getFacing(), -1));
		
			m_rect = new Rectangle(0.5, 1);
			m_rect.translate(length + 0.3 * getFacing(), 1);
			
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
		pushState(new WaitState(0.2f));
		pushState(new JabState());
		pushState(new AttackState("jab", .1f));
	}

	@Override
	public void tilt() 
	{
		AttackState tiltBeginning = new AttackState("tilt_dash", 0.3f) {};
		
		AttackState tiltDash = new AttackState("tilt_dash", 0.1f)
		{
			Hitbox m_hitbox = new Hitbox();
			Rectangle m_rect;
			BodyFixture m_fixture;
			
			@Override
			public void init()
			{
				m_hitbox.setDuration(0.1f);
				m_hitbox.setDamage(7);
				m_hitbox.setHitstun(0.3f);
				m_hitbox.setBaseKnockback(new Vector2(getFacing(), 0));
				m_hitbox.setScaledKnockback(new Vector2(5 * getFacing(), -1));
				
				m_rect = new Rectangle(4, 1); //reaalllly big hitbox
				m_rect.translate(3 * getFacing(), 1.5);
				
				m_fixture = new BodyFixture(m_rect);
				
				addHitbox(m_hitbox);
				m_hitbox.addToFixture(m_fixture);
				m_body.addFixture(m_fixture);
				m_body.setLinearDamping(10);
			}
			
			@Override
			public void end()
			{
				m_body.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				m_body.setLinearDamping(0);
			}
		};
				
		AttackState tiltEnd = new AttackState("tilt_end", 0.5f)
				{
					@Override
					public void init()
					{
						m_body.translate(3.5 * getFacing(), 0);
						m_body.setGravityScale(1);
						m_body.setAsleep(false);
						m_body.applyImpulse(new Vector2(2 * getFacing(), 0));
						m_body.setLinearDamping(10);
					}
					
					@Override
					public void end() {
						m_body.setLinearDamping(0);
					}
				};
				
		pushState(tiltEnd);
		pushState(tiltDash);
		pushState(tiltBeginning);
	}
	
	private class SmashState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public SmashState()
		{
			super("smash");
			
			m_hitbox.setDuration(0.4f);
			m_hitbox.setDamage(11);
			m_hitbox.setHitstun(0.4f);
			m_hitbox.setBaseKnockback(new Vector2(0.5 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(7 * getFacing(), -4));
			
			m_rect = new Rectangle(0.5, 1);
			m_rect.translate(length + 0.6 * getFacing(), 1.25);
		
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
		pushState(new SmashState());
		//animation plays twice with this
		//pushState(new AttackState("smash", .3f));
	}
	
	private class ProjectileStart extends AttackState
	{
		public ProjectileStart()
		{
			super("projectile");
		}
	}

	private class ProjectileState extends AttackState
	{
		private Projectile knife;
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		private Body m_knifeBody = new Body();
		
		public ProjectileState()
		{
			super("idle");
			setDuration(0.33f);
			Texture knifeTex = ResourceManager.getResource(Texture.class, "resources/images/knife");
			
			Sprite knifeSprite = new Sprite(knifeTex);
			knifeSprite.setAnimation("default");
			
			m_hitbox.setDuration(2f);
			m_hitbox.setDamage(2);
			m_hitbox.setHitstun(0.01f);
			m_hitbox.setBaseKnockback(new Vector2(0, 0));
			m_hitbox.setScaledKnockback(new Vector2(1 * getFacing(), 0));
			
			m_rect = new Rectangle(0.5, 0.5);
			m_rect.translate(0, 0);
			
			knife = new Projectile(knifeSprite, m_hitbox);
			knife.setCharacter((Character) m_body.getUserData());
			m_fixture = new BodyFixture(m_rect);
			
			Transform t = new Transform();
			t.translate(m_body.getTransform().getTranslation());
			t.translate(getFacing(), 1);
			
			m_knifeBody.setTransform(t);
			m_knifeBody.addFixture(m_fixture);
			m_knifeBody.setMass(MassType.INFINITE);
			
			knife.getSprite().setScale(m_facingRight ? RIGHT_SCALE : LEFT_SCALE);
			addProjectile(knife);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_fixture.setSensor(false);
			knife.setBody(m_knifeBody);
			m_knifeBody.setLinearVelocity(new Vector2(15*getFacing(), 0));
			m_world.addBody(m_knifeBody);
		}
		
		protected void onUpdate(float p_delta)
		{
			if(!m_hitbox.isAlive()) 
			{
				m_knifeBody.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				m_knifeBody.removeAllFixtures();
				m_world.removeBody(m_knifeBody);
				removeProjectile(knife);
			}
		}
	}
	
	@Override
	public void projectile()
	{
		pushState(new ProjectileState());
		pushState(new AttackState("projectile", 0.4f));
	}

	@Override
	public void signature()
	{
		AttackState sigState = new AttackState("signature", 0.8f)
				{
					//randomness for fun
					private Hitbox[] m_hitboxes = new Hitbox[(int)((Math.random() * 5)+2)];
					private int length = m_hitboxes.length;
					
					private Projectile[] m_fog = new Projectile[length];
					private BodyFixture[] m_fixtures = new BodyFixture[length];
					private Body[] fogBodies = new Body[length];
					private Sprite fogSprite;
					
					@Override
					public void init()
					{
						Texture fogTexture = ResourceManager.getResource(Texture.class, "resources/images/fog");
						
						fogSprite = new Sprite(fogTexture, "default");
						
						for(int i = 0; i < m_hitboxes.length; i++)
						{
							m_hitboxes[i] = new Hitbox();
							float randomDuration = (float)(Math.random() * 2) + 4;
							m_hitboxes[i].setDuration(randomDuration);
							m_hitboxes[i].setDamage(1);
							m_hitboxes[i].setHitstun(0.1f);
							m_hitboxes[i].setBaseKnockback(new Vector2(0, 0));
							m_hitboxes[i].setScaledKnockback(new Vector2(0, 0));
							
							Rectangle rect = new Rectangle(2, 1);
							rect.translate(0, 0);
							
							m_fog[i] = new Projectile(fogSprite, m_hitboxes[i]);
							m_fog[i].setCharacter((Character) m_body.getUserData());
							
							m_fixtures[i] = new BodyFixture(rect);
							
							Transform t = new Transform();
							t.translate(m_body.getTransform().getTranslation());
							
							double randomDirection = Math.random();
							double randomOffset = Math.random() * (randomDirection >= 0.5 ? 1 : -1);
							t.translate(1 + randomOffset, 1.5+randomOffset);
							
							fogBodies[i] = new Body();
							fogBodies[i].setTransform(t);
							fogBodies[i].addFixture(m_fixtures[i]);
							fogBodies[i].setMass(MassType.INFINITE); //this is funny because all these are fog
						
							addHitbox(m_hitboxes[i]);
							m_hitboxes[i].addToFixture(m_fixtures[i]);
							m_fixtures[i].setSensor(false);
							m_fog[i].setBody(fogBodies[i]);
							double randomVelocity = Math.random() * (randomDirection >= 0.5 ? 1 : -1);
							
							fogBodies[i].setLinearVelocity((0.5 + randomVelocity)*getFacing(), 0);
							m_world.addBody(fogBodies[i]);
							
							addProjectile(m_fog[i]);
						}
					}
					
					@Override
					public void interrupt()
					{
					}
					
					@Override
					public void end()
					{
						
					}
				};
		// TODO: disappearing animation
		pushState(sigState);
	}
	
	private class RecoveryState extends AttackState
	{
		public RecoveryState()
		{
			super("recovery");
		}
		
		protected void init() {}
		
		public void end() {}
	}

	@Override
	public void recover() 
	{
		AttackState recoveryStart = new AttackState("recovery", 0.5f)
				{
					private Hitbox m_hitbox = new Hitbox();
					private Rectangle m_rect;
					private BodyFixture m_fixture;
					
					@Override
					public void init()
					{
						m_hitbox.setDuration(1.0f);
						m_hitbox.setDamage(2);
						m_hitbox.setHitstun(0.1f);
						m_hitbox.setBaseKnockback(new Vector2(1, -1));
						m_hitbox.setScaledKnockback(new Vector2(1, -1));
						
						m_rect = new Rectangle(1, 1);
						m_rect.translate(1, 1.25);
						
						m_fixture = new BodyFixture(m_rect);
						
						addHitbox(m_hitbox);
						m_hitbox.addToFixture(m_fixture);
						m_body.addFixture(m_fixture);
						m_body.setLinearDamping(10);
					}
					
					@Override
					public void end()
					{
						getBody().setLinearVelocity(0, 0);
						m_body.translate(0, -4);
						m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
					}
				};
				
		AttackState recoveryEnd = new AttackState("jump_dsc", 0.1f)
				{
					private Hitbox m_hitbox = new Hitbox();
					private Rectangle m_rect;
					private BodyFixture m_fixture;
					
					@Override
					public void init()
					{
						m_hitbox.setDuration(1.0f);
						m_hitbox.setDamage(10);
						m_hitbox.setHitstun(0.5f);
						m_hitbox.setBaseKnockback(new Vector2(5 * getFacing(), -5));
						m_hitbox.setScaledKnockback(new Vector2(12 * getFacing(), -12));
						
						m_rect = new Rectangle(2, 2);
						m_rect.translate(1, 1.25);
						
						m_fixture = new BodyFixture(m_rect);
						
						addHitbox(m_hitbox);
						m_hitbox.addToFixture(m_fixture);
						m_body.addFixture(m_fixture);
						
						AffineTransform explosionOffset = new AffineTransform();
						//somehow draw this
						
						//m_body.setLinearDamping(0);
						float offset = 1.5f;
						explosionOffset.translate(m_body.getWorldCenter().x + getFacing(), m_body.getWorldCenter().y - offset);
						explosionOffset.scale(1.5, 1.5);
						
						CharacterEffect explosionEffect = new CharacterEffect("explosion", "explosion", explosionOffset);
						
						addEffect(explosionEffect);
						
						m_body.setLinearDamping(40);
					}
					
					@Override
					public void end()
					{
						m_body.applyImpulse(new Vector2(0, -2));
						m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						m_body.setLinearDamping(0);
						m_recovered = true;
					}
				};
		
		if(!m_recovered)
		{
			pushState(recoveryEnd);
			pushState(recoveryStart);
			m_recovered = true;
		}
	}

	@Override
	public String getName()
	{
		return Character.characterNames[4];
	}

}
