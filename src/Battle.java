import java.util.ArrayList;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
public class Battle
{
	// Oh look! A stub!
	private ArrayList<Hitbox> m_hitboxes;
	// List of active moves, in order to keep track of lasting effects.
	private ArrayList<Move> activeMoves;
	private CharacterController[] m_charControllers = {
		new AIController(),
		new AIController()
	};
	private Environment m_env;
	public void setEnvironment(Environment eee) { m_env = eee; }
	public Hitbox[] getHitboxes()
	{
		return (Hitbox[]) m_hitboxes.toArray();
	}
	
	public int addHitbox(Hitbox h)
	{
		m_hitboxes.add(h);
		// NOTE: not sure what this should return in terms of an int.
		return m_hitboxes.size();
	}
	
	public void addMove(Move m) { activeMoves.add(m);}
	//Port: Player *1*, Player *2*, etc.
	public void addCharacter(CharacterController c, int port) {
		m_charControllers[port - 1] = c;
	}
	public int getCharacterCount() { return m_charControllers.length; }
	public void update(float p_delta) {
		m_env.getWorld().updatev((double)(p_delta));
		for(int i = m_hitboxes.size() - 1; i >= 0; i--) {
			m_hitboxes.get(i).updateTimer(p_delta);
			if(!m_hitboxes.get(i).alive()) {
				m_hitboxes.remove(i);
			}
		}
	}
}
