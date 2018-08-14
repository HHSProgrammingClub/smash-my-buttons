package pythonAI;

import characters.Character;
import program.Hitbox;
import program.Projectile;

class PyEnemyWrapper implements pythonAI.interfaces.EnemyInterface
{
	public Character m_character;
	
	public void setCharacter(Character p_character)
	{
		m_character = p_character;
	}
	
	@Override
	public float getX()
	{
		return (float) m_character.getBody().getWorldCenter().x;
	}

	@Override
	public float getY()
	{
		return (float) m_character.getBody().getWorldCenter().y;
	}
	
	@Override
	public float getHitstun()
	{
		return m_character.peekState().getTimer();
	}
	
	@Override
	public int getDamage()
	{
		return m_character.getDamage();
	}

	@Override
	public double getXVel()
	{
		return m_character.getBody().getLinearVelocity().x;
	}

	@Override
	public double getYVel()
	{
		return m_character.getBody().getLinearVelocity().y;
	}
	
	@Override
	public String getName()
	{
		return m_character.getName();
	}

	@Override
	public boolean boosted()
	{
		return m_character.getKbooster() > 1;
	}

	@Override
	public String currentStateName()
	{
		return m_character.peekState().getName();
	}

	@Override
	public float currentStateDuration()
	{
		return m_character.peekState().getTimer();
	}

	@Override
	public Hitbox[] getHitboxes()
	{
		return m_character.getHitboxes();
	}

	@Override
	public Projectile[] getProjectiles()
	{
		return (Projectile[]) m_character.getProjectiles();
	}
	
	@Override
	public boolean isFacingRight() {
		return m_character.getFacing() == 1;
	}
}