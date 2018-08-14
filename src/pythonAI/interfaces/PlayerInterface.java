package pythonAI.interfaces;

import program.Hitbox;
import program.Projectile;

/**
 * This defines what functions are available in
 * the `player` instance in python
 * @author Michael Thompson
 *
 */
public interface PlayerInterface
{
	public void moveLeft();
	public void moveRight();
	public float getX();
	public float getY();
	public void jab();
	public void tilt();
	public void smash();
	public void proj();
	public void recover();
	public void signature();
	public void jump();
	public float getHitstun();
	public boolean jumped();
	public boolean recovered();
	public int getDamage();
	public double getXVel();
	public double getYVel();
	public boolean boosted();
	public Hitbox[] getHitboxes();
	public Object[] getProjectiles();
	public boolean isFacingRight();
}
