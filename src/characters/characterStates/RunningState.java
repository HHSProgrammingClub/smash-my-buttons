package characters.characterStates;

public class RunningState extends CharacterState
{
	public RunningState()
	{
		super("run", -1);
	}
	
	@Override
	protected void init()
	{
		m_character.getBody().setLinearDamping(0);
		m_character.getBody().applyForce(m_character.getRunForce());
	}
	
	@Override
	protected void onUpdate()
	{
		if(Math.abs(m_character.getBody().getLinearVelocity().x) < m_character.getMaxRunSpeed())
			m_character.getBody().applyForce(m_character.getRunForce());;
	}
}
