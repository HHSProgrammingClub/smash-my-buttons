package characters.characterStates;

import characters.Character;

public class JumpState extends CharacterState
{
	public JumpState()
	{
		super("jump_asc", -1);
	}
	
	protected void init()
	{
		m_character.getBody().setLinearVelocity(m_character.getBody().getLinearVelocity().x, 0);
		m_character.getBody().applyImpulse(m_character.getJumpImpulse());
	}
	
	@Override
	public void interrupt()
	{
		System.out.println("jumpend");
	}
	
	@Override
	public boolean handleAction(int p_action)
	{
		if(p_action == Character.ACTION_MOVELEFT
				|| p_action == Character.ACTION_MOVERIGHT)
			m_character.applyRunForce();
		return true;
	}
}
