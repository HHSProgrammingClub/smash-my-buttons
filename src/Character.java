import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;


public abstract class Character
{
	private int m_damage; // Damage percentage
	private Sprite m_sprite;
	
	protected Body m_body;
	protected Move[] m_moveSet;
	
	public static Vector2 jumpImpulse = new Vector2(0, 5);
	private int stock = 3;
	private String name = "George the Glass-Cutter";
	private boolean jumped = false;
	private boolean recovered = false;
	
	Character()
	{
		m_moveSet = new Move[6];
	}
	
	public int getStock() {return stock;}
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
		return name;
	}
	
	public void setMove(MoveType p_type, Move p_move)
	{
		m_moveSet[p_type.num] = p_move;
	}
	
	public void doMove(MoveType p_type, Battle battle)
	{
		Move focus = m_moveSet[p_type.num];
		battle.addMove(focus);
		focus.instantEffect(battle);
	}
	
	public void jump() //to be pronounced [jʌmp] (ipa)
	{
		if(!jumped) {
			m_body.applyImpulse(jumpImpulse);
			jumped = true;
		}
	}
	public void recover(Battle battle) {
		if(!recovered) {
			doMove(MoveType.recovery, battle);
		}
	}
	
	public void takeHit(Hitbox p_hitbox)
	{
		//may want this to happen after the scaling
		addDamage(p_hitbox.getDamage());
		
		Vector2 base = p_hitbox.getBaseKnockback();
		Vector2 scaled = p_hitbox.getScaledKnockback().multiply(m_damage/50);
		
		m_body.applyImpulse(base.add(scaled));
	}
	
	public void addToBody(Body p_body)
	{
		p_body.setUserData(this);
		m_body = p_body;
	}
	
}
