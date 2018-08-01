package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;

import graphics.Sprite;
import graphics.Texture;

//A sample character.

public class Jack extends Character
{	
	private static double position = 0;
	public Jack() 
	{
		Body tushie = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		tushie.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(1, 2);
		rect.translate(1, 1); // Set to topleft
		tushie.addFixture(rect);
		tushie.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(tushie);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/jack");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	//TODO: ascending and descending jump sprites for Jack
	
	public void jab()
	{
		
	}
	
	public void tilt()
	{
		
	}
	
	public void smash()
	{
		
	}
	
	public void projectile()
	{
		
	}
	
	public void signature()
	{
		
	}
	
	public void recover()
	{
		
	}

	@Override
	public String getName() 
	{
		return Character.characterNames[0];
	}
}
