package program;

import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.ContactPoint;

import characters.Character;
import stages.TerrainPiece;

public class GroundListener extends ContactAdapter
{
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
			if(userdata.second.getBody().getWorldCenter().y < point.getPoint().y)
			{
				userdata.second.resetJump();
			}
		}
		return true;
	}

}
