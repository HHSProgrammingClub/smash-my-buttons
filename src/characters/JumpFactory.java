package characters;

import characters.characterStates.CutoffJump;
import characters.characterStates.JumpState;
import characters.characterStates.TimerJump;

public class JumpFactory
{
	public static final int REGULAR = 0;
	public static final int CUTOFF  = 1;
	public static final int TIMER   = 2;
	
	private static int jump = 0;
	
	public static void setJump(int p_jump)
	{
		jump = p_jump;
		System.out.println(jump);
	}
	
	public static JumpState createJump()
	{
		switch(jump)
		{
			case REGULAR:
				return new JumpState();
			case CUTOFF:
				return new CutoffJump();
			case TIMER:
				return new TimerJump();
			default:
				return new JumpState();
		}
	}
}
