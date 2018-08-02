package characters.characterStates;

public class IdleState extends CharacterState
{
	public IdleState()
	{
		this(-1);
	}
	
	public IdleState(float p_duration)
	{
		super("idle", p_duration);
	}
}
