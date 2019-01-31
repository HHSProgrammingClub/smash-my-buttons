package characters.characterStates;

import characters.Character;

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
		//m_character.getBody().getFixture(0).setRestitution(.9);
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
		//m_character.getBody().getFixture(0).setRestitution(0);
	}
	
	@Override
	public boolean handleAction(int p_action)
	{
		if(p_action == Character.EVENT_HITSTUN)
		{
			m_character.popState();
			return m_character.peekState().handleAction(p_action);
		}
		return false;
	}
	
}