package program;


import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;

import graphics.pages.Page;

public class BattlePage implements Page
{
	private Battle m_battleGame;
	private CanvasGameContainer m_containter;
	
	public BattlePage(Battle p_battle)
	{
		m_battleGame = p_battle;
		
		try
		{
			m_containter = new CanvasGameContainer(m_battleGame);
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	public void beginBattle()
	{
		try {
			m_containter.start();
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public java.awt.Component getComponent()
	{
		return m_containter;
	}

}
