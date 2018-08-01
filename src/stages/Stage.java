package stages;
import java.util.ArrayList;

import org.dyn4j.dynamics.World;

import graphics.RenderList;

public abstract class Stage
{
	public static String[] stageNames = {"TestingStage"};
	
	private World m_world = new World();
	protected ArrayList<TerrainPiece> m_terrain = new ArrayList<TerrainPiece>();
	
	protected String m_name;
	
	public String getName()
	{
		return m_name;
	}
	
	public void setPhysicsWorld(World p_world)
	{
		m_world = p_world;
	}
	
	public World getPhysicsWorld()
	{
		return m_world;
	}
	
	public void registerTerrainSprites(RenderList p_list)
	{
		for(TerrainPiece t : m_terrain)
			p_list.addDrawable(t);
	}
	
}
