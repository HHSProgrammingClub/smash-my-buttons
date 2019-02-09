package program;

//TODO: componentize

public abstract class CharacterController
{
	protected Character m_character;
	
	/**
	 * Set the character this controller has control over.
	 * @param p_character
	 */
	public void setCharacter(Character p_character)
	{
		m_character = p_character;
	}
	
	/**
	 * Get the Character object this controller has control over.
	 * @return
	 */
	public final Character getCharacter()
	{
		return m_character;
	}
	
	public abstract String getName();
	public abstract String getAuthor();
	
	/**
	 * Setup anything that is needed to start the battle.
	 */
	public abstract void start();
	
	/**
	 * Update logic pertaining to this controller. Called every frame.
	 * @param p_battle
	 * @param p_delta
	 */
	public abstract void update(float p_delta);
	
	/**
	 * Reset everything to its default so we can reuse this controller for the next battle.
	 */
	public abstract void reset();
	
}
