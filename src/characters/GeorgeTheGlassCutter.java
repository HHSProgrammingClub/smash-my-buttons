package characters;
//A sample character.
import java.util.ArrayList;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

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
		super();
		Body tushie = new Body();
		// Make it not be able to rotate
		tushie.setMass(MassType.FIXED_ANGULAR_VELOCITY);
		// Add the collision fixture
		BodyFixture hurtbox = new BodyFixture(new Rectangle(64, 64)); //this will be very, very big
		hurtbox.setDensity(20);
		hurtbox.setFriction(0.5);
		hurtbox.setRestitution(0.9);
		tushie.addFixture(hurtbox);
		super.setBody(tushie);
	}
}
