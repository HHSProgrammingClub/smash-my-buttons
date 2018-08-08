package characters;

import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import characters.characterStates.CharacterState;
import characters.characterStates.IdleState;
import characters.characterStates.WaitState;
import graphics.Sprite;
import graphics.Texture;
import program.CharacterEffect;
import program.Hitbox;
import program.Projectile;

public class Cam extends Character
{
	private static double position = 0;
	private float length = 1;
	private float height = 1.5f;
	
	public Cam() 
	{
		//attribute editing
		jumpImpulse = new Vector2(0, -15);
		runForce = new Vector2(30, 0);
		maxRunSpeed = 4.5f;
		
		Body nikon = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		nikon.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(length, height);
		rect.translate(1, 1.25); // Set to topleft
		BodyFixture bf = new BodyFixture(rect);
		bf.setDensity(0.85);
		nikon.addFixture(bf);
		nikon.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(nikon);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/cam");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	private class JabState extends CharacterState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		private CharacterEffect flashEffect;
		
		JabState()
		{
			super("jab");
			
			m_hitbox.setDuration(0.05f);
			m_hitbox.setDamage(1);
			m_hitbox.setHitstun(0.4f);
			m_hitbox.setBaseKnockback(new Vector2(0, 0));
			m_hitbox.setScaledKnockback(new Vector2(0.5 * getFacing(), 0));
			
			m_body.setLinearVelocity(0, 0);
			
			m_rect = new Rectangle(1, 1);
			m_rect.translate(length + 0.75 * getFacing(), 1.25);
			
			m_fixture = new BodyFixture(m_rect);
			
			AffineTransform flashOffset = new AffineTransform();
			flashOffset.translate(m_body.getWorldCenter().x + (1.6*getFacing()), m_body.getWorldCenter().y - 0.7);
		
			flashEffect = new CharacterEffect("flash", "default", flashOffset);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_body.addFixture(m_fixture);
			addEffect(flashEffect);
			
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
			removeEffect(flashEffect);
		}
		
		
	};
	private class TiltState extends CharacterState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		TiltState()
		{
			super("tilt");
			
			m_hitbox.setDuration(0.1f);
			m_hitbox.setDamage(3);
			m_hitbox.setHitstun(0.5f);
			m_hitbox.setBaseKnockback(new Vector2(5 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(4 * getFacing(), -2.5));
			
			m_rect = new Rectangle(0.9, 0.3);
			m_rect.translate(length + 0.7 * getFacing(), 1.4);
			
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
	
	private class ProjState extends CharacterState
	{
		private Projectile coffee;
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		private Body m_bodied = new Body();
		
		ProjState()
		{
			super("projectile");
			
			Texture explosionTexture = new Texture();
			explosionTexture.openResource("resources/images/coffee");
			
			Sprite explosion = new Sprite(explosionTexture);
			explosion.setAnimation("default");
			
			m_hitbox.setDuration(2f);
			m_hitbox.setDamage(6);
			m_hitbox.setHitstun(0.3f);
			m_hitbox.setBaseKnockback(new Vector2(2 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(1 * getFacing(), 0));
			
			m_rect = new Rectangle(0.5, 0.5);
			m_rect.translate(0, 0);
			coffee = new Projectile(explosion, m_hitbox);
			coffee.setCharacter((Character) m_body.getUserData());
			m_fixture = new BodyFixture(m_rect);
			Transform t = new Transform();
			t.translate(m_body.getTransform().getTranslation());
			t.translate(1, 1);
			m_bodied.setTransform(t);
			m_bodied.addFixture(m_fixture);
			m_bodied.setMass(MassType.NORMAL);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_fixture.setSensor(false);
			coffee.setBody(m_bodied);
			m_bodied.applyImpulse(new Vector2(2 * getFacing(), -2));
			m_bodied.applyTorque(3);
			m_world.addBody(m_bodied);
		}
		
		protected void onUpdate()
		{
			if(!m_hitbox.isAlive()) {
				m_bodied.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				m_bodied.removeAllFixtures();
				m_world.removeBody(m_bodied);
			}
		}
	};
	
	private class SignatureState extends CharacterState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		SignatureState()
		{
			super("signature");
			//setDuration(1f);
			m_hitbox.setDuration(0.2f);
			m_hitbox.setDamage(5);
			m_hitbox.setHitstun(0);
			m_hitbox.setBaseKnockback(new Vector2(0, 0));
			m_hitbox.setScaledKnockback(new Vector2(0, 0));
			
			m_rect = new Rectangle(100, 100);
			m_rect.translate(0, 0);
			
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
	
	public void jab()
	{
		/*interruptStates(new CharacterState("jab", 0.1f));
		addState(new JabState());*/
		pushState(new JabState());
		pushState(new CharacterState("jab", .1f));
		//System.out.println(getDamage());
	}
	
	public void tilt()
	{
		/*interruptStates(new CharacterState("tilt", 0.1f));
		addState(new TiltState());
		addState(new CharacterState("idle", 0.3f));*/
		pushState(new WaitState(.1f));
		pushState(new TiltState());
		pushState(new CharacterState("tilt", .2f));
	}
	
	public void smash()
	{
		CharacterState smashStartup = new CharacterState("smash_startup", 1.0f)
				{
					@Override
					public void interrupt()
					{
						//needs something here
						pushState(new IdleState());
					}
				};
				
		CharacterState smashFlash = new CharacterState("smash_flash", 0.5f)
				{
					private Hitbox m_hitbox = new Hitbox();
					private Rectangle m_rect;
					private BodyFixture m_fixture;
					private CharacterEffect bigFlashEffect;
					
					@Override
					public void init()
					{
						m_hitbox.setDuration(0.2f);
						m_hitbox.setDamage(10);
						m_hitbox.setHitstun(0.75f);
						m_hitbox.setBaseKnockback(new Vector2(3 * getFacing(), 0));
						m_hitbox.setScaledKnockback(new Vector2(5 * getFacing(), -5));
						
						m_body.setLinearVelocity(0, 0);
						
						m_rect = new Rectangle(2, 1);
						m_rect.translate(length + 1 * getFacing(), 1.25);
						
						m_fixture = new BodyFixture(m_rect);
						
						addHitbox(m_hitbox);
						m_hitbox.addToFixture(m_fixture);
						m_body.addFixture(m_fixture);
						
						AffineTransform flashOffset = new AffineTransform();
						flashOffset.translate(m_body.getWorldCenter().x + (2.6*getFacing()), m_body.getWorldCenter().y - 1);
					
						bigFlashEffect = new CharacterEffect("big_flash", "default", flashOffset);
						
						addEffect(bigFlashEffect);
					}
					
					@Override
					public void interrupt()
					{
						m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
					}
					
					@Override
					public void end()
					{
						m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						removeEffect(bigFlashEffect);
					}
				};
		pushState(smashFlash);
		pushState(smashStartup);
	}
	
	public void projectile()
	{
		//Placeholder for testing.
		/*interruptStates(new CharacterState("projectile", 0.05f));
		addState(new ProjState());*/
		pushState(new WaitState(0.4f));
		pushState(new ProjState());
		pushState(new CharacterState("projectile", .1f));
	}
	
	public void signature()
	{
		/*interruptStates(new CharacterState("signature", 0.5f));
		addState(new SignatureState());*/
		pushState(new SignatureState());
		//pushState(new CharacterState("signature", .5f));
	}
	
	public void recover()
	{
		CharacterState recoveryStart = new CharacterState("recovery", 0.3f)
				{
					@Override
					public void init()
					{
						m_body.setLinearVelocity(0, 0);
					}
				};
				
		CharacterState recoveryBomb = new CharacterState("jump_asc", 0.3f)
				{
					private Hitbox m_hitbox = new Hitbox();
					private Rectangle m_rect;
					private BodyFixture m_fixture;
					private CharacterEffect explosion;
					
					@Override
					public void init()
					{
						//setDuration(1f);
						m_hitbox.setDuration(1f);
						m_hitbox.setDamage(2);
						m_hitbox.setHitstun(0.4f);
						m_hitbox.setBaseKnockback(new Vector2(0, -2));
						m_hitbox.setScaledKnockback(new Vector2(0, -2));
						
						m_rect = new Rectangle(1.2, 0.2);
						m_rect.translate(length - 0.3 * getFacing(), 0.15);
						
						m_fixture = new BodyFixture(m_rect);
						getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);
						getBody().applyImpulse(new Vector2(0, -9));
					
						addHitbox(m_hitbox);
						m_hitbox.addToFixture(m_fixture);
						m_body.addFixture(m_fixture);
						getBody().setGravityScale(0);
					
						AffineTransform explosionOffset = new AffineTransform();
						explosionOffset.translate(m_body.getWorldCenter().x+ (getFacing()), m_body.getWorldCenter().y-2);
						explosionOffset.scale(1.5, 1.5);
						
						explosion = new CharacterEffect("explosion", "explosion", explosionOffset);
						
						addEffect(explosion);
					}
					
					@Override
					public void interrupt()
					{
						m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						getBody().setGravityScale(1);
					}
					
					@Override
					public void end()
					{
						m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						getBody().setGravityScale(1);
					}
				};
		
		
		if(!m_recovered)
		{
			pushState(recoveryBomb);
			pushState(recoveryStart);
			m_recovered = true;
		}
	}

	@Override
	public String getName()
	{
		return Character.characterNames[1];
	}

}
