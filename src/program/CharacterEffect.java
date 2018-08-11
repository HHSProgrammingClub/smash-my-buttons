package program;

import java.awt.geom.AffineTransform;

import graphics.Drawable;
import graphics.Sprite;
import graphics.Texture;
import graphics.pages.Renderer;
import resourceManager.ResourceManager;

/*
 * Certain effects like explosions, flashes, that characters produce
 */
public class CharacterEffect implements Drawable
{
	private Sprite m_sprite;
	private AffineTransform m_transform/*ers*/; //robots in disguise!
	
	public CharacterEffect() {}
	
	public CharacterEffect(String p_name, String p_animation)
	{
		Texture texture = ResourceManager.getResource(Texture.class, "resources/images/" + p_name);
		
		m_sprite = new Sprite(texture);
		m_sprite.setAnimation(p_animation);
		
		m_transform = new AffineTransform();
	}
	
	public CharacterEffect(String p_name, String p_animation, AffineTransform p_transformer)
	{
		Texture texture = ResourceManager.getResource(Texture.class, "resources/images/" + p_name);
		
		m_sprite = new Sprite(texture);
		m_sprite.setAnimation(p_animation);
		
		m_transform = p_transformer;
	}
	
	public Sprite getSprite()
	{
		return m_sprite;
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		p_renderer.pushTransform(m_transform);
		m_sprite.draw(p_renderer);
		p_renderer.popTransform();
	}
}
