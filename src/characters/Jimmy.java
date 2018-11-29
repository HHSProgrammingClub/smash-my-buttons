package characters;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Sprite;
import graphics.Texture;
import program.Hitbox;
import resourceManager.ResourceManager;
import characters.characterStates.*;

public class Jimmy extends Character
{
	public Jimmy()
	{
		jumpImpulse = new Vector2(0, -15);
		runForce    = new Vector2(15, 0);
		
		Body owo = new Body();
		
		Transform t = owo.getTransform();
		t.setTranslation(4, 0);
		owo.setTransform(t);
		
		Rectangle rawr = new Rectangle(1, 1.3);
		rawr.translate(1, 1.2);
		
		BodyFixture xd = new BodyFixture(rawr);
		xd.setDensity(1);
		
		owo.addFixture(xd);
		owo.setMass(MassType.FIXED_ANGULAR_VELOCITY);
		
		setBody(owo);
		
		Texture texinator = ResourceManager.getResource(Texture.class, "resources/images/jack");
		
		Sprite spam = new Sprite(texinator);
		setSprite(spam);
	}

	@Override
	public String getName()
	{
		return "Jimmy";
	}

	@Override
	protected void jab()
	{
		AttackState jabState = new AttackState("jab")
		{
			Hitbox jbox;
			BodyFixture zucc;
			
			@Override
			protected void init()
			{
				jbox  = new Hitbox();
				jbox.setDamage(3);
				jbox.setBaseKnockback(alignFacing(new Vector2(12, -8)));
				jbox.setScaledKnockback(alignFacing(new Vector2(2, -1)));
				jbox.setHitstun(.4f);
				
				Rectangle reeeee = new Rectangle(1, .7);
				Vector2 basePos = new Vector2(1, 1.1);
				Vector2 offset  = alignFacing(new Vector2(.6, 0));
				
				reeeee.translate(basePos.add(offset));
				
				zucc = new BodyFixture(reeeee);
				
				m_body.addFixture(zucc);
				
				jbox.addToFixture(zucc);
				addHitbox(jbox);
			}
			
			@Override
			public void interrupt()
			{
				removeHitbox(jbox);
				m_body.removeFixture(zucc);
			}
			
			@Override
			public void end()
			{
				interrupt();
			}
		};
		
		AttackState jabStartup = new AttackState("jump_dsc", .3f);
		
		pushState(jabState);
		pushState(jabStartup);
	}

	@Override
	protected void tilt()
	{
		AttackState tilted = new AttackState("tilt", .3f)
		{
			Hitbox[] gethit = new Hitbox[2];
			BodyFixture[] fixem = new BodyFixture[gethit.length];
			
			@Override
			protected void init()
			{
				for (int i = 0; i < gethit.length; i++)
				{
					gethit[i]= new Hitbox();
					gethit[i].setDamage(6);
					gethit[i].setBaseKnockback(new Vector2(3 * (i == 0 ? 1 : -1), -9));
					gethit[i].setScaledKnockback(new Vector2(1 * (i == 0 ? 1 : -1), -9));
					gethit[i].setHitstun(.5f);
					
					Rectangle ra = new Rectangle(.4, 1);
					Vector2 basePos = new Vector2(1, .9);
					Vector2 offset = new Vector2(.4 * (i == 0 ? 1 : -1), 0);
					ra.translate(basePos.add(offset));
					
					fixem[i] = new BodyFixture(ra);
					gethit[i].addToFixture(fixem[i]);
					
					m_body.addFixture(fixem[i]);
					addHitbox(gethit[i]);
				}
			}
			
			@Override
			public void interrupt()
			{
				for (int i = 0; i < gethit.length; i++)
				{
					removeHitbox(gethit[i]);
					m_body.removeFixture(fixem[i]);
				}
			}
			
			@Override
			public void end()
			{
				interrupt();
			}
		};
		
		pushState(tilted);
		pushState(new AttackState("jump_dsc", .1f));
	}

	@Override
	protected void smash()
	{
		AttackState windup = new AttackState("jump_asc", .4f)
		{
			@Override
			protected void init()
			{
				m_superArmour = true;
			}
		};
		
		AttackState smooth = new AttackState("jump_dsc", .6f)
		{
			Hitbox criminal = new Hitbox();
			BodyFixture leen;
			
			@Override
			protected void init()
			{
				m_facingRight = !m_facingRight;
				
				criminal.setDamage(10);
				criminal.setBaseKnockback(alignFacing(new Vector2(2, 15)));
				criminal.setScaledKnockback(alignFacing(new Vector2(2, 18)));
				criminal.setHitstun(.2f);
				
				Rectangle r = new Rectangle(1, 1.3);
				r.translate(1, 1.2);
				
				leen = new BodyFixture(r);
				criminal.addToFixture(leen);
				
				m_body.addFixture(leen);
			}
			
			@Override
			public void end()
			{
				m_facingRight = !m_facingRight;
				m_body.removeFixture(leen);
			}
		};
		
		pushState(smooth);
		pushState(windup);
	}

	@Override
	protected void projectile()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void signature()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void recover()
	{
		// TODO Auto-generated method stub
		
	}

}
