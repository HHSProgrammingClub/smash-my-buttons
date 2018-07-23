package program;
import java.util.ArrayList;

import org.dyn4j.dynamics.World;

public abstract class Environment
{
	
	private World m_world;
	private ArrayList<TerrainPiece> m_terrain;
	
	public Environment() {}
	
	public Environment(World p_world)
	{
		setPhysicsWorld(p_world);
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
