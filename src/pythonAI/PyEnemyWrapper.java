package pythonAI;

import characters.Character;
import program.Hitbox;
import program.Projectile;

class PyEnemyWrapper implements pythonAI.interfaces.EnemyInterface {
	public Character m_character;
	
	public void setCharacter(Character p_character) {
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
	public float getHitstun()
	{
		// TODO Auto-generated method stub
		return m_character.peekState().getTimer();
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
	
	@Override
	public String getName() { return m_character.getName(); }

	@Override
	public boolean boosted() {
		return m_character.getKbooster() > 1;
	}

	@Override
	public String currentStateName() {
		return m_character.peekState().getName();
	}

	@Override
	public float currentStateDurationLeft() {
		return m_character.peekState().getTimer();
	}

	@Override
	public Hitbox[] getHitboxes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projectile[] getProjectiles() {
		// TODO Auto-generated method stub
		return null;
	}
}