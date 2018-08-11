package graphics;

import java.awt.Color;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;

import graphics.pages.Renderer;
import resourceManager.ResourceManager;
import characters.Character;

public class DeathAnimation implements Drawable
{
	private Sprite death;
	private Vector2 m_position;
	private float m_duration = 0.5f;
	
	/**
	 * D E A T H  C O M E S  T O  U S  A L L 
	 * @param p_character the character that died
	 */
	public DeathAnimation(Character p_character)
	{
		Texture LaTeX = ResourceManager.getResource(Texture.class, "resources/images/death");
		
		death = new Sprite(LaTeX);
		death.setAnimation("default");
	}
	
	/**
	 * Set the position to where the character died at
	 * @param p_position
	 */
	public void setPosition(Vector2 p_position)
	{
		m_position = p_position;
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{	
		setDirection();
		death.setPosition(m_position);
		death.setScale(3, 3);
		death.draw(p_renderer);
		
		m_duration -= 0.1f;
		
		if(m_duration > 0)
			p_renderer.addScreenOverlay(Color.RED, m_duration);
		else
			p_renderer.addScreenOverlay(Color.RED, 0);
	}
	
	/**
	 * Finds which side the character died at
	 */
	private void setDirection()
	{
		double x = m_position.x;
		double y = m_position.y;
		
		double offset = 0.5;
		
		//TODO: account for different blast lines of various stages
		if(x < 0) //left side --- default
		{
			death.setRotation(0);
			m_position.add(new Vector2(0.1, 0));
		}
		else if(x > 12) //right
		{
			death.setRotation(Math.PI);
			m_position.add(new Vector2(-0.1, 0));
		}
		else if(y < 0) //top
		{
			death.setRotation(Math.PI/2);
			m_position.add(new Vector2(0, offset));
		}
		else if(y > 10) //bottom
		{
			death.setRotation(Math.PI*3/2);
			m_position.add(new Vector2(0, -offset));
		}
	}
}
