import org.w3c.dom.Element;

public class Animation 
{
	private IntRect m_frame;
	private int m_frameCount;
	private float m_interval;
	private boolean m_loop;
	private String m_name;
	
	public Animation()
	{
		m_frame = new IntRect(0, 0, 0, 0);
	}
	
	public IntRect getFrame()
	{
		return m_frame;
	}
	
	public IntRect getFrame(int p_frame)
	{
		IntRect frame = new IntRect(m_frame);
		frame.x += m_frame.w*Math.max(Math.min(p_frame, m_frameCount), 0 );
		return frame; 
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
	
	public void parse(Element p_ele)
	{
		m_name = p_ele.getAttribute("name");
		
		m_frame.x = Integer.parseInt(p_ele.getAttribute("x"));
		m_frame.y = Integer.parseInt(p_ele.getAttribute("y"));
		m_frame.w = Integer.parseInt(p_ele.getAttribute("w"));
		m_frame.h = Integer.parseInt(p_ele.getAttribute("h"));
		
		if (p_ele.hasAttribute("frames"))
			m_frameCount = Integer.parseInt(p_ele.getAttribute("frames"));
		else
			m_frameCount = 1;
		
		if (p_ele.hasAttribute("interval"))
			m_interval = Float.parseFloat(p_ele.getAttribute("interval")) / 1000f;
		
		if (p_ele.hasAttribute("loop"))
			m_loop = Integer.parseInt(p_ele.getAttribute("loop")) == 0 ? false : true;
		else
			m_loop = false;
	}
	
}
