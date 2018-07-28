package graphics;
import org.w3c.dom.Element;

public class Animation 
{
	private IntRect m_frame;
	private int m_frameCount;
	private float m_interval;
	private int m_loop;
	private String m_name;

	public static int LOOPTYPE_NONE = 0;
	public static int LOOPTYPE_STARTEND = 1;
	public static int LOOPTYPE_PINGPONG = 2;
	
	
	public Animation()
	{
		m_frame = new IntRect(0, 0, 0, 0);
		m_loop = LOOPTYPE_NONE;
		m_frameCount = 0;
		m_interval = 0;
	}
	
	public IntRect getFrame()
	{
		return m_frame;
	}
	
	public IntRect getFrame(int p_frame)
	{
		int frameIdx = 0;
		if (m_loop == LOOPTYPE_STARTEND)
			frameIdx = (p_frame % m_frameCount + m_frameCount) % m_frameCount;
		else if (m_loop == LOOPTYPE_PINGPONG)
			frameIdx = pingpongIntValue(p_frame, m_frameCount - 1);
		else
			frameIdx = Math.max(Math.min(p_frame, m_frameCount), 0);

		IntRect frame = new IntRect(m_frame);
		frame.x += m_frame.w*frameIdx;
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
	
	public int getLoopType()
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
			m_interval = Float.parseFloat(p_ele.getAttribute("interval")) / 1000f; // Convert from milliseconds to seconds
		
		if (p_ele.hasAttribute("loop") && Integer.parseInt(p_ele.getAttribute("loop")) == 1)
			m_loop =  LOOPTYPE_STARTEND;
		else if (p_ele.hasAttribute("pingpong") && Integer.parseInt(p_ele.getAttribute("pingpong")) == 1)
			m_loop = LOOPTYPE_PINGPONG;
		else
			m_loop = LOOPTYPE_NONE;
	}
	
	private int pingpongIntValue(int p_value, int p_end)
	{
		return (p_value / p_end) % 2 > 0 ? p_end - (p_value%p_end) : (p_value%p_end);
	}
}
