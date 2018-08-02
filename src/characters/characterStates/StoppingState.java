package characters.characterStates;

public class StoppingState extends CharacterState
{
	public StoppingState()
	{
		super("idle", .1f);
	}
	
	@Override
	protected void init()
	{
		m_character.getBody().setLinearDamping(20);
	}
	
	@Override
	public void interrupt()
	{
		end();
	}
	
	@Override
	public void end()
	{
		m_character.getBody().setLinearDamping(0);
	}
}
