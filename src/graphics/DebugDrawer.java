package graphics;

import java.awt.Color;
import java.util.List;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.AABB;

import graphics.pages.Renderer;

public class DebugDrawer
{
	private World m_world;
	
	public DebugDrawer(World p_world)
	{
		m_world = p_world;
	}
	
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
				AABB obj = f.getShape().createAABB();
				DoubleRect boundingBox = new DoubleRect(obj);
				
				p_renderer.pushTransform(b.getTransform());
				
				if(b.getUserData() instanceof Character)
					p_renderer.drawRect(boundingBox, Color.RED, 0.5f, 1f/32); // Stroke is in our new units
				else
					p_renderer.drawRect(boundingBox, Color.GREEN, 0.5f, 1f/32);
				
				p_renderer.popTransform();
			}
		}
	}
}