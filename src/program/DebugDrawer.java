package program;
import java.util.List;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;

public class DebugDrawer implements Drawable
{
	private World m_world;
	
	public DebugDrawer(World p_world)
	{
		m_world = p_world;
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		List<Body> bodies = m_world.getBodies();
		for(Body b : bodies)
		{
			List<BodyFixture> fixtures = b.getFixtures();
			for(BodyFixture f : fixtures)
			{
				//draw the thing
				//choose one
				//p_renderer.drawRect(fixture dimensions, Color.RED, 0.75f);
				//p_renderer.drawRect(dimension, Color.RED, 0.75f, 3);
			}
		}
	}
}
