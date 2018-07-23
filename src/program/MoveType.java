package program;

public enum MoveType
{
	jab(0),
	tilt(1),
	smash(2),
	projectile(3),
	recovery(4),
	signature(5);
	
	public final int num;
	
	MoveType(int n)
	{
		num = n;
	}
}
