package stages;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;

import graphics.Drawable;
import graphics.Renderer;
import graphics.Sprite;

public class TerrainPiece implements Drawable
{
	private Body m_body;
	private Sprite m_sprite;
	
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
	
	@Override
	public void draw(Renderer p_renderer)
	{
		Transform t = m_body.getTransform();
		m_sprite.setPosition(t.getTranslationX(), t.getTranslationY());
		m_sprite.setRotation(t.getRotation());
		m_sprite.draw(p_renderer);
	}
	
}
