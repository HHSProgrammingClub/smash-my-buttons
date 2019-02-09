package gameObject;

public class Update implements Message
{
	float m_delta;
	
	public Update(float p_delta)
	{
		m_delta = p_delta;
	}
}
