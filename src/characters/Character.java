package characters;

import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Drawable;
import graphics.Renderer;
import graphics.Sprite;
import program.Battle;
import program.Hitbox;


public abstract class Character implements Drawable
{
	protected Body m_body;
	private Sprite m_sprite;

	private int m_damage;
	private int m_stock = 3;
	private String m_name = "George the Glass-Cutter";
	private boolean m_jumped = false;
	private boolean m_recovered = false;
	private boolean m_moving = false;

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
	
	public Character()
	{
		//set up the body here
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
	
	public String getName()
	{
		return m_name;
	}
	
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
		m_sprite.setAnimation("run");
	}
	
	public void moveRight()
	{
		m_moving = true;
		m_body.applyForce(force_R);
		m_sprite.setAnimation("run");
	}
	
	public abstract void jab(Battle p_battle);
	public abstract void tilt(Battle p_battle);
	public abstract void smash(Battle p_battle);
	public abstract void projectile(Battle p_battle);
	public abstract void signature(Battle p_battle);
	public abstract void recover(Battle p_battle);
	
	public void takeHit(Hitbox p_hitbox)
	{
		//may want this to happen after the scaling
		addDamage(p_hitbox.getDamage());
		
		Vector2 base = p_hitbox.getBaseKnockback();
		Vector2 scaled = p_hitbox.getScaledKnockback().multiply(m_damage/50);
		
		m_body.applyImpulse(base.add(scaled));
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
	
	@Override
	public void draw(Renderer p_renderer)
	{
		Transform t = m_body.getTransform();
		m_sprite.setPosition(t.getTranslationX(), t.getTranslationY());
		m_sprite.setRotation(t.getRotation());
		m_sprite.draw(p_renderer);
	}
}
