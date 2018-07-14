
public class Animation 
{
	private Rect rootFrame;
	private int frameCount;
	private float interval;
	private boolean loop;
	private Rect frame;
	private String name;
	
	public Animation(String p_name, float p_interval, boolean p_loop)
	{
		name = p_name;
		interval = p_interval;
		loop = p_loop;
	}
	
	public void init(int p_width, int p_height)
	{
		//what is this supposed to do
	}
	
	public Rect getFrame()
	{
		return frame;
	}
	
	public Rect getFrame(int p_frame)
	{
		return null; 
	}
	
	public int getFrameCount()
	{
		return frameCount;
	}
	
	public float getInterval()
	{
		return interval;
	}
	
	public boolean isLooped()
	{
		return loop;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void parse()
	{
		//for Michael to do
	}
	
}
