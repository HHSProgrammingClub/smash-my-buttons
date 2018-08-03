package program;

import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;

public class HitboxListener implements CollisionListener {
	@Override
	public boolean collision(Body body1, BodyFixture fixture1, Body body2,
			BodyFixture fixture2) {
		if(body1.getUserData() instanceof characters.Character &&
				fixture2.getUserData() instanceof Hitbox &&
				!(fixture1.getUserData() instanceof Hitbox)) {
			characters.Character c = (
					(characters.Character)(body1.getUserData()));
			Hitbox h = ((Hitbox)fixture2.getUserData());
			c.takeHit(h);
			return false;
		}
		//System.out.println("3 blind mice");
		return true;
	}
	@Override
	public boolean collision(Body body1, BodyFixture fixture1, Body body2,
			BodyFixture fixture2, Manifold manifold) { return true; }
	@Override
	public boolean collision(Body body1, BodyFixture fixture1, Body body2,
			BodyFixture fixture2, Penetration penetration) { return true; }
	@Override
	public boolean collision(ContactConstraint contactConstraint) { return true; }
}
