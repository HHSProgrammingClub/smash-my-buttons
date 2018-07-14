import org.dyn4j.geometry.Vector2;
import org.dyn4j.collision.Fixture;

public class Hitbox 
{

	private Fixture m_fixture;
	private Vector2 m_knockback;
	private int m_damage;
	
	public Hitbox() {}
	
	public Hitbox(Vector2 p_knockback)
	{
		setKnockback(p_knockback);
	}
	
	public Hitbox(Vector2 p_knockback, int p_damage)
	{
		setKnockback(p_knockback);
		setDamage(p_damage);
	}
	
	public void setFixture(Fixture p_fixture)
	{
		m_fixture = p_fixture;
		m_fixture.setSensor(true);
	}
	
	public void setKnockback(Vector2 p_knockback)
	{
		m_knockback = p_knockback;
	}
	
	public void setDamage(int p_damage)
	{
		m_damage = p_damage;
	}
	
	public int getDamage()
	{
		return m_damage;
	}
	
	public Vector2 getKnockback()
	{
		return m_knockback;
	}
	
}
