package pythonAI;

import characters.Character;

/**
 * This wrapper delegates calls from the interface to a real Character object
 */
class PyCharacterWrapper implements pythonAI.interfaces.PlayerInterface
{
	private Character m_character;
	
	public void setCharacter(Character p_character)
	{
		m_character = p_character;
	}
	
	@Override
	public float getX()
	{
		// TODO Auto-generated method stub
		return (float) m_character.getBody().getWorldCenter().x;
	}

	@Override
	public float getY()
	{
		return (float) m_character.getBody().getWorldCenter().y;
	}
	
	@Override
	public void moveLeft()
	{
		m_character.handleEvent(Character.ACTION_MOVELEFT);
	}
	
	@Override
	public void moveRight()
	{
		m_character.handleEvent(Character.ACTION_MOVERIGHT);
	}

	@Override
	public void jab()
	{
		m_character.handleEvent(Character.ACTION_JAB);
	}

	@Override
	public void tilt()
	{
		m_character.handleEvent(Character.ACTION_TILT);
	}

	@Override
	public void smash()
	{
		m_character.handleEvent(Character.ACTION_SMASH);
	}

	@Override
	public void proj()
	{
		m_character.handleEvent(Character.ACTION_PROJECTILE);
	}

	@Override
	public void recover()
	{
		m_character.handleEvent(Character.ACTION_RECOVERY);
	}

	@Override
	public void signature()
	{
		m_character.handleEvent(Character.ACTION_SIGNATURE);
	}

	@Override
	public void jump()
	{
		m_character.handleEvent(Character.ACTION_JUMP);
		//System.out.print("Jump");
	}

	@Override
	public float getHitstun()
	{
		// TODO Auto-generated method stub
		return m_character.peekState().getTimer();
	}

	@Override
	public boolean jumped() {
		return m_character.jumped();
	}

	@Override
	public boolean recovered() {
		return m_character.recovered();
	}

	@Override
	public int getDamage() {
		return m_character.getDamage();
	}

	@Override
	public double getXVel() {
		return m_character.getBody().getLinearVelocity().x;
	}

	@Override
	public double getYVel() {
		return m_character.getBody().getLinearVelocity().y;
	}
}