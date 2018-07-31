package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;

import graphics.Sprite;
import graphics.Texture;
import program.Battle;

public class Birboi extends Character
{
	private static double position = 0;
	
	public Birboi()
	{
		Body birb = new Body();

		Transform t = new Transform();
		t.setTranslation(position, 0);
		birb.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(1, 2);
		rect.translate(1, 1); // Set to topleft
		birb.addFixture(rect);
		birb.setMass(MassType.FIXED_ANGULAR_VELOCITY);
		
		//birb.setLinearVelocity(2, 5);
		setBody(birb);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/birboi");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}

	@Override
	public void jab(Battle p_battle)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tilt(Battle p_battle) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void smash(Battle p_battle)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectile(Battle p_battle)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void signature(Battle p_battle) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recover(Battle p_battle) 
	{
		// TODO Auto-generated method stub
		
	}
}
