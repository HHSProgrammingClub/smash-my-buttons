import java.util.ArrayList;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;

public abstract class Environment
{
	public Environment() {}
	
	public Environment(World p_world)
	{
		setPhysicsWorld(p_world);
	}
	
	public void setPhysicsWorld(World p_world)
	{
		m_world = p_world;
	}
	
	public World getWorld() { return m_world; }
	
	private void addTerrainPiece(Body p_piece)
	{
		m_terrain.add(p_piece);
		m_terrain.get(m_terrain.size()).setMassType(MassType.INFINITE);
	}
	
	private World m_world;
	
	private ArrayList<Body> m_terrain;
}
