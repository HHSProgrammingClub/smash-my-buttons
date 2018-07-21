import java.util.List;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;

public class DebugDrawer
{
	public void debugDraw(World p_world)
	{
		//do the stuff
		List<Body> bodies =  p_world.getBodies();
		for(Body b : bodies)
		{
			List<BodyFixture> fixtures = b.getFixtures();
			for(BodyFixture f : fixtures)
			{
				//draw the thing
			}
		}
	}
}
