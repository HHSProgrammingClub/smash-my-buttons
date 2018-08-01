package graphics;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import characters.Character;

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
		
		Texture tex = new Texture();
		tex.openResource("resources/images/stock");
		
		m_stockSprite = new Sprite(tex);
		m_stockSprite.setAnimation("default");
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		//TODO: make sure things are centered ish or at least spaced evenly
		AffineTransform transform = new AffineTransform();
		transform.translate(m_player*2 + 0.5, 12.5); // FIXME: someone clean this up
		p_renderer.pushTransform(transform);
		
		//offset shadow for  a e s t h e t i c s
		AffineTransform shadowOffset = new AffineTransform();
		shadowOffset.translate(0.1, 0);
		p_renderer.pushTransform(shadowOffset);
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "SansSerif", Color.BLACK,  1, 0.25f);
		p_renderer.popTransform();
		
		//draw the damage
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "SansSerif", Color.BLACK,  1, 1);	
		
		AffineTransform nameOffset = new AffineTransform();
		nameOffset.translate(0, 0.4);
		nameOffset.scale(.5, .5);
		p_renderer.pushTransform(nameOffset);
		p_renderer.drawText(m_character.getName(), "SansSerif", Color.BLACK,  1, 1);
		p_renderer.popTransform();
		
		for(int i = 0; i < m_character.getStock(); i++)
		{
			AffineTransform stockOffset = new AffineTransform();
			stockOffset.translate(0, 0.4); // TODO: Adjust this offset
			p_renderer.pushTransform(stockOffset);
			
			m_stockSprite.draw(p_renderer);
			p_renderer.popTransform();
		}
		p_renderer.popTransform();
	}

}
