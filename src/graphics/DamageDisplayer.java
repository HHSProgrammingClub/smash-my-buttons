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
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;

		//TODO: make sure things are centered ish or at least spaced evenly
		AffineTransform transform = new AffineTransform();
		transform.translate(m_player*((width/64)/6) + 0.5, (height/64) - 3.5); //someone clean this up
		
		AffineTransform shadowOffset = new AffineTransform();
		shadowOffset.translate(m_player*((width/64)/6) + 0.6, (height/64) - 3.4);
		
		//offset shadow for  a e s t h e t i c s
		p_renderer.pushTransform(shadowOffset);
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "SansSerif", Color.BLACK,  1, 0.25f);
		p_renderer.popTransform();
		
		//draw the damage
		p_renderer.pushTransform(transform);
		p_renderer.drawText(Integer.toString(m_character.getDamage()) + "%", "SansSerif", Color.BLACK,  1, 1);
		p_renderer.popTransform();
		
		AffineTransform nameOffset = new AffineTransform();
		nameOffset.translate(m_player*((width/64)/6), (height/64) - 3);
		nameOffset.scale(.5, .5);
		
		p_renderer.pushTransform(nameOffset);
		p_renderer.drawText(m_character.getName(), "SansSerif", Color.BLACK,  1, 1);
		p_renderer.popTransform();
		
		//TODO: render stock sprites
	}

}
