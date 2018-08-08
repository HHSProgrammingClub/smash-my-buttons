package graphics;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import characters.Character;
import program.CharacterController;
import program.PlayerController;
import pythonAI.AIController;

/**
 * Displays the name of player on top of characters
 * @author Catherine Guevara
 */
public class NameLabel implements Drawable
{
	private Sprite arrow;
	
	private Character m_character;
	private String name;
	private int m_port;
	
	/**
	 * Display a name label for a player
	 * @param p_controller the CharacterController's character to display above
	 * @param p_port player 1 or 2
	 */
	public NameLabel(CharacterController p_controller, int p_port)
	{
		Texture arrowTex = new Texture();
		arrowTex.openResource("resources/images/arrow");
		
		arrow = new Sprite(arrowTex, "default");
		
		m_character = p_controller.getCharacter();
		m_port = p_port;
		
		if(p_controller instanceof PlayerController)
			name = (p_port == 1 ? "Player 1" : "Player 2");
		else
			name = ((AIController) p_controller).getName();
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		Vector2 position = m_character.getBody().getWorldCenter();
		AffineTransform textScalar = new AffineTransform();
		Transform t = m_character.getBody().getTransform();
		int offset = 1;
		
		//TODO: refactor later for stages with different boundaries
		if(position.x < 0) //far left
		{
			arrow.setPosition(0.5, t.getTranslation().y + offset);
			arrow.setRotation(Math.PI/2); //90 degrees point left
			
			textScalar.translate(0.5, t.getTranslation().y + offset);
		}
		else if(position.x > 13) //far right
		{
			arrow.setPosition(11.5, t.getTranslation().y + offset);
			arrow.setRotation(Math.PI*3/2); //270 degrees pointing right
			
			textScalar.translate(10.5, t.getTranslation().y + offset);
		}
		else if(position.y < 0) //far up
		{
			arrow.setPosition(t.getTranslation().x + offset, 0.5);
			arrow.setRotation(Math.PI); //180 degrees pointing up
			
			textScalar.translate(t.getTranslation().x + offset, 0.5);
		}
		else if(position.y > 9) //far down
		{
			arrow.setPosition(t.getTranslation().x, 8.5);
			textScalar.translate(t.getTranslation().x, 8);
		}
		else
		{
			arrow.setPosition(t.getTranslation().x + 0.75, t.getTranslation().y);
			arrow.setRotation(0);
			
			textScalar.translate(t.getTranslation().x + 0.5, t.getTranslation().y);
		}
		
		arrow.draw(p_renderer);
		
		textScalar.scale(0.25, 0.25);
		p_renderer.pushTransform(textScalar);
		
		if(m_port == 1)
			p_renderer.drawText(name, "SansSerif", Color.BLUE, 1, 1);
		else
			p_renderer.drawText(name, "SansSerif", Color.RED, 1, 1);
		
		p_renderer.popTransform();
	}
}
