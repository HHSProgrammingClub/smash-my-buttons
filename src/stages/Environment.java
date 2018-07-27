package stages;
import java.util.ArrayList;

import org.dyn4j.dynamics.World;

import program.TerrainPiece;

public abstract class Environment
{
	public static String[] stageNames = {"TestingStage"};
	
	private World m_world;
	private ArrayList<TerrainPiece> m_terrain;
	
	protected String m_name;
	
	public Environment()
	{
		m_world = new World();
	}
	
	public Environment(World p_world)
	{
		setPhysicsWorld(p_world);
	}
	
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
	
	protected void addTerrainPiece(TerrainPiece p_piece)
	{
		m_terrain.add(p_piece);
		m_world.addBody(p_piece.getBody());
	}
	
}
