package graphics;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import gameObject.SpriteComponent;
import graphics.pages.Renderer;
import resourceManager.ResourceManager;

// TODO: make this a GameObject instead

/**
 * Displays a character's damage and number of lives (stocks)
 * @author Catherine Guevara
 *
 */
/*public class DamageDisplayer
{
	private Character m_character;
	private SpriteComponent m_stockSprite;
	private int m_player;
	
	public DamageDisplayer(Character p_character, int p_port)
	{
		m_character = p_character;
		m_player = p_port;
		
		Texture tex = ResourceManager.getResource(Texture.class, "resources/images/stock");
		
		m_stockSprite = new SpriteComponent(tex);
		m_stockSprite.setAnimation("default");
	}
	
	public void draw(Renderer p_renderer)
	{
		//translate the damage displaye depending on the player
		AffineTransform transform = new AffineTransform();
		transform.translate(m_player*3.5, 8); 
		p_renderer.pushTransform(transform);
		
		//offset shadow for  a e s t h e t i c s
		AffineTransform shadowOffset = new AffineTransform();
		shadowOffset.translate(0.1, 0);
		p_renderer.pushTransform(shadowOffset);
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "Consolas", Color.BLACK,  1, 0.25f);
		p_renderer.popTransform();
		
		//draw the damage
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "Consolas", Color.BLACK,  1, 1);	
		
		//render the name labels
		AffineTransform nameOffset = new AffineTransform();
		nameOffset.translate(0, 0.4);
		nameOffset.scale(.5, .5);
		p_renderer.pushTransform(nameOffset);
		p_renderer.drawText(m_character.getName(), "Consolas", Color.BLACK,  1, 1);
		p_renderer.popTransform();
		
		//render the stocks
		for(int i = 0; i < m_character.getStock(); i++)
		{
			AffineTransform stockOffset = new AffineTransform();
			stockOffset.translate(0 + i, 0.4);
			stockOffset.scale(2, 2);
			p_renderer.pushTransform(stockOffset);
			
			m_stockSprite.draw(p_renderer);
			p_renderer.popTransform();
		}
		
		//pop goes the weasel
		p_renderer.popTransform();
		
	}

}*/
