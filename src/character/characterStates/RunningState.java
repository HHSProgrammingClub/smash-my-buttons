package character.characterStates;

import characters.Character;
import characters.characterEvent.CharacterEvent;

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
	public boolean handleEvent(CharacterEvent p_event)
	{
		//TODO
		/*if(p_event == Character.ACTION_MOVELEFT ||
				p_event == Character.ACTION_MOVERIGHT)
			m_moving = true;
		else
		{
			m_character.popState();
			m_character.peekState().handleEvent(p_event);
		}*/
		return true;
	}
}
