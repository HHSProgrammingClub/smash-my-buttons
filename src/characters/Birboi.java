
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
import program.Projectile;
import characters.characterStates.*;

public class Birboi extends Character
{
	public Birboi()
	{
		jumpImpulse = new Vector2(0, -15);
		runForce = new Vector2(20, 0);
		maxRunSpeed = 6.5f;
		
		Body birb = new Body();
		
		Transform t = new Transform();
		t.setTranslation(position, 0);
		birb.setTransform(t);
		position += 1;
		
		// Add the collision fixture
		Rectangle rect = new Rectangle(1, 1.4f);
		rect.translate(1, 1.3); // Set to topleft
		BodyFixture bf = new BodyFixture(rect);
		bf.setDensity(0.65);
		birb.addFixture(rect);
		birb.setMass(MassType.FIXED_ANGULAR_VELOCITY);

		setBody(birb);
		
		Texture tex = new Texture();
		tex.openResource("resources/images/birboi");
		
		Sprite sp = new Sprite(tex);
		sp.setAnimation("idle");
		
		setSprite(sp);
		
		jumpImpulse = new Vector2(0, -17); //TODO: multiple jumps
	}
	
	@Override
	public void jab()
	{
		Hitbox jabBox = new Hitbox();
		
		jabBox.setBaseKnockback(new Vector2(0, -3));
		jabBox.setScaledKnockback(alignFacing(new Vector2(0, -5)));
		jabBox.setDamage(2);
		jabBox.setDuration(10);
		jabBox.setHitstun(0f);
		
		Vector2 jabBoxPos    = new Vector2(1, 1);
		Vector2 jabBoxOffset = new Vector2(.5, 0);
		
		Rectangle r = new Rectangle(.7, 1.2);
		r.translate(jabBoxPos.add(alignFacing(jabBoxOffset)));
		
		BodyFixture f = new BodyFixture(r);
		jabBox.addToFixture(f);
		
		CharacterState jabState = new CharacterState("jab", .25f)
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
		
		tiltBoxFront.setBaseKnockback(alignFacing(new Vector2(7, 4)));
		tiltBoxFront.setScaledKnockback(alignFacing(new Vector2(3.5, 2.5)));
		tiltBoxFront.setDamage(4);
		tiltBoxFront.setDuration(30);
		tiltBoxFront.setHitstun(.1f);
		
		Vector2 tiltBoxPos    = new Vector2(1, 1.5);
		Vector2 tiltBoxOffset = new Vector2(.5, 0);
		
		Rectangle r = new Rectangle(0.5, 1);
		r.translate(tiltBoxPos.add(alignFacing(tiltBoxOffset)));
		
		BodyFixture f = new BodyFixture(r);
		tiltBoxFront.addToFixture(f);
		
		
		
		Hitbox tiltBoxBack = new Hitbox();
		
		tiltBoxBack.setBaseKnockback(alignFacing(new Vector2(7, -3)));
		tiltBoxBack.setScaledKnockback(alignFacing(new Vector2(3.5, -4)));
		tiltBoxBack.setDamage(3);
		tiltBoxBack.setDuration(30);
		tiltBoxBack.setHitstun(.1f);
		
		Vector2 tiltBoxPosBack    = new Vector2(1, 1.5);
		Vector2 tiltBoxOffsetBack = new Vector2(-.5, 0);
		
		Rectangle rb = new Rectangle(r.getWidth(), r.getHeight());
		rb.translate(tiltBoxPosBack.add(alignFacing(tiltBoxOffsetBack)));
		
		BodyFixture fb = new BodyFixture(rb);
		tiltBoxBack.addToFixture(fb);
		
		CharacterState tiltState = new CharacterState("tilt")
		{
			@Override
			protected void init()
			{
				m_body.applyImpulse(new Vector2(1.5 * getFacing(), 0));
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
		pushState(new WaitState(0.2f));
		pushState(tiltState);
	}
	
	@Override
	public void smash()
	{
		float duration = .4f;
		
		CharacterState smashContact = new CharacterState("smash_contact", .4f)
		{
			final Vector2 coolDownImpulse = new Vector2(-1.5, -8);
			
			@Override
			protected void init()
			{
				m_body.setLinearVelocity(0, 0);
				m_body.applyImpulse(alignFacing(coolDownImpulse));
			}
		};
		
		CharacterState smashStartup = new CharacterState("smash_startup")
				{
					@Override
					public void init()
					{
						getBody().setLinearVelocity(0, 0);
						getBody().setGravityScale(0.1);
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
					
					@Override
					public void init()
					{
						getBody().applyImpulse(alignFacing(m_baseImpulse));
						m_hitbox.setDamage(9);
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
						interrupt();
					}
					
					@Override
					protected void onUpdate()
					{
						if(!m_hitbox.isAlive() && getTimer() > 0)
						{
							popState();
							pushState(smashContact);
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
		Hitbox signatureBox = new Hitbox();
		
		signatureBox.setBaseKnockback(alignFacing(new Vector2(4, 3)));
		signatureBox.setScaledKnockback(alignFacing(new Vector2(2, 3)));
		signatureBox.setDamage(8);
		signatureBox.setDuration(30);
		signatureBox.setHitstun(.4f);
		
		Vector2 signatureBoxPos    = new Vector2(1, 1.3);
		Vector2 signatureBoxOffset = new Vector2(0, 0);
		
		Rectangle sr = new Rectangle(1, 1.4);
		sr.translate(signatureBoxPos.add(alignFacing(signatureBoxOffset)));
		
		BodyFixture sf = new BodyFixture(sr);
		signatureBox.addToFixture(sf);
		
		CharacterState signatureContact = new CharacterState("smash_contact", .3f)
		{
			private final Vector2 imp = new Vector2(-3, -4);
			
			@Override
			protected void init()
			{
				m_body.setLinearVelocity(0, 0);
				m_body.applyImpulse(alignFacing(imp));
			}
		};
		
		CharacterState signatureState = new CharacterState("signature", -1)
		{
			public Projectile[] shockwaves = new Projectile[2];
			@Override
			protected void init()
			{
				m_body.setGravityScale(0);
				m_body.setLinearVelocity(alignFacing(new Vector2(1.75, 8)));
				m_body.addFixture(sf);
				addHitbox(signatureBox);
			}
			
			@Override
			public void interrupt()
			{
				m_body.setGravityScale(1);
				m_body.removeFixture(sf);
				removeHitbox(signatureBox);
			}
			
			@Override
			public void end()
			{
				m_body.setGravityScale(1);
				m_body.removeFixture(sf);
				removeHitbox(signatureBox);
			}
			
			private void spawnShockwaves()
			{
				for(int i = 0; i < shockwaves.length; i++)
				{
					int flip = (i == 0 ? 1 : -1);
					
					Texture shockwave = new Texture();
					shockwave.openResource("resources/images/" + (i == 0 ? "shockwave_L" : "shockwave_R"));
					Sprite sp = new Sprite(shockwave);
					sp.setAnimation("default");
					
					Hitbox box = new Hitbox();
					box.setBaseKnockback(alignFacing(new Vector2(1 * flip, -2)));
					box.setScaledKnockback(alignFacing(new Vector2(.5 * flip, -1)));
					box.setDamage(3);
					box.setDuration(0.5f);
					box.setHitstun(.25f);
					
					Vector2 offset = new Vector2(1 + .6 * flip, 1.5);
					
					Rectangle r = new Rectangle(.3, 1);
					
					Body b = new Body();
					b.addFixture(new BodyFixture(r));
					b.setMassType(MassType.INFINITE);
					
					Transform t = new Transform();
					t.translate(m_body.getTransform().getTranslation());
					t.translate(offset);
					b.setTransform(t);
					
					Projectile p = new Projectile();
					p.setCharacter((Character) m_body.getUserData());
					p.setHitbox(box);
					p.setBody(b);
					p.setSprite(sp);
					
					p.getBody().setLinearVelocity(3 * flip, 0);
					
					m_world.addBody(b);
					
					addProjectile(p);
				}
			}
			
			private void contactGround()
			{
				spawnShockwaves();
				popState();
			}
			
			private void contactPlayer()
			{
				popState();
				pushState(signatureContact);
			}
			
			@Override
			protected void onUpdate()
			{
				if(m_body.getLinearVelocity().y == 0)
					contactGround();
				if(!signatureBox.isAlive())
					contactPlayer();
			}
		};
		
		CharacterState signatureStartup = new CharacterState("jump_asc", .25f)
		{
			@Override
			protected void init()
			{
				if(m_jumped)
					popState();
				else
					m_body.applyImpulse(alignFacing(new Vector2(.7, -17)));
			}
		};
		pushState(new WaitState(0.4f));
		pushState(signatureState);
		pushState(signatureStartup);
	}
	
	@Override
	public void recover() 
	{
		Hitbox recoverBox = new Hitbox();
		
		recoverBox.setBaseKnockback(alignFacing(new Vector2(2, 4)));
		recoverBox.setScaledKnockback(alignFacing(new Vector2(.1, 4)));
		recoverBox.setDamage(4);
		recoverBox.setDuration(10);
		recoverBox.setHitstun(0);
		
		Vector2 recoverBoxPos = new Vector2(1, 1.8);
		
		Rectangle rr = new Rectangle(3.2, 2.4);
		rr.translate(recoverBoxPos);
		
		BodyFixture rf = new BodyFixture(rr);
		recoverBox.addToFixture(rf);
		
		CharacterState recoveryStartup = new CharacterState("recovery", .6f)
		{
			private float delay = .3f;
			private boolean jumped = false;
			
			@Override
			protected void init()
			{
				m_body.setLinearVelocity(0, 0);
				m_body.setGravityScale(0);
				m_superArmour = true;
				
				m_body.addFixture(rf);
				addHitbox(recoverBox);
			}
			
			@Override
			public void interrupt()
			{
				m_recovered = true;
			}
			
			@Override
			public void end()
			{
				m_body.setGravityScale(1);
				m_superArmour = false;
				
				m_body.removeFixture(rf);
				removeHitbox(recoverBox);
				m_recovered = true;
			}
			
			@Override
			protected void onUpdate()
			{
				if(!jumped && (getDuration() - getTimer()) >= delay)
				{
					m_body.applyImpulse(new Vector2(0, -10));
					jumped = true;
				}
			}
		};
		
		if(!m_recovered)
		{
			pushState(new JumpState(false));
			pushState(recoveryStartup);
		}
	}

	@Override
	public String getName()
	{
		return Character.characterNames[1];
	}
}
