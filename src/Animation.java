
public class Animation 
{
	private Rect m_rootFrame;
	private int m_frameCount;
	private float m_interval;
	private boolean m_loop;
	private Rect m_frame;
	private String m_name;
	
	public Animation(String p_name, float p_interval, boolean p_loop)
	{
		m_name = p_name;
		m_interval = p_interval;
		m_loop = p_loop;
	}
	
	public Rect getFrame()
	{
		return m_frame;
	}
	
	public Rect getFrame(int p_frame)
	{
		return null; 
	}
	
	public int getFrameCount()
	{
		return m_frameCount;
	}
	
	public float getInterval()
	{
		return m_interval;
	}
	
	public boolean isLooped()
	{
		return m_loop;
	}
	
	public String getName()
	{
		return m_name;
	}
	
	public void parse()
	{
		//for Michael to do
	}
	
}
