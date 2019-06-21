package stages;



import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

import graphics.Drawable;
import graphics.Sprite;
import graphics.pages.Renderer;

public class TerrainPiece implements Drawable
{
	private Body m_body;
	private Sprite m_sprite;
	private Vector2 m_position = new Vector2();
	
	public TerrainPiece() {}
	
	public void setBody(Body p_body)
	{
		m_body = p_body;
		m_body.setUserData(this);
	}
	
	public Body getBody()
	{
		return m_body;
	}
	
	public void setSprite(Sprite p_sprite)
	{
		m_sprite = p_sprite;
	}
	
	public void setPosition(Vector2 p_position)
	{
		m_position.set(p_position);
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		m_sprite.setPosition(m_position);
		assert(m_body != null);
		m_sprite.setRotation(m_body.getTransform().getRotation());
		m_sprite.draw(p_renderer);
	}
	
}
