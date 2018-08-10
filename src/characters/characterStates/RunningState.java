package characters.characterStates;

import characters.Character;

public class RunningState extends CharacterState
{
	private boolean m_moving = true;

	public RunningState()
	{
		super("run", -1);
	}
	
	@Override
	protected void onUpdate(float p_delta)
	{
		if(!m_moving)
			m_character.popState();
		m_character.applyRunForce();
		m_moving = false;
	}
	
	@Override
	public boolean handleAction(int p_action)
	{
		if(p_action == Character.ACTION_MOVELEFT ||
				p_action == Character.ACTION_MOVERIGHT)
			m_moving = true;
		else
		{
			m_character.popState();
			m_character.peekState().handleAction(p_action);
		}
		return true;
	}
}
