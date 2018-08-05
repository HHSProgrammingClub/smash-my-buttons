package characters;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Stack;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Animation;
import graphics.Drawable;
import graphics.IntRect;
import graphics.Renderer;
import graphics.Sprite;
import program.Hitbox;
import characters.characterStates.*;


//TODO: Add a way to lock out actions

public abstract class Character implements Drawable
{
	protected Body m_body;
	protected Sprite m_sprite;

	private int m_damage;
	private int m_stock = 3;
	private String m_name = "George the Glass-Cutter";
	private boolean m_jumped        = false;
	protected boolean m_recovered     = false;
	private boolean m_moving        = false;
	protected boolean m_superArmour = false;
	private boolean m_stunned       = false;
	private boolean m_attacking     = false;
	private boolean m_facingRight   = false;
	
	// -1 = left 1 = right: for use with placing hitboxes, applying forces, etc.
	//not for use with flipping sprites
	private static final int FACING_LEFT  = -1;
	private static final int FACING_RIGHT = 1;
	
	private static final Vector2 LEFT_SCALE  = new Vector2(1, 1);
	private static final Vector2 RIGHT_SCALE = new Vector2(-1, 1);

	protected static Vector2 jumpImpulse = new Vector2(0, -20);
	protected static Vector2 runForce    = new Vector2(10, 0);
	protected static float   maxRunSpeed = 6;
	
	public static String[] characterNames = {"Jack", "Birboi", "Cam", "W'all", "Edgewardo", "Jimmy"};
	
	private ArrayList<Hitbox> m_hitboxes = new ArrayList<Hitbox> ();
	
	private Stack<CharacterState> m_stateStack = new Stack<CharacterState> ();
	
	public static final int ACTION_MOVERIGHT	= -2;
	public static final int ACTION_MOVELEFT 	= -1;
	public static final int ACTION_JUMP  		= 0;
	public static final int ACTION_JAB   		= 1;
	public static final int ACTION_TILT  		= 2;
	public static final int ACTION_SMASH 		= 3;
	public static final int ACTION_PROJECTILE = 4;
	public static final int ACTION_SIGNATURE  = 5;
	public static final int ACTION_RECOVERY 	= 6;
	
	protected static double position = 0;
	
	protected Character()
	{
		
	}
	
	protected void addState(CharacterState p_state)
	{
		p_state.setCharacter(this);
		m_stateStack.add(p_state);
	}
	
	protected void addAnimationState(Animation p_animation, float p_duration)
	{
		addState(new CharacterState(p_animation, p_duration));
	}
	
	protected void addAnimationState(String p_name, float p_duration)
	{
		addState(new CharacterState(p_name, p_duration));
	}
	
	public void pushState(CharacterState p_state)
	{
		p_state.setCharacter(this);
		if(!m_stateStack.empty())
			m_stateStack.get(0).pause();
		m_stateStack.push(p_state);
	}
	
	protected void pushAnimationState(Animation p_animation, float p_duration)
	{
		pushState(new CharacterState(p_animation, p_duration));
	}
	
	protected void pushAnimationState(String p_name, float p_duration)
	{
		pushState(new CharacterState(p_name, p_duration));
	}
	
	protected void interruptStates(CharacterState p_state)
	{
		if(m_stateStack.size() > 0)
			m_stateStack.get(0).interrupt();
		m_stateStack.clear();
		addState(p_state);
	}
	
	protected void interruptStates()
	{
		interruptStates(new IdleState());
	}
	
	protected void interruptCurrentState()
	{
		m_stateStack.get(0).interrupt();
		m_stateStack.remove(0);
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
	
	public abstract String getName();
	
	public Vector2 getJumpImpulse()
	{
		return jumpImpulse;
	}
	
	protected void jump() //to be pronounced [jʌmp] (ipa)
	{
		m_jumped = true;
		pushState(new JumpState());
		System.out.println("jumpo");
	}
	
	public void resetJump()
	{
		if(m_stateStack.peek() instanceof JumpState)
			m_stateStack.pop();
		m_jumped = false;
		m_recovered = false;
	}
	
	public void moveLeft()
	{
		m_moving = true;
		m_facingRight = false;
		m_body.applyForce(getRunForce());
		if(!m_jumped)
			m_sprite.setAnimation("run");
	}
	
	public void moveRight()
	{
		m_moving = true;
		m_facingRight = true;
		m_body.applyForce(getRunForce());
		if(!m_jumped)
			m_sprite.setAnimation("run");
	}
	
	public void stopRunning()
	{
		m_moving = false;
		if(!m_jumped && !m_attacking)
			interruptStates(new StoppingState());
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
	
	public Vector2 getRunForce()
	{
		return alignFacing(runForce);
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
	
	public void performAction(int p_action)
	{
		if(!m_stateStack.peek().handleAction(p_action))
			return;
		
		switch(p_action)
		{
			case ACTION_MOVERIGHT:
				moveRight();
				return;
				
			case ACTION_MOVELEFT:
				moveLeft();
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
			System.out.println(base.add(scaled));
			m_body.applyImpulse(base.add(scaled));
			applyHitstun(p_hitbox.getHitstun());
			p_hitbox.kill();
		}
	}
	
	public void applyHitstun(float p_duration)
	{
		if(m_superArmour || p_duration <= 0)
			return;
		interruptStates(new Hitstun(p_duration));
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
		interruptStates(new Hitstun(0));
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
	
	@Override
	public void draw(Renderer p_renderer)
	{
		Transform t = m_body.getTransform();
		
		IntRect frame = m_sprite.getAnimation().getFrame();
		int offset = m_facingRight ? frame.w / 32 : 0;
		m_sprite.setPosition(t.getTranslationX() + offset, t.getTranslationY());
		m_sprite.setRotation(t.getRotation());
		m_sprite.setScale(m_facingRight ? RIGHT_SCALE : LEFT_SCALE);
		m_sprite.draw(p_renderer);
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
		
		if(m_stateStack.size() > 0)
		{
			CharacterState currentState = m_stateStack.peek();
			
			if(!currentState.isStarted())
				currentState.start();

			if(currentState.isPaused())
				currentState.resume();
			
			currentState.update(p_delta);
			
			if(!currentState.isIndefinite() && currentState.getTimer() <= 0)
			{
				currentState.end();
				m_stateStack.pop();
				if(!(m_stateStack.size() > 0))
					addState(new IdleState());

				m_stateStack.peek().start();
			}
		}
	}
}