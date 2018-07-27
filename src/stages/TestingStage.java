package stages;

import org.dyn4j.dynamics.World;

public class TestingStage extends Environment
{
	public TestingStage()
	{
		World aWorld = new World();
		//set up world
		setPhysicsWorld(aWorld);
		m_name = "TestingStage";
	}
}
