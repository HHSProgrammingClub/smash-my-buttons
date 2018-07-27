package graphics;

import javax.swing.JFrame;

public class GUI
{
	private JFrame m_window;
	private Page m_currentPage;
	private Renderer m_renderer;
	
	public GUI(Renderer p_renderer)
	{
		m_window = new JFrame("AI Fighters");
		m_window.setSize(800, 600);
		m_window.setResizable(true);
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
	
}
