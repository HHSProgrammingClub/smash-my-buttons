package program;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

import gameObject.OnInitPhysics;

public class PhysicsWorld
{
	private World m_world = new World();
	
	public PhysicsWorld()
	{
		m_world.setGravity(new Vector2(0, 1));
	}
	
	public void update(float p_delta, Scene p_scene)
	{
		p_scene.receiveMessage(new OnInitPhysics(m_world));
		
		m_world.updatev(p_delta);
	}
}
