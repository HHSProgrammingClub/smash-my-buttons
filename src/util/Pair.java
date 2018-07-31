package util;

/**
 * A generic for returning 2 related objects
 * @author Michael Thompson
 */
public class Pair<T1, T2>
{
	public T1 first;
	public T2 second;
	public Pair(T1 p_first, T2 p_second)
	{
		first = p_first;
		second = p_second;
	}
}