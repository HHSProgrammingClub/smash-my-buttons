package characters;
//A sample character.
import java.util.ArrayList;

import program.Battle;
import program.Hitbox;
import program.Move;

public class GeorgeTheGlassCutter extends Character
{
	//a showcasing of different ways to make a character
	protected Hitbox[][] moveHitboxes = {
		{new Hitbox()},
	};
	
	protected Move[] m_moveset = {
		// You can assign hitboxes in the constructor
		new Move("Score", 1.0f, new Hitbox[] {}),
		// Or in an external array
		new Move("Score", 1.0f, moveHitboxes[0]),
		//Or you could just use an inherited class (necessary for move effects)
		new Move("Score", 1.0f, moveHitboxes[0]),
		new Move("Score", 1.0f, moveHitboxes[0]),
		new Move("Score", 1.0f, moveHitboxes[0]),
		new Move("Score", 1.0f, moveHitboxes[0])
	};
	
	public GeorgeTheGlassCutter() 
	{
		
	}
}
