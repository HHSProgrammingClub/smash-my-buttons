import org.dyn4j.geometry.Vector2;
import org.dyn4j.dynamics.BodyFixture;

public class Hitbox
{
	public void addToFixture(BodyFixture p_fixture)
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
	}
	
	public float get_duration()
	{
		return m_duration;
	}
	
	private Vector2 m_scaledKnockback;
	private Vector2 m_baseKnockback;
	private int m_damage;
	private float m_duration;
}
