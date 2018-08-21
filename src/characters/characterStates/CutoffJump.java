package characters.characterStates;

import org.dyn4j.geometry.Vector2;

import characters.Character;

public class CutoffJump extends JumpState
{
	private boolean off = false;
	private boolean holding = false;
	
	@Override
	protected void onUpdate(float p_delta)
	{
		if(!m_falling && m_character.getBody().getLinearVelocity().y > 0)
			setAnimation("jump_dsc");
		
		if(!(holding || off))
		{
			Vector2 vel = m_character.getBody().getLinearVelocity();
			m_character.getBody().setLinearVelocity(vel.x, 0);
			off = true;
		}
		
		holding = false;
	}
	
	@Override
	public boolean handleAction(int p_action)
	{
		if(p_action == Character.ACTION_MOVELEFT
				|| p_action == Character.ACTION_MOVERIGHT)
			m_character.applyRunForce();
		else if(p_action == Character.ACTION_JUMP_HOLD)
			holding = true;
		else if(p_action <= Character.ACTION_SIGNATURE
				&& p_action >= Character.ACTION_JAB)
		{
			off = true;
			m_character.getBody().setLinearVelocity(m_character.getBody().getLinearVelocity().x, 0);
		}
		return true;
	}
}
