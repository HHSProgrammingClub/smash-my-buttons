package stages;
import java.util.ArrayList;

import org.dyn4j.dynamics.World;

import graphics.Drawable;
import graphics.RenderList;
import graphics.Renderer;
import graphics.Sprite;

public abstract class Stage implements Drawable
{
	public static String[] stageNames = {"TestingStage"};
	
	private World m_world = new World();
	private Sprite m_background;
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
	
	public void setBackground(Sprite p_background)
	{
		m_background = p_background;
	}
	
	public Sprite getBackground()
	{
		return m_background;
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{	
		m_background.setPosition(0,  0);
		m_background.draw(p_renderer);
	}
	
}
