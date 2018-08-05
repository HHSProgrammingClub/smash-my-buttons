package characters.characterStates;

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
		System.out.println("jump");
	}
	
	@Override
	public boolean handleAction(int p_Action)
	{
		return true;
	}
}
