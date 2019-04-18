package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import characters.characterStates.*;
import graphics.Sprite;
import graphics.Texture;
import program.Hitbox;
import program.Projectile;
import resourceManager.ResourceManager;

//A sample character.

public class STEAMO extends Character
{	
	private static double position = 0;
	private float length = 1.8f;
	private float height = 2.3f;
	private int boostsLeft = 0;
	public STEAMO() 
	{
		//attribute editing
		jumpImpulse = new Vector2(0, -40);
		runForce = new Vector2(35, 0);
		maxRunSpeed = 3.5f;
		
		Body tushie = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		tushie.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(length, height);
		rect.translate(1, 1.25); // Set to topleft
		tushie.addFixture(rect);
		tushie.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(tushie);
		
		Texture tex = ResourceManager.getResource(Texture.class, "resources/images/jack");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	//TODO: ascending and descending jump sprites for Jack
	
	private class JabState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		JabState()
		{
			super("jab");
			
			m_hitbox.setDuration(0.2f);
			m_hitbox.setDamage(7);
			m_hitbox.setHitstun(0.4f);
			m_hitbox.setBaseKnockback(new Vector2(9 * getFacing(), -1));
			m_hitbox.setScaledKnockback(new Vector2(5 * getFacing(), -1));
			
			Vector2 position = new Vector2(1, 1.2);
			Vector2 dimensions = new Vector2(1, 1);
			m_rect = new Rectangle(dimensions.x, dimensions.y);
			m_rect.translate(flipBox(length, position));
			
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
	private class TiltState extends AttackState
	{
		private Projectile coffee;
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		private Body m_bodied = new Body();
		
		TiltState()
		{
			super("projectile");
			
			Texture explosionTexture = ResourceManager.getResource(Texture.class, "resources/images/coffee");
			
			Sprite explosion = new Sprite(explosionTexture);
			explosion.setAnimation("default");
			
			m_hitbox.setDuration(2f);
			m_hitbox.setDamage(3);
			m_hitbox.setHitstun(0);
			m_hitbox.setBaseKnockback(new Vector2(10 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(1.5 * getFacing(), 0));
			
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
			m_bodied.setGravityScale(0);
			
			addProjectile(coffee);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_fixture.setSensor(false);
			coffee.setBody(m_bodied);
			m_bodied.applyImpulse(new Vector2(2 * getFacing(), 0));
			m_world.addBody(m_bodied);
		}
		
		protected void onUpdate(float p_delta)
		{
			if(!m_hitbox.isAlive()) {
				m_bodied.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				m_bodied.removeAllFixtures();
				m_world.removeBody(m_bodied);
			}
		}
		
		
	};
	private class SmashState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		SmashState()
		{
			super("smash");
			
			m_hitbox.setDuration(0.3f);
			m_hitbox.setDamage(13);
			m_hitbox.setHitstun(0.9f);
			m_hitbox.setBaseKnockback(new Vector2(3 * getFacing(), -3));
			m_hitbox.setScaledKnockback(new Vector2(10 * getFacing(), -10));
			
			Vector2 position = new Vector2(1, 1.2);
			Vector2 dimensions = new Vector2(0.5, 2.2);
			m_rect = new Rectangle(dimensions.x, dimensions.y);
			m_rect.translate(flipBox(length, position));
			
			m_fixture = new BodyFixture(m_rect);
		}
		
		protected void init()
		{
			m_body.applyImpulse(new Vector2(22 * getFacing(), 0));
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
	
	private class ProjState extends AttackState
	{
		private Projectile coffee;
		private Hitbox m_hitbox = new Hitbox();
		private Rectangle m_rect;
		private BodyFixture m_fixture;
		private Body m_bodied = new Body();
		
		ProjState()
		{
			super("projectile");
			
			Texture explosionTexture = ResourceManager.getResource(Texture.class, "resources/images/coffee");
			
			Sprite explosion = new Sprite(explosionTexture);
			explosion.setAnimation("default");
			
			m_hitbox.setDuration(5f);
			m_hitbox.setDamage(7);
			m_hitbox.setHitstun(0.5f);
			m_hitbox.setBaseKnockback(new Vector2(2 * getFacing(), 0));
			m_hitbox.setScaledKnockback(new Vector2(1 * getFacing(), 0));
			
			m_rect = new Rectangle(1, 0.5);
			m_rect.translate(0, 0.5);
			coffee = new Projectile(explosion, m_hitbox);
			coffee.setCharacter((Character) m_body.getUserData());
			m_fixture = new BodyFixture(m_rect);
			Transform t = new Transform();
			t.translate(m_body.getTransform().getTranslation());
			t.translate(1, 1);
			m_bodied.setTransform(t);
			m_bodied.addFixture(m_fixture);
			m_bodied.setMass(MassType.NORMAL);
			m_bodied.setGravityScale(0);
			
			addProjectile(coffee);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_fixture.setSensor(false);
			coffee.setBody(m_bodied);
			m_bodied.applyImpulse(new Vector2(0, 0));
			m_world.addBody(m_bodied);
			m_superArmour = true;
		}
		
		protected void onUpdate(float p_delta)
		{
			m_bodied.applyImpulse(new Vector2(0.25 * getFacing(), 0));
			if(!m_hitbox.isAlive()) {
				m_bodied.removeFixture(m_fixture);
				removeHitbox(m_hitbox);
				m_bodied.removeAllFixtures();
				m_world.removeBody(m_bodied);
			}
		}
		
		@Override
		public void end()
		{
			m_superArmour = false;
		}
	};
	
	private class SignatureState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		SignatureState()
		{
			super("signature");
			//setDuration(1f);
			setDuration(0.5f);
		}
		
		protected void init()
		{
			
		}
		
		public void interrupt()
		{
			
		}
		
		public void end()
		{
			runForce = new Vector2(90, 0);
		}
		
		
	};
	
	private class RecoveryStart extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		RecoveryStart()
		{
			super("recovery");
			setDuration(0.1f);
		}
		
		protected void init()
		{
			m_body.setLinearDamping(40);
		}
		
		public void end() {
			m_body.setLinearDamping(0);
		}
	};
	
	private class RecoveryState extends AttackState
	{
		private Hitbox m_hitbox = new Hitbox();

		private Rectangle m_rect;
		
		private BodyFixture m_fixture;
		
		RecoveryState()
		{
			super("recovery");
			//setDuration(1f);
			m_hitbox.setDuration(1f);
			m_hitbox.setDamage(2);
			m_hitbox.setHitstun(0.4f);
			m_hitbox.setBaseKnockback(new Vector2(0, 18));
			m_hitbox.setScaledKnockback(new Vector2(0, 2));
			
			Vector2 position = new Vector2(0, 2.2);
			Vector2 dimensions = new Vector2(1, 0.2);
			m_rect = new Rectangle(dimensions.x, dimensions.y);
			m_rect.translate(flipBox(length, position));
			
			m_fixture = new BodyFixture(m_rect);
		}
		
		protected void init()
		{
			addHitbox(m_hitbox);
			m_hitbox.addToFixture(m_fixture);
			m_body.addFixture(m_fixture);
			m_body.setLinearVelocity(new Vector2(getFacing() * 3, 0));
			m_body.applyImpulse(new Vector2(0, -60));
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
		/*interruptStates(new AttackState("jab", 0.1f));
		addState(new JabState());*/
		pushState(new JabState());
		pushState(new AttackState("jab", .15f));
		//System.out.println(getDamage());
	}
	
	public void tilt()
	{
		/*interruptStates(new AttackState("tilt", 0.1f));
		addState(new TiltState());
		addState(new AttackState("idle", 0.3f));*/
		pushState(new TiltState());
		pushState(new AttackState("tilt", .1f));
		pushState(new WaitState(0.1f));
	}
	
	public void smash()
	{
		/*interruptStates(new AttackState("idle", 0.2f));
		addState(new AttackState("smash", 0.1f));
		addState(new SmashState());
		addState(new AttackState("idle", 0.3f));*/
		pushState(new WaitState(.4f));
		pushState(new SmashState());
		pushState(new AttackState("smash", .1f));
		pushState(new WaitState(.1f));
	}
	
	public void projectile()
	{
		//Placeholder for testing.
		/*interruptStates(new AttackState("projectile", 0.05f));
		addState(new ProjState());*/
		pushState(new WaitState(0.3f));
		pushState(new ProjState());
		pushState(new AttackState("projectile", .2f));
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
		if(!m_recovered)
		{
			/*interruptStates(new AttackState("idle", 0.05f));
			addState(new RecoveryState());*/
			pushState(new RecoveryState());
			pushState(new RecoveryStart());
			//pushState(new WaitState(.05f));
			m_recovered = true;
		}
	}
	
	@Override
	public void update(float p_delta) {
		if(isDead()) {
			runForce = new Vector2(35, 0);
		}
		super.update(p_delta);
	}

	@Override
	public String getName() 
	{
		return Character.characterNames[7];
	}
}
