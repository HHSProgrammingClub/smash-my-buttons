import java.util.ArrayList;

public class Move
{
	private ArrayList<Hitbox> m_hitboxes;
	private float m_duration; //player will be unreceptive during this duration.
	//dunno if frames (easier to calculate damage) or
	//seconds (smoother visuals) will be better
	// = startup + duration of all hitboxes + end lag
	private float startup; // startup frames
	private double timer = 0;
	private String m_name;
	
	public Move(String name, ArrayList<Hitbox> hitboxes)
	{
		m_name = name;
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
		if(timer < 1)
		{ // Some kind of threshold
			// If we're using a seconds system, we're going to need a more
			// complex threshold system so that instantEffect only activates
			// once.
			
			// You could instead just call the instantEffect() once
			// when the move is used then call update() every frame
			// and not bother with this threshold thing
			//   --Ben
			instantEffect(battle);
		}
		
		if(timer < m_duration && timer > startup)
		{
			timer++;
			effect(battle);
		}
	}
}