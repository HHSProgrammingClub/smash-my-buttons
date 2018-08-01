package characters;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Animation;
import graphics.Drawable;
import graphics.Renderer;
import graphics.Sprite;
import program.Hitbox;

public abstract class Character implements Drawable
{
	protected Body m_body;
	protected Sprite m_sprite;

	private int m_damage;
	private int m_stock = 3;
	private String m_name;
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
	
	private ArrayList<AnimationState> m_animationStack = new ArrayList<AnimationState> ();
	private ArrayList<Effect> m_effectStack = new ArrayList<Effect> ();
	
	protected static double position = 0;
	
	
	protected void addAnimation(AnimationState p_animation)
	{
		m_animationStack.add(p_animation);
	}
	
	protected void clearAnimations()
	{
		m_animationStack.clear();
		m_sprite.setAnimation("idle");
	}
	
	public void interruptAnimations(AnimationState p_newAnimation)
	{
		clearAnimations();
		addAnimation(p_newAnimation);
	}
	
	protected class AnimationState
	{
		private Animation m_animation;
		private float m_duration;
		private float m_timer;
		
		AnimationState(Animation p_animation, float p_duration)
		{
			m_animation = p_animation;
			m_duration = p_duration;
			m_timer = m_duration;
		}
		
		AnimationState(Animation p_animation)
		{
			this(p_animation, p_animation.getInterval() * p_animation.getFrameCount());
		}
		
		AnimationState(String p_animationName, float p_duration)
		{
			this(m_sprite.getTexture().getAnimation(p_animationName), p_duration);
		}
		
		AnimationState(String p_animationName)
		{
			this(m_sprite.getTexture().getAnimation(p_animationName));
		}
		
		public Animation getAnimation()
		{
			return m_animation;
		}
		
		public float getDuration()
		{
			return m_duration;
		}
		
		public float getTimer()
		{
			return m_timer;
		}
		
		public void updateTimer(float p_delta)
		{
			m_timer -= p_delta;
		}
	}
	
	protected void addEffect(Effect p_effect)
	{
		m_effectStack.add(p_effect);
		if(m_effectStack.get(0) == p_effect)
			m_effectStack.get(0).effectStart();
	}
	
	protected void interruptCurrentEffect(Effect p_effect)
	{
		interruptCurrentEffect();
		addEffect(p_effect);
	}
	
	protected void interruptCurrentEffect()
	{
		m_effectStack.get(0).effectInterrupted();
		m_effectStack.clear();
	}
	
	public abstract class Effect
	{
		private float m_duration;
		private float m_timer;
		
		Effect(float p_duration)
		{
			m_duration = p_duration;
			m_timer = m_duration;
		}
		
		public float getDuration()
		{
			return m_duration;
		}
		
		public float getTimer()
		{
			return m_timer;
		}
		
		public void updateTimer(float p_delta)
		{
			m_duration -= p_delta;
		}
		
		public void effectInterrupted()
		{
			//undo effect, if necessary
		}
		
		public abstract void effectStart();
		public abstract void effectEnd();
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
		m_sprite.setAnimation("idle");
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
		interruptCurrentEffect(new Hitstun(p_duration));
		interruptAnimations(new AnimationState("hitstun", p_duration));
	}
	
	public boolean isStunned()
	{
		return m_stunned;
	}
	
	public void interruptAttack()
	{
		if(m_superArmour)
			return;
		interruptCurrentEffect();
		interruptAnimations(new AnimationState("hitstun", .016667f));
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
		
		if(m_animationStack.size() > 0)
		{
			AnimationState currentState = m_animationStack.get(0);
			if(currentState.getTimer() == currentState.getDuration())
				m_sprite.setAnimation(currentState.getAnimation());
			currentState.updateTimer(p_delta);
			if(currentState.getTimer() <= 0)
			{	
				m_animationStack.remove(0);
				if(m_animationStack.size() == 0)
					m_sprite.setAnimation("idle");
			}
		}
	}
	
	public class Hitstun extends Effect
	{

		Hitstun(float p_duration)
		{
			super(p_duration);
		}

		@Override
		public void effectStart()
		{
			m_stunned = true;
		}
		
		@Override
		public void effectInterrupted()
		{
			effectEnd();
		}

		@Override
		public void effectEnd()
		{
			m_stunned = false;
		}
		
	}
}
