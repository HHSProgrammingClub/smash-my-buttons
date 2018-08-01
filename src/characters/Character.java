
package characters;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Stack;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Animation;
import graphics.Drawable;
import graphics.Renderer;
import graphics.Sprite;
import program.Hitbox;


//TODO: Add a way to lock out actions

public abstract class Character implements Drawable
{
	protected Body m_body;
	protected Sprite m_sprite;

	private int m_damage;
	private int m_stock = 3;
	private String m_name = "George the Glass-Cutter";
	private boolean m_jumped = false;
	private boolean m_recovered = false;
	private boolean m_moving = false;
	protected boolean m_superArmour = false;
	private boolean m_stunned = false;

	protected static Vector2 jumpImpulse = new Vector2(0, -5);
	protected static Vector2 force_L = new Vector2(-5, 0);
	protected static Vector2 force_R = new Vector2(5, 0);
	
	public static String[] characterNames = {"Jack", "Birboi", "Cam", "W'all", "Edgewardo", "Jimmy"};
	
	protected int current_move;
	
	public static final int MOVE_NONE = 0;
	public static final int MOVE_JAB = 1;
	public static final int MOVE_TILT = 2;
	public static final int MOVE_SMASH = 3;
	public static final int MOVE_SGNATURE = 4;
	public static final int MOVE_RECOVERY = 5;
	
	private ArrayList<Hitbox> m_hitboxes = new ArrayList<Hitbox> ();
	
	private Stack<CharacterState> m_stateStack = new Stack<CharacterState> ();
	
	protected static double position = 0;
	
	protected void addState(CharacterState p_state)
	{
		m_stateStack.add(p_state);
	}
	
	protected void addAnimationState(Animation p_animation, float p_duration)
	{
		m_stateStack.add(new CharacterState(p_animation, p_duration));
	}
	
	protected void addAnimationState(String p_name, float p_duration)
	{
		m_stateStack.add(new CharacterState(p_name, p_duration));
	}
	
	protected void interruptStates(CharacterState p_state)
	{
		m_stateStack.clear();
		addState(p_state);
	}
	
	protected void interruptStates()
	{
		interruptStates(new CharacterState("idle", -1));
	}
	
	protected void interruptCurrentState()
	{
		m_stateStack.get(0).interrupt();
		m_stateStack.remove(0);
	}
	
	/**
	 * Class to hold effects on characters, such as temporary
	 * animations or selective ignoring of gravity.
	 * 
	 * Extend this class for effects that do more than
	 * play an animation
	 */
	protected class CharacterState
	{
		private Animation m_animation;
		private float m_duration;
		private float m_timer;
		
		private boolean m_started = false;
		
		private boolean m_indefinite;
		
		CharacterState(Animation p_animation, float p_duration)
		{
			setAnimation(p_animation);
			setDuration(p_duration);
		}
		
		CharacterState(Animation p_animation)
		{
			this(p_animation, p_animation.getInterval() * p_animation.getFrameCount());
		}
		
		CharacterState(String p_animationName, float p_duration)
		{
			this(m_sprite.getTexture().getAnimation(p_animationName), p_duration);
		}
		
		CharacterState(String p_animationName)
		{
			this(m_sprite.getTexture().getAnimation(p_animationName));
		}
		
		CharacterState() {}
		
		public void setAnimation(Animation p_animation)
		{
			m_animation = p_animation;
		}
		
		public Animation getAnimation()
		{
			return m_animation;
		}
		
		public boolean isIndefinite()
		{
			return m_indefinite;
		}
		
		/**
		 * Set the duration of the state
		 * @param p_duration pass a negative value to make indefinite
		 */
		public void setDuration(float p_duration)
		{
			m_duration = p_duration;
			m_timer = m_duration;
			m_indefinite = m_timer < 0;
		}
		
		public float getDuration()
		{
			return m_duration;
		}
		
		public float getTimer()
		{
			return m_timer;
		}
		
		public boolean isStarted()
		{
			return m_started;
		}
		
		public void start()
		{
			m_sprite.setAnimation(m_animation);
			m_started = true;
			moreStart();
		}
		
		protected void moreStart()
		{
			
		}
		
		public void interrupt()
		{
			
		}
		
		public void end()
		{
			
		}
		
		/**
		 * @param p_delta
		 * @return false if p_timer <= 0
		 */
		public boolean updateTimer(float p_delta)
		{
			m_timer -= p_delta;
			return m_timer > 0;
		}
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
	
	public void jump() //to be pronounced [jʌmp] (ipa)
	{
		if(!m_jumped)
		{
			m_body.applyImpulse(jumpImpulse);
			m_jumped = true;
			m_sprite.setAnimation("jump_asc");
		}
	}
	
	public void resetJump()
	{
		m_jumped = false;
		m_recovered = false;
		/*if(!m_jumped)
			m_sprite.setAnimation("idle");*/
	}
	
	public void moveLeft()
	{
		m_moving = true;
		m_body.applyForce(force_L);
		if(!m_jumped)
			m_sprite.setAnimation("run");
	}
	
	public void moveRight()
	{
		m_moving = true;
		m_body.applyForce(force_R);
		if(!m_jumped)
			m_sprite.setAnimation("run");
	}
	
	public abstract void jab();
	public abstract void tilt();
	public abstract void smash();
	public abstract void projectile();
	public abstract void signature();
	public abstract void recover();
	
	public void takeHit(Hitbox p_hitbox)
	{
		//may want this to happen after the scaling
		addDamage(p_hitbox.getDamage());
		
		Vector2 base = p_hitbox.getBaseKnockback();
		Vector2 scaled = p_hitbox.getScaledKnockback().multiply(m_damage/50);
		
		m_body.applyImpulse(base.add(scaled));
	}
	
	public void applyHitstun(float p_duration)
	{
		if(m_superArmour)
			return;
		interruptStates(new Hitstun(p_duration));
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
	
	@Override
	public void draw(Renderer p_renderer)
	{
		Transform t = m_body.getTransform();
		m_sprite.setPosition(t.getTranslationX(), t.getTranslationY());
		m_sprite.setRotation(t.getRotation());
		m_sprite.draw(p_renderer);
	}
	
	public void update(float p_delta)
	{	
		for(Hitbox h : m_hitboxes)
		{
			h.updateTimer(p_delta);
			
			if(!h.isAlive())
			{
				m_hitboxes.remove(h);
			}
		}
		
		if(m_stateStack.size() > 0)
		{
			CharacterState currentState = m_stateStack.get(0);
			
			if(!currentState.isIndefinite())
			{
				currentState.updateTimer(p_delta);
				if (currentState.getTimer() <= 0)
				{
					currentState.end();
					m_stateStack.remove(0);
					if(m_stateStack.size() > 0)
						m_stateStack.get(0).start();
				} 
			}
		}
	}
	
	public class Hitstun extends CharacterState
	{

		Hitstun(float p_duration)
		{
			super("hitstun", p_duration);
		}

		@Override
		public void start()
		{
			m_stunned = true;
		}
		
		@Override
		public void interrupt()
		{
			end();
		}

		@Override
		public void end()
		{
			m_stunned = false;
		}
		
	}
}
