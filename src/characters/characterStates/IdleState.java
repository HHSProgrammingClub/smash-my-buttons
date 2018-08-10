package characters.characterStates;

import characters.Character;

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
	
	@Override
	protected void init()
	{
		//m_character.getBody().setLinearDamping(20);
		m_character.setAttacking(false);
	}
	
	@Override
	public void pause()
	{
		m_character.getBody().setLinearDamping(0);
	}
	
	@Override
	public void resume()
	{
		m_character.getBody().setLinearDamping(20);
		m_character.setAttacking(false);
	}
	
	@Override
	protected void onUpdate(float p_delta)
	{
		if(m_character.getBody().getLinearVelocity().y != 0)
			m_character.getBody().setLinearDamping(0);
		else
			m_character.getBody().setLinearDamping(20);
	}
	
	@Override
	public boolean handleAction(int p_action)
	{
		switch(p_action)
		{
		case Character.ACTION_MOVELEFT:
		case Character.ACTION_MOVERIGHT:
			m_character.pushState(new RunningState());
			break;
		case Character.ACTION_JUMP:
			m_character.jump();
			break;
		}
		return true;
	}
}
