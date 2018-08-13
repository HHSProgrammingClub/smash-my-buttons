package pythonAI.interfaces;

import program.Hitbox;
import program.Projectile;

public interface EnemyInterface
{
	public float getX();
	public float getY();
	public float getHitstun();
	public int getDamage();
	public double getXVel();
	public double getYVel();
	public String getName();
	public boolean boosted();
	public String currentStateName();
	public float currentStateDuration();
	public Hitbox[] getHitboxes();
	public Projectile[] getProjectiles();
}