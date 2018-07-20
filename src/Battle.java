import java.util.ArrayList;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
public class Battle
{
	private ArrayList<Hitbox> m_hitboxes;
	// List of active moves, in order to keep track of lasting effects.
	private ArrayList<Move> m_activeMoves;
	private Environment m_env;
	
	private CharacterController[] m_charControllers = {
		new AIController(),
		new AIController()
	};
	
	public void setEnvironment(Environment p_env)
	{
		m_env = p_env;
	}
	
	public Hitbox[] getHitboxes()
	{
		return (Hitbox[]) m_hitboxes.toArray();
	}
	
	public void addHitbox(Hitbox p_hitbox)
	{
		m_hitboxes.add(p_hitbox);
	}
	
	public void addMove(Move p_move)
	{
		m_activeMoves.add(p_move);
	}
	
	//Port: Player *1*, Player *2*, etc.
	public void addCharacter(CharacterController p_controller, int p_port)
	{
		m_charControllers[p_port - 1] = p_controller;
	}
	
	public int getCharacterCount()
	{
		return m_charControllers.length;
	}
	
	public void update(float p_delta)
	{
		m_env.getPhysicsWorld().updatev((double)(p_delta));
		
		for(int i = m_hitboxes.size() - 1; i >= 0; i--)
		{
			m_hitboxes.get(i).updateTimer(p_delta);
			
			if(!m_hitboxes.get(i).alive())
			{
				m_hitboxes.remove(i);
			}
		}
	}
}
