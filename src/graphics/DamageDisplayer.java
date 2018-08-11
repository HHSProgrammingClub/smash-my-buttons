package graphics;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

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
		int height = p_renderer.getHeight();
		AffineTransform transform = new AffineTransform();
		transform.translate(m_player*3.5, 8); 
		p_renderer.pushTransform(transform);
		
		//offset shadow for  a e s t h e t i c s
		AffineTransform shadowOffset = new AffineTransform();
		shadowOffset.translate(0.1, 0);
		p_renderer.pushTransform(shadowOffset);
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "SansSerif", Color.BLACK,  1, 0.25f);
		p_renderer.popTransform();
		
		//draw the damage
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "SansSerif", Color.BLACK,  1, 1);	
		
		//render the name labels
		AffineTransform nameOffset = new AffineTransform();
		nameOffset.translate(0, 0.4); // FIXME: account for name lengths
		nameOffset.scale(.5, .5);
		p_renderer.pushTransform(nameOffset);
		p_renderer.drawText(m_character.getName(), "SansSerif", Color.BLACK,  1, 1);
		p_renderer.popTransform();
		
		//render the stocks
		for(int i = 0; i < m_character.getStock(); i++)
		{
			AffineTransform stockOffset = new AffineTransform();
			stockOffset.translate(0 + i, 0.4); // FIXME: make these even and next to name
			stockOffset.scale(2, 2);
			p_renderer.pushTransform(stockOffset);
			
			m_stockSprite.draw(p_renderer);
			p_renderer.popTransform();
		}
		
		//pop goes the weasel
		p_renderer.popTransform();
		
		//TODO: render stocks then render name label next to stocks depending on name length
	}

}
