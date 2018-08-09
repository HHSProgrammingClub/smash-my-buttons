package characters.characterStates;

import characters.Character;

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
	public boolean handleAction(int p_action)
	{
		if(p_action == Character.EVENT_HITSTUN)
		{
			m_character.popState();
			m_character.peekState().handleAction(p_action);
		}
		return false;
	}
}
