package graphics;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GUI
{
	private JFrame m_window;
	private Page m_currentPage;
	
	//ratio of the default window size (800x600) to cathy's desktop computer
	private final float SCALE_W = 0.6f;
	private final float SCALE_H = 0.8f;
	
	private final float xRatio = .8f;
	private final float yRatio = .6f;
	private final float scale  = .8f;
	
	public GUI()
	{
		//get the resolution of the user's computer
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		float proporitonalWidth  = gd.getDisplayMode().getWidth()  / xRatio;
		float proportionalHeight = gd.getDisplayMode().getHeight() / yRatio;
		
		int width;
		int height;
		
		if(proporitonalWidth > proportionalHeight)
		{
			height = (int) (gd.getDisplayMode().getHeight() * scale);
			width  = (int) (height * xRatio/yRatio);
		}
		else
		{
			width  = (int) (gd.getDisplayMode().getWidth() * scale);
			height = (int) (width * yRatio/xRatio);
		}
		
		//set dimensions of window according to the user's computer
		//int width = (int) (gd.getDisplayMode().getWidth() * SCALE_W);
		//int height = (int) (gd.getDisplayMode().getHeight() * SCALE_H);
		
		//initialize the window
		String titleText;
		String[] epicTitles = {
			"Code Fighters!",
			"AI Fighters",
			"AI-Vengers Infinity War",
			"Thanos Wholly Approves of This Event",
			"Alexa, Play Despacito",
			"Super Smash Code Ultimate",
			"Code Fight",
			"Smash 'em up",
			"Smash my Buttons"
		};
		m_window = new JFrame(epicTitles[(int)(Math.random() * epicTitles.length)]);
		m_window.setSize(width, height);
		m_window.setResizable(true);
		m_window.setVisible(true);
		m_window.setLocationRelativeTo(null);
		m_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setPage(Page p_page)
	{
		//clear window
		if(m_currentPage != null)
			m_window.remove(m_currentPage.getComponent());
		m_currentPage = p_page;
		
		//display page
		m_window.add(m_currentPage.getComponent());
		// Make sure this component has focus so it can receive input
		m_currentPage.getComponent().requestFocusInWindow(); 
		m_window.validate();
	}
	
	public Page getCurrentPage()
	{
		return m_currentPage;
	}
	
	public JFrame getWindow()
	{
		return m_window;
	}
}
