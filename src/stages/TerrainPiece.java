package stages;

import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Transform;

import graphics.Drawable;
import graphics.Sprite;
import graphics.pages.Renderer;

public class TerrainPiece implements Drawable
{
	private Body m_body;
	private Sprite m_sprite;
	private AffineTransform m_transform;
	
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
	
	public void setTransform(AffineTransform p_transform)
	{
		m_transform = p_transform;
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		Transform t = m_body.getTransform();
		p_renderer.pushTransform(m_transform);
		//m_sprite.setPosition(t.getTranslationX(), t.getTranslationY());
		m_sprite.setRotation(t.getRotation());
		m_sprite.draw(p_renderer);
		p_renderer.popTransform();
	}
	
}
