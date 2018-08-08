package characters.characterStates;

import characters.Character;

public class JumpState extends CharacterState
{
	private boolean m_falling = false;
	private boolean m_jump;
	
	public JumpState()
	{
		this(true);
	}
	
	public JumpState(boolean p_jump)
	{
		super("jump_asc", -1);
		m_jump = p_jump;
	}
	
	protected void init()
	{
		if(!m_jump)
			return;
		m_character.getBody().setLinearVelocity(m_character.getBody().getLinearVelocity().x, 0);
		m_character.getBody().applyImpulse(m_character.getJumpImpulse());
	}
	
	@Override
	protected void onUpdate()
	{
		if(!m_falling && m_character.getBody().getLinearVelocity().y > 0)
			setAnimation("jump_dsc");
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
