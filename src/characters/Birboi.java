
package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Sprite;
import graphics.Texture;
import program.Hitbox;
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
		Rectangle rect = new Rectangle(1, 1.4f);
		rect.translate(1, 1.35); // Set to topleft
		birb.addFixture(rect);
		birb.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(birb);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/birboi");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
		
		jumpImpulse = new Vector2(0, -25);
	}
	
	@Override
	public void jab()
	{
		Hitbox jabBox = new Hitbox();
		
		jabBox.setBaseKnockback(new Vector2());
		jabBox.setScaledKnockback(alignFacing(new Vector2(0, -1)));
		jabBox.setDamage(6);
		jabBox.setDuration(10);
		jabBox.setHitstun(.15f);
		
		Vector2 jabBoxPos    = new Vector2(1, 1);
		Vector2 jabBoxOffset = new Vector2(.5, 0);
		
		Rectangle r = new Rectangle(.7, 1.2);
		r.translate(jabBoxPos.add(alignFacing(jabBoxOffset)));
		
		BodyFixture f = new BodyFixture(r);
		jabBox.addToFixture(f);
		
		CharacterState jabState = new CharacterState("jab", .2f)
		{
			@Override
			protected void init()
			{
				m_body.addFixture(f);
				addHitbox(jabBox);
			}
			
			@Override
			public void interrupt()
			{
				end();
			}
			
			@Override
			public void end()
			{
				m_body.removeFixture(f);
				removeHitbox(jabBox);
			}
		};
		
		pushState(jabState);
	}
	
	@Override
	public void tilt() 
	{
		Hitbox tiltBoxFront = new Hitbox();
		
		tiltBoxFront.setBaseKnockback(alignFacing(new Vector2(1.5, 4)));
		tiltBoxFront.setScaledKnockback(alignFacing(new Vector2(.2, 3.6)));
		tiltBoxFront.setDamage(9);
		tiltBoxFront.setDuration(10);
		tiltBoxFront.setHitstun(.1f);
		
		Vector2 tiltBoxPos    = new Vector2(1, 1.3);
		Vector2 tiltBoxOffset = new Vector2(1, 0);
		
		Rectangle r = new Rectangle(1.1, 1.4);
		r.translate(tiltBoxPos.add(alignFacing(tiltBoxOffset)));
		
		BodyFixture f = new BodyFixture(r);
		tiltBoxFront.addToFixture(f);
		
		
		
		Hitbox tiltBoxBack = new Hitbox();
		
		tiltBoxBack.setBaseKnockback(alignFacing(new Vector2(2, -3)));
		tiltBoxBack.setScaledKnockback(alignFacing(new Vector2(.3, -4)));
		tiltBoxBack.setDamage(11);
		tiltBoxBack.setDuration(10);
		tiltBoxBack.setHitstun(.3f);
		
		Vector2 tiltBoxPosBack    = new Vector2(1, 1.3);
		Vector2 tiltBoxOffsetBack = new Vector2(-1, 0);
		
		Rectangle rb = new Rectangle(1.1, 1.4);
		rb.translate(tiltBoxPosBack.add(alignFacing(tiltBoxOffsetBack)));
		
		BodyFixture fb = new BodyFixture(rb);
		tiltBoxBack.addToFixture(fb);
		
		CharacterState tiltState = new CharacterState("tilt")
		{
			@Override
			protected void init()
			{
				m_body.addFixture(f);
				addHitbox(tiltBoxFront);
				m_body.addFixture(fb);
				addHitbox(tiltBoxBack);
			}
			
			@Override
			public void interrupt()
			{
				end();
			}
			
			@Override
			public void end()
			{
				m_body.removeFixture(f);
				removeHitbox(tiltBoxFront);
				m_body.removeFixture(fb);
				removeHitbox(tiltBoxBack);
			}
		};
		
		pushState(tiltState);
	}
	
	@Override
	public void smash()
	{
		// TODO Create hitboxes
		float duration = .4f;
		
		CharacterState smashStartup = new CharacterState("smash_startup")
				{
					@Override
					public void init()
					{
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
						
					}
				};
		
		CharacterState smashFlight = new CharacterState("smash_fly", duration)
				{
					Hitbox m_hitbox = new Hitbox();
					Rectangle m_rect = new Rectangle(.6, 1.7);
					BodyFixture m_fixture;
					
					final Vector2 m_baseImpulse     = new Vector2(16, 0);
					final Vector2 m_baseKnockback   = new Vector2(8, -5);
					final Vector2 m_scaledKnockback = new Vector2(1.5, -2);
					
					final Vector2 m_hitboxBasePos   = new Vector2(1, 1.25);
					final Vector2 m_hitboxOffsetPos = new Vector2(.4, 0);
					
					final Vector2 coolDownImpulse = new Vector2(-1.5, -8);
					
					@Override
					public void init()
					{
						getBody().applyImpulse(alignFacing(m_baseImpulse));
						m_hitbox.setDamage(15);
						m_hitbox.setBaseKnockback(alignFacing(m_baseKnockback));
						m_hitbox.setScaledKnockback(alignFacing(m_scaledKnockback));
						m_hitbox.setDuration(duration);
						
						m_rect.translate(m_hitboxBasePos.add(alignFacing(m_hitboxOffsetPos)));
						m_fixture = new BodyFixture(m_rect);
						getBody().addFixture(m_fixture);
						m_hitbox.addToFixture(m_fixture);
						addHitbox(m_hitbox);
					}
					
					@Override
					public void interrupt()
					{
						getBody().removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						getBody().setGravityScale(1);
					}

					@Override
					public void end()
					{
						getBody().removeFixture(m_fixture);
						removeHitbox(m_hitbox);
						getBody().setGravityScale(1);
					}
					
					@Override
					protected void onUpdate()
					{
						if(!m_hitbox.isAlive() && getTimer() > 0)
						{
							setDuration(0);
							m_body.setLinearVelocity(0, 0);
							m_body.applyImpulse(alignFacing(coolDownImpulse));
						}
					}
				};
		pushState(smashFlight);
		pushState(smashStartup);
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
