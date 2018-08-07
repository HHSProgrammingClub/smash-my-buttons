package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Sprite;
import graphics.Texture;

public class Cam extends Character
{
	private static double position = 0;
	private float length = 1;
	private float height = 1.5f;
	
	public Cam() 
	{
		//attribute editing
		jumpImpulse = new Vector2(0, -15);
		runForce = new Vector2(25, 0);
		maxRunSpeed = 4.0f;
		
		Body nikon = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		nikon.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(length, height);
		rect.translate(1, 1.25); // Set to topleft
		nikon.addFixture(rect);
		nikon.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(nikon);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/cam");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	@Override
	public void jab()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tilt() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void smash()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectile() 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void signature()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recover()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName()
	{
		return Character.characterNames[0];
	}

}
