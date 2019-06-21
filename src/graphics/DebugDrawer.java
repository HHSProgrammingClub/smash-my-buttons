package graphics;


import java.util.List;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.AABB;
import org.newdawn.slick.Color;

import graphics.pages.Renderer;
import stages.TerrainPiece;

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
				AABB obj = f.getShape().createAABB();
				DoubleRect boundingBox = new DoubleRect(obj);
				
				p_renderer.pushTransform(b.getTransform());
				
				if(b.getUserData() instanceof Character)
					p_renderer.drawRect(boundingBox, Color.red, 0.5f); // Stroke is in our new units
				else if (b.getUserData() instanceof TerrainPiece)
					p_renderer.drawRect(boundingBox, Color.blue, 0.5f);
				else
					p_renderer.drawRect(boundingBox, Color.green, 0.5f);
				
				p_renderer.popTransform();
			}
		}
	}
}