package graphics;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class GUI
{
	private JFrame m_window;
	private Page m_currentPage;
	
	//ratio of the default window size (800x600) to cathy's desktop computer
	private final float SCALE_W = 0.6f;
	private final float SCALE_H = 0.8f;
	
	public GUI()
	{
		//get the resolution of the user's computer
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		//set dimensions of window according to the user's computer
		int width = (int) (gd.getDisplayMode().getWidth() * SCALE_W);
		int height = (int) (gd.getDisplayMode().getHeight() * SCALE_H);
		
		//initialize the window
		m_window = new JFrame("AI Fighters");
		m_window.setSize(width, height);
		m_window.setResizable(false);
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
