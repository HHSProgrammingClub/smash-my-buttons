package characters;

import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import characters.characterStates.AttackState;
import characters.characterStates.Hitstun;
import characters.characterStates.IdleState;
import characters.characterStates.WaitState;
import graphics.Sprite;
import graphics.Texture;
import program.CharacterEffect;
import program.Hitbox;
import program.Projectile;
import resourceManager.ResourceManager;

public class Cam extends Character
{
	private static double position = 0;
	private float length = 1;
	private float height = 1.5f;
	
	private double Kbooster = 1; //Knockback booster
	private float Sbooster = 1; //Speed booster
	
	@Override
	public double getKbooster() { return Kbooster; }
	
	private void resetBoosts() {
		Kbooster = 1;
		Sbooster = 1;
		runForce = new Vector2(30, 0);
		maxRunSpeed = 4.5f;
	}
	
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
		
		Texture tex = ResourceManager.getResource(Texture.class, "resources/images/cam");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	private class JabState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		private CharacterEffect flashEffect;
		
		JabState()
		{
			super("jab");
			setDuration(0.2f);
			m_hitbox.setDuration(0.05f);
			m_hitbox.setDamage(1);
			m_hitbox.setHitstun(0.1f);
			m_hitbox.setBaseKnockback(new Vector2(0, 0));
			m_hitbox.setScaledKnockback(new Vector2(0.5 * getFacing(), 0));
			
			m_body.setLinearVelocity(0, 0);
			
			m_rect = new Rectangle(1.3, 1);
			m_rect.translate(length + 0.8 * getFacing(), 1.25);
			
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
			resetBoosts();
		}
		
		
	};
	private class TiltState extends AttackState
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
			m_hitbox.setBaseKnockback(alignFacing(new Vector2(9, 0)).multiply(Kbooster));
			m_hitbox.setScaledKnockback(alignFacing(new Vector2(2, -2.5)).multiply(Kbooster));
			
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
			resetBoosts();
		}
		
		
	};
	
	private class ProjState extends AttackState
	{
		private Projectile duffelBag;
		private Hitbox duffelBox = new Hitbox();
		private Rectangle duffelRect;
		private BodyFixture duffelFix;
		private Body duffelBody = new Body();
		
		//just for fun
		private int equipCount = (int)(Math.random()*2 + 1);
		private Projectile[] m_equipment = new Projectile[equipCount];
		private Hitbox[] equipBoxes = new Hitbox[equipCount];
		private Rectangle[] equipRects = new Rectangle[equipCount];
		private BodyFixture[] equipFixes = new BodyFixture[equipCount];
		private Body[] equipBodies = new Body[equipCount];
		
		public ProjState()
		{
			super("projectile");
			
			//Cam throws the duffel bag and random equipment
			Texture duffelTexture = ResourceManager.getResource(Texture.class, "resources/images/duffelbag");
			Sprite duffelSprite = new Sprite(duffelTexture);
			duffelSprite.setAnimation("default");
			
			Texture equipTex1 = ResourceManager.getResource(Texture.class, "resources/images/equipment1");
			Sprite equipSprite1 = new Sprite(equipTex1, "default");
			
			Texture equipTex2 = ResourceManager.getResource(Texture.class, "resources/images/equipment2");
			Sprite equipSprite2 = new Sprite(equipTex2, "default");
			
			Texture equipTex3 = ResourceManager.getResource(Texture.class, "resources/images/equipment3");
			Sprite equipSprite3 = new Sprite(equipTex3, "default");
			
			//initialize the random equipment
			for(int i = 0; i < equipCount; i++)
			{
				equipBoxes[i] = new Hitbox();
				equipBoxes[i].setDuration(2.0f);
				equipBoxes[i].setDamage(2);
				equipBoxes[i].setHitstun(0f);
				equipBoxes[i].setBaseKnockback(alignFacing(new Vector2(2, 0)));
				equipBoxes[i].setScaledKnockback(alignFacing(new Vector2(1, 0)));
				
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
			duffelBox.setDamage(3);
			duffelBox.setHitstun(0.05f);
			duffelBox.setBaseKnockback(alignFacing(new Vector2(2, 0)));
			duffelBox.setScaledKnockback(alignFacing(new Vector2(1, 0)));
			
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
				float RNG = (float)(Math.random() * 2 + 2);
				// RNGzus = (float) (Math.random() * 3);
				equipBodies[i].applyImpulse(alignFacing(new Vector2((i + 1), -(equipCount - i))));
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
		
		protected void onUpdate(float p_delta)
		{
			
		}
		
		@Override
		public void end()
		{
			resetBoosts();
		}
	};
	
	private class SignatureState extends AttackState
	{
		
		SignatureState()
		{
			super("signature");
			setDuration(1f);
		}
		
		@Override
		public void end()
		{
			Kbooster = 1.75;
			Sbooster = 0.2f;
			jumpImpulse = new Vector2(0, -20);
			runForce = new Vector2(100, 0);
			maxRunSpeed = 10f;
		}
		
		
	};
	
	public void jab()
	{
		/*interruptStates(new AttackState("jab", 0.1f));
		addState(new JabState());*/
		pushState(new JabState());
		pushState(new AttackState("jab", .1f * Sbooster));
		//System.out.println(getDamage());
	}
	
	public void tilt()
	{
		/*interruptStates(new AttackState("tilt", 0.1f));
		addState(new TiltState());
		addState(new AttackState("idle", 0.3f));*/
		pushState(new WaitState(.1f * Sbooster));
		pushState(new TiltState());
		pushState(new AttackState("tilt", .3f * Sbooster));
	}
	
	public void smash()
	{
		AttackState smashStartup = new AttackState("smash_startup", 1.0f * Sbooster)
				{
					@Override
					public void init()
					{
						m_superArmour = true;
					}
					
					@Override
					public void interrupt()
					{
						m_superArmour = false;
					}
					
					@Override
					public void end()
					{
						m_superArmour = false;
					}
				};
				
		AttackState smashFlash = new AttackState("smash_flash", 0.5f * Sbooster)
				{
					private Hitbox m_hitbox = new Hitbox();
					private Rectangle m_rect;
					private BodyFixture m_fixture;
					private CharacterEffect bigFlashEffect;
					
					@Override
					public void init()
					{
						m_hitbox.setDuration(0.2f);
						m_hitbox.setDamage(20);
						m_hitbox.setHitstun(1.2f);
						m_hitbox.setBaseKnockback(new Vector2(0 * getFacing(), 0));
						m_hitbox.setScaledKnockback(new Vector2(0 * getFacing(), -0));
						
						m_body.setLinearVelocity(0, 0);
						
						m_rect = new Rectangle(2.3, 1);
						m_rect.translate(length + 1.25 * getFacing(), 1);
						
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
						if(m_fixture != null)
							m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
					}
					
					@Override
					public void end()
					{
						if(m_fixture != null)
							m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						removeEffect(bigFlashEffect);
						resetBoosts();
					}
				};
		pushState(smashFlash);
		pushState(smashStartup);
	}
	
	public void projectile()
	{
		//Placeholder for testing.
		/*interruptStates(new AttackState("projectile", 0.05f));
		addState(new ProjState());*/
		pushState(new WaitState(0.4f * Sbooster));
		pushState(new ProjState());
		pushState(new WaitState(0.2f * Sbooster));
	}
	
	public void signature()
	{
		/*interruptStates(new AttackState("signature", 0.5f));
		addState(new SignatureState());*/
		pushState(new SignatureState());
		//pushState(new AttackState("signature", .5f));
	}
	
	public void recover()
	{
		AttackState recoveryStart = new AttackState("recovery", 0.3f)
				{
					
				};
				
		AttackState recoveryBomb = new AttackState("jump_asc", 0.3f)
				{
					private Hitbox m_hitbox = new Hitbox();
					private Rectangle m_rect;
					private BodyFixture m_fixture;
					private CharacterEffect explosion;
					
					@Override
					public void init()
					{
						//setDuration(1f);
						m_hitbox.setDuration(0.1f);
						m_hitbox.setDamage(22);
						m_hitbox.setHitstun(0.2f);
						m_hitbox.setBaseKnockback(alignFacing(new Vector2(8, -6)).multiply(Kbooster));
						m_hitbox.setScaledKnockback(alignFacing(new Vector2(8, -6)).multiply(Kbooster));
						
						m_rect = new Rectangle(1.8, 1.8);
						m_rect.translate(1, 1);
						
						m_fixture = new BodyFixture(m_rect);
						getBody().setLinearVelocity(getBody().getLinearVelocity().x, 0);
						getBody().applyImpulse(alignFacing(new Vector2(10, -23)));
					
						addHitbox(m_hitbox);
						m_hitbox.addToFixture(m_fixture);
						m_body.addFixture(m_fixture);
					
						AffineTransform explosionOffset = new AffineTransform();
						explosionOffset.translate(m_body.getWorldCenter().x+ (getFacing()), m_body.getWorldCenter().y-2);
						explosionOffset.scale(1.5, 1.5);
						
						explosion = new CharacterEffect("explosion", "explosion", explosionOffset);
						
						addEffect(explosion);
						setDamage(getDamage() + 18);
						pushState(new Hitstun(0.5f));
					}
					
					@Override
					public void interrupt()
					{
						if(m_fixture != null)
							m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
					}
					
					@Override
					public void end()
					{
						if(m_fixture != null)
							m_body.removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						resetBoosts();
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
		return Character.characterNames[2];
	}

}
