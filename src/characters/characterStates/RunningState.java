package characters.characterStates;

public class RunningState extends CharacterState
{
	public RunningState()
	{
		super("run", -1);
	}
	
	@Override
	protected void init()
	{
		m_character.getBody().setLinearDamping(0);
	}
}
