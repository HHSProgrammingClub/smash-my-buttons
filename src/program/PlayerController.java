package program;

import java.awt.event.*;

class PlayerKeyListener implements KeyListener
{
	@Override
	public void keyPressed(KeyEvent e)
	{
		e.getKeyCode();
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// Do nothing...
	}
}

public class PlayerController extends CharacterController
{
	private graphics.Page m_page;
	private PlayerKeyListener m_keyListener;
	
	
	public void attachPage(graphics.Page p_page)
	{
		m_page = p_page;
		m_page.getComponent();
	}
	
	@Override
	public String getName()
	{
		return "User Controlled";
	}
	
	@Override
	public String getAuthor()
	{
		return "N/A";
	}
	
	@Override
	public void start()
	{
		
	}

	@Override
	public void update(Battle p_battle, float p_delta)
	{
		
	}

	@Override
	public void reset()
	{
		
	}
}
