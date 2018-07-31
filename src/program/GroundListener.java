package program;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.contact.ContactListener;
import org.dyn4j.dynamics.contact.ContactPoint;
import org.dyn4j.dynamics.contact.PersistedContactPoint;
import org.dyn4j.dynamics.contact.SolvedContactPoint;

import characters.Character;
import stages.TerrainPiece;

public class GroundListener implements ContactListener {
	@Override
	//The important one
	public void sensed(ContactPoint point) {

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
	public boolean begin(ContactPoint point)
	{
		util.Pair<TerrainPiece, Character> userdata
			= util.ListenerUtil.checkUserDataForClasses(point, TerrainPiece.class, false, characters.Character.class, false);
		if(userdata != null)
		{
			// Veeeeery rudimentary ground collision algorithm
			// Essentially, tests whether it has collided with the top half
			// of the TerrainPiece
			if(userdata.second.getBody().getWorldCenter().y < point.getPoint().y /*&& // it seems to do just fine without the "change in position" check
					c.getBody().getChangeInPosition().y > 0*/) 
			{
				userdata.second.resetJump();
				System.out.println("reset");
			}
		}
		return true;
	}

}
