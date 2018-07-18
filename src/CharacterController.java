
public abstract class CharacterController
{
	protected Character m_character;
	
	final void setCharacter(Character p_character)
	{
		m_character = p_character;
	}
	
	final Character getCharacter()
	{
		return m_character;
	}
	
	/**
	 * Setup anything that is needed to start the battle.
	 */
	public abstract void start();
	
	/**
	 * Update logic pertaining to this controller. Called every frame.
	 * @param p_battle
	 * @param p_delta
	 */
	public abstract void update(Battle p_battle, float p_delta);
	
	/**
	 * Reset everything to its default so we can reuse this controller for the next battle.
	 */
	public abstract void reset();
}
