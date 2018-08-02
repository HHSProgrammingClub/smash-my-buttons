
package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Sprite;
import graphics.Texture;
import characters.characterStates.*;

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
		
		CharacterState smashStartup = new CharacterState("smash_startup")
				{
					@Override
					public void init()
					{
						System.out.println("Lemme");
						getBody().setLinearVelocity(0, 0);
						getBody().setGravityScale(0);
					}
					
					@Override
					public void interrupt()
					{
						getBody().setGravityScale(1);
					}
					
					@Override
					public void end()
					{
						System.out.println("yo");
					}
				};
		
		CharacterState smashFlight = new CharacterState("smash_fly", 1.5f)
				{

					@Override
					public void init()
					{
						System.out.println("Smash");
						getBody().applyImpulse(new Vector2(7, 0)); //TODO: account for direction the player is facing
					}
					
					@Override
					public void interrupt()
					{
						getBody().setGravityScale(1);
					}

					@Override
					public void end()
					{
						getBody().setGravityScale(1);
						System.out.println("things");
					}
				};
		interruptStates(smashStartup);
		addState(smashFlight);
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
