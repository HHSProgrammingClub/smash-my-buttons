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
		private Projectile duffelBag;
		private Hitbox duffelBox = new Hitbox();
		private Rectangle duffelRect;
		private BodyFixture duffelFix;
		private Body duffelBody = new Body();
		
		//just for fun
		private int equipCount = (int)(Math.random()*5 + 1);
		private Projectile[] m_equipment = new Projectile[equipCount];
		private Hitbox[] equipBoxes = new Hitbox[equipCount];
		private Rectangle[] equipRects = new Rectangle[equipCount];
		private BodyFixture[] equipFixes = new BodyFixture[equipCount];
		private Body[] equipBodies = new Body[equipCount];
		
		public ProjState()
		{
			super("projectile");
			
			//Cam throws the duffel bag and random equipment
			Texture duffelTexture = new Texture();
			duffelTexture.openResource("resources/images/duffelbag");
			Sprite duffelSprite = new Sprite(duffelTexture);
			duffelSprite.setAnimation("default");
			
			Texture equipTex1 = new Texture();
			equipTex1.openResource("resources/images/equipment1");
			Sprite equipSprite1 = new Sprite(equipTex1, "default");
			
			Texture equipTex2 = new Texture();
			equipTex2.openResource("resources/images/equipment2");
			Sprite equipSprite2 = new Sprite(equipTex2, "default");
			
			Texture equipTex3 = new Texture();
			equipTex3.openResource("resources/images/equipment3");
			Sprite equipSprite3 = new Sprite(equipTex2, "default");
			
			//initialize the random equipment
			for(int i = 0; i < equipCount; i++)
			{
				equipBoxes[i] = new Hitbox();
				equipBoxes[i].setDuration(2.0f);
				equipBoxes[i].setDamage(2);
				equipBoxes[i].setHitstun(0.1f);
				equipBoxes[i].setBaseKnockback(new Vector2(2 * getFacing(), 0));
				equipBoxes[i].setScaledKnockback(new Vector2(getFacing(), 0));
				
				int RNG = (int)(Math.random()*3 + 1);
				switch(RNG)
				{
					case 1:
						m_equipment[i] = new Projectile(equipSprite1, equipBoxes[i]);
						equipRects[i] = new Rectangle(0.5, 0.5);
						break;
					case 2:
						m_equipment[i] = new Projectile(equipSprite2, equipBoxes[i]);
						equipRects[i] = new Rectangle(0.5, 1);
						break;
					case 3:
						m_equipment[i] = new Projectile(equipSprite3, equipBoxes[i]);
						equipRects[i] = new Rectangle(0.5, 0.5);
						break;
				}
				equipRects[i].translate(0, 0);
				m_equipment[i].setCharacter((Character) m_body.getUserData());
				equipFixes[i] = new BodyFixture(equipRects[i]);
				
				Transform t = new Transform();
				t.translate(m_body.getTransform().getTranslation());
				t.translate(1.5, 1);
				
				equipBodies[i] = new Body();
				equipBodies[i].setTransform(t);
				equipBodies[i].addFixture(equipFixes[i]);
				equipBodies[i].setMass(MassType.NORMAL);
				
				addProjectile(m_equipment[i]);
			}
			
			duffelBox.setDuration(2.0f);
			duffelBox.setDamage(6);
			duffelBox.setHitstun(0.3f);
			duffelBox.setBaseKnockback(new Vector2(2 * getFacing(), 0));
			duffelBox.setScaledKnockback(new Vector2(getFacing(), 0));
			
			duffelRect = new Rectangle(0.5, 0.5);
			duffelRect.translate(0, 0);
			
			duffelBag = new Projectile(duffelSprite, duffelBox);
			duffelBag.setCharacter((Character) m_body.getUserData());
			
			duffelFix = new BodyFixture(duffelRect);
			
			Transform t = new Transform();
			t.translate(m_body.getTransform().getTranslation());
			t.translate(1, 1);
			
			duffelBody.setTransform(t);
			duffelBody.addFixture(duffelFix);
			duffelBody.setMass(MassType.NORMAL);
			
			addProjectile(duffelBag);
			
		}
		
		protected void init()
		{
			for(int i = 0; i < equipCount; i++)
			{
				addHitbox(equipBoxes[i]);
				equipBoxes[i].addToFixture(equipFixes[i]);
				equipFixes[i].setSensor(false);
				m_equipment[i].setBody(equipBodies[i]);
				float RNG = (float)(Math.random() * 2 + 1);
				equipBodies[i].applyImpulse(new Vector2(RNG * getFacing(), -RNG));
				equipBodies[i].applyTorque(RNG + 1);
				m_world.addBody(equipBodies[i]);
			}
			
			addHitbox(duffelBox);
			duffelBox.addToFixture(duffelFix);
			duffelFix.setSensor(false);
			duffelBag.setBody(duffelBody);
			duffelBody.applyImpulse(new Vector2(2 * getFacing(), -2));
			duffelBody.applyTorque(1.5);
			m_world.addBody(duffelBody);
		}
		
		protected void onUpdate()
		{
			for(int i = 0; i < equipCount; i++)
			{
				if(!equipBoxes[i].isAlive())
				{
					equipBodies[i].removeFixture(equipFixes[i]);
					removeHitbox(equipBoxes[i]);
					equipBodies[i].removeAllFixtures();
					m_world.removeBody(equipBodies[i]);
					removeProjectile(m_equipment[i]);
				}
			}
			
			if(!duffelBox.isAlive())
			{
				duffelBody.removeFixture(duffelFix);
				removeHitbox(duffelBox);
				duffelBody.removeAllFixtures();
				m_world.removeBody(duffelBody);
				removeProjectile(duffelBag);
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
		pushState(new WaitState(0.2f));
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
