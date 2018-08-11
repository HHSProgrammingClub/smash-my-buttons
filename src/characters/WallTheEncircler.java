package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Sprite;
import graphics.Texture;
import resourceManager.ResourceManager;

public class WallTheEncircler extends Character
{
	private static double position = 0;
	private float length = 1;
	private float height = 2.0f;
	
	public WallTheEncircler()
	{
		jumpImpulse = new Vector2(0, -15);
		runForce = new Vector2(15, 0);
		maxRunSpeed = 5.0f;
		
		Body swol = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		swol.setTransform(t);
		position += 1;
		
		Rectangle getRekt = new Rectangle(length, height);
		getRekt.translate(1, 1);
		swol.addFixture(getRekt);
		swol.setMass(MassType.FIXED_ANGULAR_VELOCITY);
		
		setBody(swol);
		
		Texture textile = ResourceManager.getResource(Texture.class, "resources/images/Wall");
	
		Sprite mountainDew = new Sprite(textile);
		mountainDew.setAnimation("idle");
		
		setSprite(mountainDew);
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
		return Character.characterNames[3];
	}

}
