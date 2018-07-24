package program;

public class Clock
{
	private long m_startTime;
	private long m_pauseTime;
	private boolean m_isPaused;
	
	/**
	 * Construct a clock. The clock begins at construction.
	 */
	public Clock()
	{
		m_startTime = System.nanoTime();
		m_isPaused = false;
	}
	
	/**
	 * Get elapse from the start of this clock to now in seconds.
	 * @return Elapse in seconds
	 */
	public float getElapse()
	{
		long elapseNano = System.nanoTime() - m_startTime;
		return (float)elapseNano*1e-9f; // Convert to seconds
	}
	
	/**
	 * Start the clock. Use getElapse() to get the time.
	 */
	public void start()
	{
		long nowTime = System.nanoTime();
		if (m_isPaused)
			m_startTime += nowTime - m_pauseTime;
		m_isPaused = false;
	}
	
	/**
	 * Pause and reset the clock to 0.
	 */
	public void stop()
	{
		pause();
		restart();
	}
	
	/**
	 * Pause this clock
	 */
	public void pause()
	{
		m_pauseTime = System.nanoTime();
		m_isPaused = true;
	}
	
	/**
	 * Reset this clock to 0 seconds.
	 */
	public void restart()
	{
		if (m_isPaused)
			m_startTime = m_pauseTime; // Distance between start time and pause time becomes 0.
		else
			m_startTime = System.nanoTime();
	}
	
	/**
	 * Check if this clock is paused.
	 * @return True if paused.
	 */
	public boolean isPaused()
	{
		return m_isPaused;
	}
}
