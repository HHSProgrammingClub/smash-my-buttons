package characters.characterStates;

import org.dyn4j.geometry.Vector2;

import characters.Character;

public class TimerJump extends JumpState
{
	private boolean off = false;
	private boolean holding = false;
	private double minVelocity = -1;
	
	@Override
	protected void onUpdate(float p_delta)
	{
		if(!m_falling && m_character.getBody().getLinearVelocity().y > 0)
			setAnimation("jump_dsc");
		
		if(!(holding || off))
		{
			off = true;
			Vector2 vel = m_character.getBody().getLinearVelocity();
			if(vel.y >= minVelocity)
				m_character.getBody().setLinearVelocity(vel.x, minVelocity);
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
			if(m_character.getBody().getLinearVelocity().y >= minVelocity)
				m_character.getBody().setLinearVelocity(m_character.getBody().getLinearVelocity().x, minVelocity);
		}
		return true;
	}
}
