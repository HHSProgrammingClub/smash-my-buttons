package stages;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

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
		Transform transform = new Transform();
		transform.setTranslation(t.getTranslationX() * 32, t.getTranslationY() * 32);
		p_renderer.setTransform(transform);
		m_sprite.draw(p_renderer);
	}
	
}
