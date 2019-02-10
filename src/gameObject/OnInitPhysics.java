package gameObject;

import org.dyn4j.dynamics.World;

public class OnInitPhysics implements Message
{
	private World m_world;
	
	public OnInitPhysics(World p_world)
	{
		m_world = p_world;
	}
	
	World getWorld()
	{
		return m_world;
	}
}
