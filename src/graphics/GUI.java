package graphics;

import javax.swing.JFrame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class GUI
{
	private JFrame m_window;
	private Page m_currentPage;
	private Renderer m_renderer;
	
	private final float SCALE_W = 0.6f;
	private final float SCALE_H = 0.8f;
	
	public GUI(Renderer p_renderer)
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = (int) (gd.getDisplayMode().getWidth() * SCALE_W);
		int height = (int) (gd.getDisplayMode().getHeight() * SCALE_H);
		
		m_window = new JFrame("AI Fighters");
		m_window.setSize(width, height);
		m_window.setResizable(false);
		m_window.setVisible(true);
		m_window.setLocationRelativeTo(null);
		m_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		m_renderer = p_renderer;
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
	
	public Renderer getRenderer()
	{
		return m_renderer;
	}
	
	public JFrame getWindow()
	{
		return m_window;
	}
}
