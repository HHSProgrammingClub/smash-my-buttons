package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;

import graphics.Sprite;
import graphics.Texture;

public class Edgewardo extends Character
{

	public Edgewardo()
	{
		Body emo = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		emo.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(1, 2);
		rect.translate(1, 1); // Set to topleft
		emo.addFixture(rect);
		emo.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(emo);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/edgewardo");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
	}
	
	@Override
	public void jab() 
	{
		// TODO Auto-generated method stub
		m_sprite.setAnimation("jab");
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
		m_sprite.setAnimation("smash");
	}

	@Override
	public void projectile()
	{
		// TODO Auto-generated method stub
		m_sprite.setAnimation("projectile");
	}

	@Override
	public void signature()
	{
		// TODO Auto-generated method stub
		m_sprite.setAnimation("signature");
	}

	@Override
	public void recover() 
	{
		// TODO Auto-generated method stub
		m_sprite.setAnimation("recovery");
	}

}
