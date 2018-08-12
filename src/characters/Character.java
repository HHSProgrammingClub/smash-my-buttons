package characters;

import java.util.ArrayList;
import java.util.Stack;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.DeathAnimation;
import graphics.Drawable;
import graphics.IntRect;
import graphics.Sprite;
import graphics.pages.Renderer;
import program.Hitbox;
import program.Projectile;
import program.CharacterEffect;
import characters.characterStates.*;

public abstract class Character implements Drawable
{
	protected Body m_body;
	protected Sprite m_sprite;

	private int m_damage;
	private int m_stock = 3;
	private String m_name = "George the Glass-Cutter";
	protected boolean m_jumped       = false;
	protected boolean m_recovered   = false;
	protected boolean m_superArmour = false;
	private boolean m_stunned       = false;
	private boolean m_attacking     = false;
	boolean m_facingRight   = false;
	//centered around x=6.25
	private final static float CENTER = 6.25f;
	private final static float DISTANCE_FROM_CENTER = 10;
	private final static float RIGHT_BLAST_LINE = CENTER + DISTANCE_FROM_CENTER;
	private final static float LEFT_BLAST_LINE = CENTER - DISTANCE_FROM_CENTER;
	private final static float UPPER_BLAST_LINE = -5;
	private final static float BOTTOM_BLAST_LINE = 16;
	protected World m_world;
	
	protected Character m_opponent;
	
	// -1 = left 1 = right: for use with placing hitboxes, applying forces, etc.
	//not for use with flipping sprites
	private static final int FACING_LEFT  = -1;
	private static final int FACING_RIGHT = 1;
	
	static final Vector2 LEFT_SCALE  = new Vector2(1, 1);
	static final Vector2 RIGHT_SCALE = new Vector2(-1, 1);

	protected Vector2 jumpImpulse = new Vector2(0, -20);
	protected Vector2 runForce    = new Vector2(20, 0);
	protected float   maxRunSpeed = 5;
	
	public static String[] characterNames = {"Jack", "Birboi", "Cam", "W'all", "Edgewardo", "Jimmy"};
	
	private ArrayList<Hitbox> m_hitboxes = new ArrayList<Hitbox> ();
	private ArrayList<CharacterEffect> m_effects  = new ArrayList<CharacterEffect>();
	private ArrayList<Projectile> m_projectiles = new ArrayList<Projectile>();
	
	private Stack<CharacterState> m_stateStack = new Stack<CharacterState> ();
	
	public static final int EVENT_HITSTUN	    = -3;
	public static final int ACTION_MOVERIGHT	= -2;
	public static final int ACTION_MOVELEFT   = -1;
	public static final int ACTION_JUMP  	    = 0;
	public static final int ACTION_JAB   	    = 1;
	public static final int ACTION_TILT 	    = 2;
	public static final int ACTION_SMASH 		= 3;
	public static final int ACTION_PROJECTILE = 4;
	public static final int ACTION_SIGNATURE  = 5;
	public static final int ACTION_RECOVERY   = 6;
	
	protected static double position = 0;
	
	private DeathAnimation death;
	
	protected Character()
	{
		pushState(new IdleState());
	}
	
	public void setWorld(World p_world) {
		m_world = p_world;
	}
	
	public double getKbooster() { return 1; }
	
	public boolean jumped() { return m_jumped; }
	public boolean recovered() { return m_recovered; }
	
	public void pushState(CharacterState p_state)
	{
		p_state.setCharacter(this);
		if(!m_stateStack.empty())
			m_stateStack.peek().pause();
		m_stateStack.push(p_state);
	}
	
	public void popState()
	{
		m_stateStack.peek().interrupt();
		m_stateStack.pop();
		m_stateStack.peek().resume();
	}
	
	public void endState()
	{
		m_stateStack.peek().end();
		m_stateStack.pop();
		m_stateStack.peek().resume();
	}
	
	public CharacterState peekState()
	{
		return m_stateStack.peek();
	}
	
	public int getStock()
	{
		return m_stock;
	}
	
	public void setSprite(Sprite p_sprite)
	{
		m_sprite = p_sprite;
	}
	
	public Sprite getSprite()
	{
		return m_sprite;
	}
	
	public int getDamage()
	{
		return m_damage;
	}
	
	public void setDamage(int p_damage)
	{
		m_damage = p_damage;
	}
	
	public void addDamage(int p_damage)
	{
		m_damage += p_damage;
	}
	
	public Character getOpponent()
	{
		return m_opponent;
	}
	
	public void setOpponenet(Character p_opponent)
	{
		m_opponent = p_opponent;
	}
	
	public abstract String getName();
	
	public Vector2 getJumpImpulse()
	{
		return jumpImpulse;
	}
	
	public void jump() //to be pronounced [jʌmp] (ipa)
	{
		pushState(new JumpState());
		m_jumped = true;
	}
	
	public void resetJump()
	{
		if(peekState() instanceof JumpState)
			popState();
		m_jumped = false;
		m_recovered = false;
	}
	
	public int getFacing()
	{
		return m_facingRight ? FACING_RIGHT : FACING_LEFT;
	}
	
	public Vector2 alignFacing(Vector2 p_vec)
	{
		return new Vector2(p_vec.x * getFacing(), p_vec.y);
	}
	
	public void setFacing(int p_direction)
	{
		m_facingRight = (p_direction == FACING_RIGHT) ? true : false;
	}
	
	public void applyRunForce()
	{
		if(Math.abs(getBody().getLinearVelocity().x) < getMaxRunSpeed()
				|| Math.signum(getBody().getLinearVelocity().x) != Math.signum(getFacing()))
			m_body.applyForce(alignFacing(runForce));
	}
	
	public float getMaxRunSpeed()
	{
		return maxRunSpeed;
	}
	
	protected abstract void jab();
	protected abstract void tilt();
	protected abstract void smash();
	protected abstract void projectile();
	protected abstract void signature();
	protected abstract void recover();
	
	public void handleEvent(int p_event)
	{
		if(!m_stateStack.peek().handleAction(p_event))
			return;
		
		switch(p_event)
		{
			case ACTION_MOVERIGHT:
				m_facingRight = true;
				return;
				
			case ACTION_MOVELEFT:
				m_facingRight = false;
				return;
				
			case ACTION_JUMP:
				if(!m_jumped)
				jump();
				return;
				
			case ACTION_JAB:
				jab();
				setAttacking(true);
				return;
				
			case ACTION_TILT:
				tilt();
				setAttacking(true);
				return;
				
			case ACTION_SMASH:
				smash();
				setAttacking(true);
				return;
				
			case ACTION_SIGNATURE:
				signature();
				setAttacking(true);
				return;
				
			case ACTION_PROJECTILE:
				projectile();
				setAttacking(true);
				return;
				
			case ACTION_RECOVERY:
				recover();
				setAttacking(true);
				return;
		}
	}
	
	public void setAttacking(boolean p_attacking)
	{
		m_attacking = p_attacking;
	}
	
	public boolean isAttacking()
	{
		return m_attacking;
	}
	
	public void takeHit(Hitbox p_hitbox)
	{
		//may want this to happen after the scaling
		if(p_hitbox.isAlive())
		{
			addDamage(p_hitbox.getDamage());
			
			Vector2 base = p_hitbox.getBaseKnockback();
			Vector2 scaled = p_hitbox.getScaledKnockback().multiply((double)(m_damage)/50);
			if(p_hitbox.getHitstun() > 0) {
				m_body.setLinearVelocity(0, 0);
			}
			//System.out.println(base.add(scaled));
			m_body.applyImpulse(base.add(scaled));
			applyHitstun(p_hitbox.getHitstun());
			p_hitbox.kill();
		}
	}
	
	public void applyHitstun(float p_duration)
	{
		if(m_superArmour || p_duration < 0)
			return;
		if(p_duration > 0) {
			handleEvent(EVENT_HITSTUN);
			pushState(new Hitstun(p_duration));
		}
	}
	
	public void setStunned(boolean p_stunned)
	{
		m_stunned = p_stunned;
	}
	
	public boolean isStunned()
	{
		return m_stunned;
	}
	
	public void interruptAttack()
	{
		if(m_superArmour)
			return;
		pushState(new Hitstun(0));
	}

	public void setBody(Body p_body)
	{
		p_body.setUserData(this);
		m_body = p_body;
	}
	
	public Body getBody()
	{
		return m_body;
	}
	
	protected void addHitbox(Hitbox p_hitbox)
	{
		m_hitboxes.add(p_hitbox);
	}
	
	protected void removeHitbox(Hitbox p_hitbox)
	{
		m_hitboxes.remove(p_hitbox);
	}
	
	
	protected void addEffect(CharacterEffect p_effect)
	{
		m_effects.add(p_effect);
	}
	
	protected void removeEffect(CharacterEffect p_effect)
	{
		m_effects.remove(p_effect);
	}
	
	protected void addProjectile(Projectile p_projectile)
	{
		m_projectiles.add(p_projectile);
	}
	
	protected void removeProjectile(Projectile p_projectile)
	{
		m_projectiles.remove(p_projectile);
	}
	
	public Object[] getProjectiles() {
		return m_projectiles.toArray();
	}
	
	public boolean isDead()
	{
		return (m_body.getWorldCenter().x > RIGHT_BLAST_LINE ||
				m_body.getWorldCenter().x < LEFT_BLAST_LINE ||
				m_body.getWorldCenter().y < UPPER_BLAST_LINE ||
				m_body.getWorldCenter().y > BOTTOM_BLAST_LINE);
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{	
		Transform t = m_body.getTransform();
		
		if (m_sprite.getAnimation() != null)
		{
			IntRect frame = m_sprite.getAnimation().getFrame();
			int offset = m_facingRight ? frame.w / 32 : 0;
			m_sprite.setPosition(t.getTranslationX() + offset, t.getTranslationY());
			m_sprite.setRotation(t.getRotation());
			m_sprite.setScale(m_facingRight ? RIGHT_SCALE : LEFT_SCALE);
			m_sprite.draw(p_renderer);
		}
		
		for(CharacterEffect e : m_effects)
		{
			e.getSprite().setScale(m_facingRight ? RIGHT_SCALE : LEFT_SCALE);
			e.draw(p_renderer);
		}
		
		for(Projectile p : m_projectiles)
		{
			if(p.getBody() != null)
			{
				//this has... interesting consequences. maybe move it to add_projectile?
				//p.getSprite().setScale(m_facingRight ? RIGHT_SCALE : LEFT_SCALE);
				p.draw(p_renderer);
			}
		}

		if(death != null) //checking if isDead() doesn't seem to work
			death.draw(p_renderer);
	}
	
	public void update(float p_delta)
	{
		for(int i = 0; i < m_hitboxes.size(); i++)
	    {
			m_hitboxes.get(i).updateTimer(p_delta);
		    if(!m_hitboxes.get(i).isAlive())
		    {
		      m_hitboxes.remove(i--);
		    }
	    }
		
		for(int i = 0; i < m_projectiles.size(); i++) {
			m_projectiles.get(i).getHitbox().updateTimer(p_delta);
			if(!m_projectiles.get(i).getHitbox().isAlive()) {
				m_world.removeBody(m_projectiles.get(i).getBody());
				removeProjectile(m_projectiles.get(i));
				i--;
			}
		}
		//CharacterState currentState = peekState();
		
		peekState().update(p_delta);
		
		for(int i = 0; i < m_projectiles.size(); i++)
			if(!m_projectiles.get(i).update(p_delta))
				m_projectiles.remove(i--);
		
		//TODO: move this stuff out of the charcter class to a collision listener
		if(isDead()) 
		{
			m_stock--;
			death = new DeathAnimation(this);
			death.setPosition(m_body.getWorldCenter());
			//TODO: Perhaps a character state for waiting time?
			Transform t = new Transform();
			t.translate(6.25, 0);
			m_body.setTransform(t);
			m_body.setLinearVelocity(0, 0);
			applyHitstun(0.01f);
			setDamage(0);
		}
	}
}