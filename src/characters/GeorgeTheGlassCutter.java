package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
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
		// Make it non-rotating
		Transform t = new Transform();
		t.setTranslation(position, 100);
		tushie.setTransform(t);
		position += 100;
		// Add the collision fixture
		/*BodyFixture hurtbox = new BodyFixture(new Rectangle(64, 64)); //this will be very, very big
		hurtbox.setDensity(1);
		hurtbox.setFriction(0.5);
		hurtbox.setRestitution(0.9);*/
		tushie.addFixture(new Rectangle(2, 2));
		tushie.setMass(MassType.NORMAL);
		//tushie.setLinearVelocity(2, 5);
		setBody(tushie);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/birboi");
		
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
