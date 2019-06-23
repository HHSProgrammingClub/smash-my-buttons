package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.joint.WeldJoint;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import characters.characterStates.AttackState;
import characters.characterStates.IdleState;
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
		jumpImpulse = new Vector2(0, -30);
		runForce = new Vector2(60, 0);
		maxRunSpeed = 2.5f;
		
		Body swol = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		swol.setTransform(t);
		position += 1;
		
		Rectangle getRekt = new Rectangle(length, height);
		getRekt.translate(1, 1);
		BodyFixture bf = new BodyFixture(getRekt);
		bf.setDensity(1.25);
		swol.addFixture(bf);
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
			m_hitbox.setDamage(3);
			m_hitbox.setHitstun(0f);
			m_hitbox.setBaseKnockback(new Vector2(14 * getFacing(), -1));
			m_hitbox.setScaledKnockback(new Vector2(2 * getFacing(), 0));
			
			m_rect = new Rectangle(1, 0.5);
			m_rect.translate(length + 0.5 * getFacing(), 1);
			
			m_fixture = new BodyFixture(m_rect);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_body.addFixture(m_fixture);
			m_superArmour = true;
		}
		
		public void interrupt()
		{
			m_body.removeFixture(m_fixture);
			removeHitbox(m_hitbox);
			m_superArmour = false;
		}
		
		public void end()
		{
			m_body.removeFixture(m_fixture);
			removeHitbox(m_hitbox);
			m_superArmour = false;
		}
	}
	
	@Override
	public void jab() 
	{
		pushState(new JabState());
		pushState(new AttackState("jab", 0.12f));
	}
	
	private class TiltState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public TiltState()
		{
			super("tilt");
			setDuration(0.4f);
			m_hitbox.setDuration(0.01f);
			m_hitbox.setDamage(5);
			m_hitbox.setHitstun(0.5f);
			m_hitbox.setBaseKnockback(new Vector2(-7 * getFacing(), -20));
			m_hitbox.setScaledKnockback(new Vector2(-0.5 * getFacing(), -1));
			
			m_rect = new Rectangle(0.5, 1);
			m_rect.translate(length + 0.75 * getFacing(), 1.5);
			
			m_fixture = new BodyFixture(m_rect);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_body.addFixture(m_fixture);
			m_superArmour = true;
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
			m_superArmour = false;
		}
	}

	@Override
	public void tilt() 
	{
		pushState(new TiltState());
		pushState(new AttackState("tilt", 0.1f));
		pushState(new WaitState(0.05f));
	}
	
	private class SmashState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		
		public SmashState()
		{
			super("smash");
			setDuration(0.6f);
			m_hitbox.setDamage(8);
			m_hitbox.setBaseKnockback(new Vector2(-10 * getFacing(), -5));
			m_hitbox.setScaledKnockback(new Vector2(-9 * getFacing(), -5));
			m_hitbox.setDuration(0.5f);
			m_hitbox.setHitstun(0.3f);
			
			m_rect = new Rectangle(1, 1.6);
			m_rect.translate(length - 0.3 * getFacing(), 0.75);
			
			m_fixture = new BodyFixture(m_rect);
		}
		
		protected void init()
		{
			m_body.applyImpulse(new Vector2(-5 * getFacing(), 0));
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
		pushState(new WaitState(0.6f));
		pushState(new SmashState());
		pushState(new WaitState(0.1f));
	}
	
	private class ProjectileState extends AttackState
	{
		private Body chairBody;
		
		public ProjectileState()
		{
			super("signature_slam");
			setDuration(0.3f);
		}
		
		private void initChair()
		{
			Texture texMex = ResourceManager.getResource(Texture.class, "resources/images/chair");
			
			Sprite chairemAnime = new Sprite(texMex);
			chairemAnime.setAnimation("default");
			
			Hitbox chairHitbox = new Hitbox();
			chairHitbox.setDuration(3.0f);
			chairHitbox.setDamage(7);
			chairHitbox.setHitstun(0.2f);
			chairHitbox.setBaseKnockback(alignFacing(new Vector2(1, 5)));
			chairHitbox.setScaledKnockback(alignFacing(new Vector2(2, 6)));
			
			Rectangle chairRect = new Rectangle(1, 1.3);
			chairRect.translate(1, 1.1);
			
			BodyFixture chairFixture = new BodyFixture(chairRect);
			
			addHitbox(chairHitbox);
			chairHitbox.addToFixture(chairFixture);
			chairFixture.setSensor(false);
			
			chairBody = new Body();
			
			Transform t = new Transform();
			t.translate(m_body.getTransform().getTranslation());
			t.translate(alignFacing(new Vector2(0, -1.5)));
			
			chairBody.addFixture(chairFixture);
			chairBody.setMass(MassType.NORMAL);
			chairBody.setTransform(t);
			
			Projectile chair = new Projectile(chairemAnime, chairHitbox)
					{
						public boolean update(float p_delta)
						{
							if(m_duration < 0 || !((Hitbox)m_body.getFixture(0).getUserData()).isAlive())
								return true;
							m_timer -= p_delta;
							return m_timer > 0;
						}
					};
			chair.setCharacter((Character) m_body.getUserData());
			
			chair.setBody(chairBody);
			
			addProjectile(chair);
			m_world.addBody(chairBody);
			
			chairBody.setGravityScale(0);
		}
		
		@Override
		protected void init()
		{
			initChair();
			chairBody.setGravityScale(1);
			chairBody.applyImpulse(alignFacing(new Vector2(6, 15)));
			chairBody.applyTorque(120);
		}
	}

	@Override
	public void projectile() 
	{
		//someone time this correctly
		pushState(new WaitState(0.2f));
		pushState(new ProjectileState());
		pushState(new AttackState("signature_slam", 0.1f));
	}

	WeldJoint hold;
	
	@Override
	public void signature()
	{
		
		AttackState suplexSlam = new AttackState("signature_slam", -1f)
		{
			@Override
			public void interrupt() 
			{
				m_world.removeJoint(hold);
			}
			
			@Override
			public void end()
			{
				Hitbox welcomeToTheJam = new Hitbox();
				welcomeToTheJam.setDamage(3);
				welcomeToTheJam.setBaseKnockback(alignFacing(new Vector2(10, -6)));
				welcomeToTheJam.setScaledKnockback(alignFacing(new Vector2(3, -4)));
				welcomeToTheJam.setHitstun(0.3f);
				
				
				m_opponent.takeHit(welcomeToTheJam);
				
				m_world.removeJoint(hold);
			}
			
			@Override
			protected void onUpdate(float p_delta)
			{
				if(m_body.getLinearVelocity().y == 0)
					endState();
				if(m_opponent.getDamage() == 0) {
					m_world.removeJoint(hold);
				}
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
			public void interrupt()
			{
				m_body.setLinearVelocity(0, 0);
				m_world.removeJoint(hold);
			}
		};
		
		float dashDuration = 0.3f;
		
		AttackState suplexDash = new AttackState("signature_dash", dashDuration)
		{
			Hitbox m_hitbox = new Hitbox();
			Rectangle m_rect = new Rectangle(0.5, 0.75);
			BodyFixture m_fixture;
			
			@Override
			public void init()
			{
				m_body.applyImpulse(alignFacing(new Vector2(30, 0)));
				
				m_hitbox.setDamage(0);
				m_hitbox.setBaseKnockback(new Vector2(0, 0));
				m_hitbox.setScaledKnockback(new Vector2(0, 0));
				m_hitbox.setDuration(dashDuration);
				
				m_rect.translate(length + 0 * getFacing(), 1.5);
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
				//pushState(new AttackState("signature_dash", cooldown));
			}
			
			@Override
			protected void onUpdate(float p_delta)
			{
				if(!m_hitbox.isAlive() && getTimer() > 0)
				{	
					Vector2 pos = m_body.getTransform().getTranslation();
					Transform opT = m_opponent.getBody().getTransform();
					opT.setTranslation(pos.add(alignFacing(new Vector2(0.5, 0))));
					
					m_opponent.getBody().setTransform(opT);
					
					hold = new WeldJoint(m_body, m_opponent.getBody(), new Vector2());
					m_world.addJoint(hold);
					
					popState();
					pushState(suplexSlam);
					pushState(new WaitState(0.01f));
					pushState(suplexThrow);
				}
			}
		};
		pushState(new WaitState(0.2f));
		pushState(suplexDash);
		pushState(new WaitState(0.2f));
	}

	@Override
	public void recover()
	{
		AttackState recoveryJump = new AttackState("recovery_jump", 0.4f)
		{
			@Override
			public void init()
			{
				m_body.setLinearVelocity(0, 0);
				m_body.applyImpulse(new Vector2(2*getFacing(), -45));
				m_recovered = true;
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
				m_superArmour = true;
			}
			
			@Override
			protected void onUpdate(float p_delta)
			{
				m_body.applyImpulse(new Vector2(4 * getFacing(), 0));
				m_superArmour = true;
			}
		};
		
		AttackState recoverySlam = new AttackState("recovery_slam", -1f)
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
				m_hitbox.setDamage(8);
				m_hitbox.setHitstun(0.6f);
				m_hitbox.setBaseKnockback(new Vector2(getFacing(), 7));
				m_hitbox.setScaledKnockback(new Vector2(getFacing(), 7));
			
				m_rect = new Rectangle(1.4, 0.4);
				m_rect.translate(1, 2);
				
				m_fixture = new BodyFixture(m_rect);
				
				addHitbox(m_hitbox);
				m_hitbox.addToFixture(m_fixture);
				m_body.addFixture(m_fixture);

				m_body.applyImpulse(new Vector2(getFacing(), -15));
			}
			
			private void bounce()
			{
				m_body.setLinearVelocity(0, -10);
				endState();
			}
			
			@Override
			protected void onUpdate(float p_delta)
			{
				if(m_body.getLinearVelocity().y == 0 || getTimer() >= 0.3)
					endState();
				
				else if(!m_hitbox.isAlive())
					bounce();
			}
			
			@Override
			public void end()
			{
				if(m_fixture != null)
					m_body.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				m_superArmour = false;
			}
			
			@Override
			public boolean handleAction(int p_action)
			{
				if(p_action == Character.ACTION_JUMP)
				{
					m_body.setLinearVelocity(new Vector2(0, -5));
					endState();
					peekState().handleAction(p_action);
				}
				return false;
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
