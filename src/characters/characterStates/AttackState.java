package characters.characterStates;

import characters.Character;
import characters.characterEvent.CharacterEvent;

public class AttackState extends CharacterState
{
	public AttackState(String p_animation, float p_duration)
	{
		super(p_animation, p_duration);
	}
	
	public AttackState(String p_animation)
	{
		super(p_animation);
	}
	
	@Override
	public boolean handleEvent(CharacterEvent p_event)
	{
		//TODO
		/*if(p_event == Character.EVENT_HITSTUN)
		{
			m_character.popState();
			m_character.peekState().handleEvent(p_event);
		}*/
		return false;
	}
}
