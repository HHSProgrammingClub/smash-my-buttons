package program;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Move
{
	// UPDATE: Changed to array, since hitboxes will be added on initialization
	private Hitbox[] m_hitboxes;
	private float m_duration; //player will be unreceptive during this duration.
	//dunno if frames (easier to calculate damage) or
	//seconds (smoother visuals) will be better
	// = startup + duration of all hitboxes + end lag
	private float startup; // startup frames
	private double timer = 0;
	private String m_name;
	
	public Move(String name, float p_duration, Hitbox[] hitboxes)
	{
		m_name = name;
		m_duration = p_duration;
		m_hitboxes = hitboxes;
	}
	
	public void spawnHitboxes(Battle battle)
	{
		for(Hitbox h : m_hitboxes)
		{
			battle.addHitbox(h);
		}
	}
	
	public void instantEffect(Battle battle)
	{ // Replace during move init.
		// Activates immediately when the move is spawned.
		// Projectiles spawned will be in here.
		spawnHitboxes(battle);
		// Do whatever.
		// Ex: Apply impulse to player.
	}
	
	public void effect(Battle battle)
	{ // Replace during move init.
		// Activates during the entire duration of the attack.
		// Ex: Apply vertical impulse to player to simulate a hovering effect.
	}
	
	public void update(Battle battle)
	{
		// TO: Ben -- accepted.
		if(timer < m_duration && timer > startup)
		{
			timer++;
			effect(battle);
		}
	}
}