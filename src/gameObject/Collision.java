package gameObject;

import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;

public class Collision implements CollisionListener
{

	@Override
	public boolean collision(ContactConstraint arg0)
	{
		return false;
	}

	@Override
	public boolean collision(Body p_body1, BodyFixture p_fixture1, Body p_body2, BodyFixture p_fixture2)
	{
		BoxColliderComponent collider1 = (BoxColliderComponent) p_fixture1.getUserData();
		BoxColliderComponent collider2 = (BoxColliderComponent) p_fixture2.getUserData();
		
		GameObject object1 = collider1.getObject();
		GameObject object2 = collider2.getObject();
		
		object1.receiveMessage(new OnCollision(collider1, collider2));
		object2.receiveMessage(new OnCollision(collider2, collider1));
		return true;
	}

	@Override
	public boolean collision(Body arg0, BodyFixture arg1, Body arg2, BodyFixture arg3, Penetration arg4)
	{
		return false;
	}

	@Override
	public boolean collision(Body arg0, BodyFixture arg1, Body arg2, BodyFixture arg3, Manifold arg4)
	{
		return false;
	}
	
}
