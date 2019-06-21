package graphics;


import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import org.newdawn.slick.Color;

import characters.Character;
import graphics.pages.Renderer;
import resourceManager.ResourceManager;

/**
 * Displays a character's damage and number of lives (stocks)
 * @author Catherine Guevara
 *
 */
public class DamageDisplayer implements Drawable
{
	private Character m_character;
	private Sprite m_stockSprite;
	private int m_player;
	
	public DamageDisplayer(Character p_character, int p_port)
	{
		m_character = p_character;
		m_player = p_port;
		
		Texture tex = ResourceManager.getResource(Texture.class, "resources/images/stock");
		
		m_stockSprite = new Sprite(tex);
		m_stockSprite.setAnimation("default");
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		//offset shadow for  a e s t h e t i c s
		p_renderer.pushTransform()
			.translate(0.1f, 0);
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "Consolas", Color.black,  1, 0.25f);
		p_renderer.popTransform();
		
		//draw the damage
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "Consolas", Color.black,  1, 1);	
		
		//render the name labels
		p_renderer.pushTransform()
			.translate(0, 0.4f)
			.scale(0.5f, 0.5f);
		p_renderer.drawText(m_character.getName(), "Consolas", Color.black,  1, 1);
		p_renderer.popTransform();
		
		//render the stocks
		for(int i = 0; i < m_character.getStock(); i++)
		{
			p_renderer.pushTransform()
				.translate(0 + i, 0.4f)
				.scale(2, 2);
			
			m_stockSprite.draw(p_renderer);
			p_renderer.popTransform();
		}
		
		//pop goes the weasel
		p_renderer.popTransform();
		
	}

}
