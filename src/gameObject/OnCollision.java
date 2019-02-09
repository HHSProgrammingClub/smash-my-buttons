package gameObject;

public class OnCollision implements Message 
{
	private BoxColliderComponent m_ourCollider, m_theirCollider;
	
	OnCollision(BoxColliderComponent p_ourCollider, BoxColliderComponent p_theirCollider)
	{
		m_ourCollider = p_ourCollider;
		m_theirCollider = p_theirCollider;
	}
	
	BoxColliderComponent ourCollider()
	{
		return m_ourCollider;
	}
	
	BoxColliderComponent theirCollider()
	{
		return m_theirCollider;
	}
}