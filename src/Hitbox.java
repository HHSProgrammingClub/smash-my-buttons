import org.dyn4j.geometry.Vector2;
import org.dyn4j.collision.Fixture;

public class Hitbox
{
	
	private Vector2 m_scaledKnockback;
	private Vector2 m_baseKnockback;
	private int m_damage;
	private float m_duration;
	private boolean m_alive = true;
	private float m_timeLeft;
	
	public void addToFixture(Fixture p_fixture)
	{
		p_fixture.setUserData(this);
	}
	
	public void setDamage(int p_damage)
	{
		m_damage = p_damage;
	}
	
	public int getDamage()
	{
		return m_damage;
	}

	public Vector2 getBaseKnockback()
	{
		return m_baseKnockback;
	}

	public void setBaseKnockback(Vector2 p_baseKnockback)
	{
		m_baseKnockback = p_baseKnockback;
	}

	public Vector2 getScaledKnockback()
	{
		return m_scaledKnockback;
	}

	public void setScaledKnockback(Vector2 p_scaledKnockback)
	{
		m_scaledKnockback = p_scaledKnockback;
	}
	
	public void setDuration(float p_seconds)
	{
		m_duration = p_seconds;
		m_timeLeft = p_seconds;
	}
	
	public float get_duration()
	{
		return m_duration;
	}
	
	public void updateTimer(float p_delta)
	{
		m_timeLeft -= p_delta;
		if(m_timeLeft <= 0)
		{
			m_alive = false; // Prepare for r e m o v a l
		}
	}
	
	public boolean alive()
	{
		return m_alive;
	}
	
}
