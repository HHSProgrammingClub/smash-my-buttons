package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;

import graphics.Sprite;
import graphics.Texture;
import program.Battle;

//A sample character.

public class GeorgeTheGlassCutter extends Character
{	
	private static double position = 0;
	public GeorgeTheGlassCutter() 
	{
		Body tushie = new Body();
		// TODO: Make it non-rotating
		Transform t = new Transform();
		t.setTranslation(position, 0);
		tushie.setTransform(t);
		position += 1;
		// Add the collision fixture
		Rectangle rect = new Rectangle(1, 2);
		rect.translate(1, 1); // Set to topleft
		tushie.addFixture(rect);
		tushie.setMass(MassType.FIXED_ANGULAR_VELOCITY);
		//tushie.setLinearVelocity(2, 5);
		setBody(tushie);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/jack");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	public void jab(Battle p_battle)
	{
		
	}
	
	public void tilt(Battle p_battle)
	{
		
	}
	
	public void smash(Battle p_battle)
	{
		
	}
	
	public void projectile(Battle p_battle)
	{
		
	}
	
	public void signature(Battle p_battle)
	{
		
	}
	
	public void recover(Battle p_battle)
	{
		
	}
}
