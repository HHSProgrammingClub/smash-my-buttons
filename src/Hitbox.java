import org.dyn4j.geometry.Vector2;
import org.dyn4j.collision.Fixture;

public class Hitbox {
	
	Hitbox() {}
	
	Hitbox(Vector2 p_knockback)
	{
		setKnockback(p_knockback);
	}
	
	Hitbox(Vector2 p_knockback, int p_damage)
	{
		setKnockback(p_knockback);
		setDamage(p_damage);
	}
	
	void setFixture(Fixture p_fixture)
	{
		m_fixture = p_fixture;
		m_fixture.setSensor(true);
	}
	
	void setKnockback(Vector2 p_knockback)
	{
		m_knockback = p_knockback;
	}
	
	void setDamage(int p_damage)
	{
		m_damage = p_damage;
	}
	
	int getDamage()
	{
		return m_damage;
	}
	
	private Fixture m_fixture;
	private Vector2 m_knockback;
	private int m_damage;
}
