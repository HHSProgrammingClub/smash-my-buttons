package characters;

import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import characters.characterStates.CharacterState;
import graphics.Sprite;
import graphics.Texture;
import program.CharacterEffect;
import program.Hitbox;

public class Edgewardo extends Character
{
	private static double position = 0;
	private float length = 1;
	private float height = 2;

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
			m_hitbox.setDamage(2);
			m_hitbox.setHitstun(1.5f);
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
		pushState(new JabState());
		pushState(new CharacterState("jab", .1f));
	}

	@Override
	public void tilt() 
	{
		CharacterState tiltBeginning = new CharacterState("tilt_dash", 0.4f) {};
		
		CharacterState tiltDash = new CharacterState("tilt_dash", 0.1f)
		{
			Hitbox m_hitbox = new Hitbox();
			Rectangle m_rect;
			BodyFixture m_fixture;
			
			@Override
			public void init()
			{
				m_hitbox.setDuration(0.1f);
				m_hitbox.setDamage(7);
				m_hitbox.setHitstun(0.5f);
				m_hitbox.setBaseKnockback(new Vector2(getFacing(), 0));
				m_hitbox.setScaledKnockback(new Vector2(5 * getFacing(), -1));
				
				m_rect = new Rectangle(4, 1); //reaalllly big hitbox
				m_rect.translate(3 * getFacing(), 1.5);
				
				m_fixture = new BodyFixture(m_rect);
				
				addHitbox(m_hitbox);
				m_hitbox.addToFixture(m_fixture);
				m_body.addFixture(m_fixture);
				m_body.setLinearDamping(20);
			}
			
			@Override
			public void end()
			{
				m_body.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				m_body.setLinearDamping(0);
			}
		};
				
		CharacterState tiltEnd = new CharacterState("tilt_end", 0.5f)
				{
					@Override
					public void init()
					{
						m_body.translate(6 * getFacing(), 0);
						m_body.setGravityScale(1);
						m_body.setAsleep(false);
						m_body.setLinearDamping(42);
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
		pushState(new SmashState());
		pushState(new CharacterState("smash", .1f));
	}

	@Override
	public void projectile()
	{
		m_sprite.setAnimation("projectile");
	}

	@Override
	public void signature()
	{
		// TODO: brooding fog sprites
		m_sprite.setAnimation("signature");
	}
	
	private class RecoveryState extends CharacterState
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
		CharacterState recoveryStart = new CharacterState("recovery", 0.5f)
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
						//nerfed back a bit
						m_body.translate(0, -4);
						m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
					}
				};
				
		CharacterState recoveryEnd = new CharacterState("jump_dsc", 0.1f)
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
						m_hitbox.setScaledKnockback(new Vector2(10 * getFacing(), -10));
						
						m_rect = new Rectangle(2, 2);
						m_rect.translate(1, 1.25);
						
						m_fixture = new BodyFixture(m_rect);
						
						addHitbox(m_hitbox);
						m_hitbox.addToFixture(m_fixture);
						m_body.addFixture(m_fixture);
						
						Texture explosionTexture = new Texture();
						explosionTexture.openResource("resources/images/explosion");
						
						Sprite explosion = new Sprite(explosionTexture);
						explosion.setAnimation("explosion");
						
						AffineTransform explosionOffset = new AffineTransform();
						explosionOffset.translate(m_body.getLocalCenter().x, m_body.getLocalCenter().y);
						//somehow draw this
						
						//m_body.setLinearDamping(0);
						float offset = -1.5f;
						explosionOffset.translate(m_body.getWorldCenter().x + offset, m_body.getWorldCenter().y + offset);
						explosionOffset.scale(1.5, 1.5);
						
						CharacterEffect ExplosionEffect = new CharacterEffect(explosion, explosionOffset);
						
						addEffect(ExplosionEffect);
						
						m_body.setLinearDamping(40);
					}
					
					@Override
					public void end()
					{
						m_body.applyImpulse(new Vector2(0, -2));
						m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						m_body.setLinearDamping(0);
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
