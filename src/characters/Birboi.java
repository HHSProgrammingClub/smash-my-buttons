package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;

import graphics.Sprite;
import graphics.Texture;

public class Birboi extends Character
{
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

		setBody(birb);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/birboi");
		
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
		// TODO Create hitboxes
		AnimationState startup = new AnimationState("smash_startup");
		AnimationState flight  = new AnimationState("smash_fly", 1.5f);
		
		Effect smashFlight = new Effect(1.5f)
				{

					@Override
					public void effectStart()
					{
						addAnimation(flight);
						getBody().setLinearVelocity(7, 0); //TODO: account for direction the player is facing
					}
					
					@Override
					public void effectInterrupted()
					{
						getBody().setGravityScale(1);
					}

					@Override
					public void effectEnd()
					{
						getBody().setGravityScale(1);
					}
				};
		
		Effect smashStartup = new Effect(.4f)
				{

					@Override
					public void effectStart()
					{
						getBody().setLinearVelocity(0, 0);
						getBody().setGravityScale(0);
						addAnimation(startup);
					}
					
					@Override
					public void effectInterrupted()
					{
						getBody().setGravityScale(1);
					}
					
					@Override
					public void effectEnd()
					{
						addEffect(smashFlight);
					}
				};
		addEffect(smashStartup);
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
		return Character.characterNames[1];
	}
}
