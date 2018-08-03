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
		m_character.setAttacking(false);
		//To make it so they don't slide around so much when hit
		//So stuff without hitstun is essentially a windbox as there's no
		//linear damping :)
		m_character.getBody().setLinearDamping(1);
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
		m_character.getBody().setLinearDamping(0);
	}
	
}