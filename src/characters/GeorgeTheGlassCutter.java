package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

import program.Battle;

//A sample character.

public class GeorgeTheGlassCutter extends Character
{	
	public GeorgeTheGlassCutter() 
	{
		Body tushie = new Body();
		// Make it non-rotating
		tushie.setMass(MassType.FIXED_ANGULAR_VELOCITY);
		// Add the collision fixture
		BodyFixture hurtbox = new BodyFixture(new Rectangle(64, 64)); //this will be very, very big
		hurtbox.setDensity(20);
		hurtbox.setFriction(0.5);
		hurtbox.setRestitution(0.9);
		tushie.addFixture(hurtbox);
		setBody(tushie);
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
