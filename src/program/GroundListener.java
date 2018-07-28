package program;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;
import org.dyn4j.dynamics.contact.PersistedContactPoint;
import org.dyn4j.dynamics.contact.SolvedContactPoint;

import stages.TerrainPiece;

public class GroundListener implements ContactListener {
	@Override
	//The important one
	public void sensed(ContactPoint point) {
		if(point.getBody1().getUserData() instanceof characters.Character &&
				point.getBody2().getUserData() instanceof TerrainPiece) {
			characters.Character c = (
					(characters.Character)(point.getBody1().getUserData()));
			// Veeeeery rudimentary ground collision algorithm
			// Essentially, tests whether it has collided with the top half
			// of the TerrainPiece
			if(c.getBody().getWorldCenter().y > point.getPoint().y &&
					c.getBody().getChangeInPosition().y > 0) {
				c.resetJump();
			}
		}
	}
	@Override
	public boolean preSolve(ContactPoint point) { return true; }
	@Override
	public void postSolve(SolvedContactPoint point) {}
	@Override
	public boolean persist(PersistedContactPoint point) { return true; }
	@Override
	public void end(ContactPoint point) {}
	@Override
	public boolean begin(ContactPoint point) {return true;}
}
