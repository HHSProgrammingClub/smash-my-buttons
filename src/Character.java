import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;


public abstract class Character
{
	private int m_damage;
	private Sprite m_sprite;
	
	protected Body m_body;
	protected Move[] m_moveSet;
	
	Character()
	{
		m_moveSet = new Move[6];
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
	
	public static String getName()
	{
		return "Default";
	}
	
	public void setMove(MoveType p_type, Move p_move)
	{
		m_moveSet[p_type.num] = p_move;
	}
	
	public void doMove(MoveType p_type)
	{
		//m_moveSet[p_type.num].doThing();
	}
	
	//this will return the total impulse to apply to the character
	//would need to be passed the body to apply the impulse itself
	public Vector2 takeHit(Hitbox p_hitbox)
	{
		//may want this to happen after the scaling
		addDamage(p_hitbox.getDamage());
		
		Vector2 base = p_hitbox.getBaseKnockback();
		Vector2 scaled = p_hitbox.getScaledKnockback().multiply(m_damage/50);
		
		return base.add(scaled);
	}
	
	public void addToBody(Body p_body)
	{
		p_body.setUserData(this);
		m_body = p_body;
	}
}
