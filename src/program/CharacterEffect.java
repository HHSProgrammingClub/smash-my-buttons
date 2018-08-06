package program;

import java.awt.geom.AffineTransform;

import graphics.Drawable;
import graphics.Renderer;
import graphics.Sprite;

/*
 * Certain effects like explosions, flashes, that characters produce
 */
public class CharacterEffect implements Drawable
{
	private Sprite m_sprite;
	private AffineTransform m_transform/*ers*/; //robots in disguise!
	
	public CharacterEffect() {}
	
	public CharacterEffect(Sprite p_sprite)
	{
		m_sprite = p_sprite;
		m_transform = new AffineTransform();
	}
	
	public CharacterEffect(Sprite p_sprite, AffineTransform p_transformer)
	{
		m_sprite = p_sprite;
		m_transform = p_transformer;
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		p_renderer.pushTransform(m_transform);
		m_sprite.draw(p_renderer);
		p_renderer.popTransform();
	}
}
