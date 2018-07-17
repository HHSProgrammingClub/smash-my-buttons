
public class Projectile {
	
	public Projectile() {}
	
	public Projectile(Sprite p_sprite)
	{
		setSprite(p_sprite);
	}
	
	public Projectile(Sprite p_sprite, Hitbox p_hitbox)
	{
		setSprite(p_sprite);
		setHitbox(p_hitbox);
	}
	
	public void setSprite(Sprite p_sprite)
	{
		m_sprite = p_sprite;
	}
	
	public void setHitbox(Hitbox p_hitbox)
	{
		m_hitbox = p_hitbox;
	}
	
	private Hitbox m_hitbox;
	private Sprite m_sprite;
}
