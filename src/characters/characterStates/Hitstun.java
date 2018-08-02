package characters.characterStates;

public class Hitstun extends CharacterState
{

	public Hitstun(float p_duration)
	{
		super("hitstun", p_duration);
	}

	@Override
	public void init()
	{
		m_character.setStunned(true);
	}
	
	@Override
	public void interrupt()
	{
		end();
	}

	@Override
	public void end()
	{
		m_character.setStunned(false);
	}
	
}