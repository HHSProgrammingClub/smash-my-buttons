package program;

import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Transform;

import graphics.DebugDrawer;
import graphics.GUI;
import graphics.GridRuler;
import graphics.NameLabel;
import graphics.RenderList;
import graphics.pages.Renderer;
import graphics.pages.ResultsScreen;
import pythonAI.AIController;
import graphics.DamageDisplayer;
import stages.Stage;

public class Battle
{
	private Stage m_stage;

	private boolean m_visibleHitboxes = true;
	private boolean m_showGrid = true;
	
	private CharacterController[] m_charControllers = new CharacterController [2];
	
	private CharacterController m_winner;
	
	
	private Renderer m_renderer = new Renderer();
	RenderList m_renderList = new RenderList();
	
	private Thread battleThread;
	
	private GUI m_gui;
	
	public Battle(Stage p_env)
	{
		setStage(p_env);
	}
	
	public Battle() {}

	public void setStage(Stage p_env)
	{
		m_stage = p_env;
		//Gotta get dem listeners
		m_stage.getPhysicsWorld().addListener(new HitboxListener());
		m_stage.getPhysicsWorld().addListener(new GroundListener());
	
		m_renderList.addDrawable(m_stage.getBackground());
	}
	
	public World getWorld()
	{ 
		return m_stage.getPhysicsWorld();
	}
	
	//Port: Player *1*, Player *2*, etc.
	public void addCharacter(CharacterController p_controller, int p_port)
	{
		m_charControllers[p_port - 1] = p_controller;
		Transform t = new Transform();
		switch(p_port) {
		case 1:
			t.translate(2.25, 0);
			p_controller.getCharacter().getBody().setTransform(t);
			p_controller.getCharacter().setFacing(1);
		break;
		case 2:
			t.translate(8.25, 0);
			p_controller.getCharacter().getBody().setTransform(t);
		break;
		}
		if(p_controller instanceof PlayerController)
			((PlayerController)(p_controller)).setPortControl(p_port);
		m_stage.getPhysicsWorld().addBody(p_controller.getCharacter().getBody());
		m_renderList.addDrawable(p_controller.getCharacter());
		m_renderList.addDrawable(new DamageDisplayer(p_controller.getCharacter(), p_port));
		m_renderList.addDrawable(new NameLabel(p_controller, p_port));
		p_controller.getCharacter().setWorld(getWorld());
		//p_controller.getCharacter().getBody().translate(3 + 4 * p_port, 0);
	}
	
	public int getCharacterCount()
	{
		return m_charControllers.length;
	}
	
	public void setVisibleHitboxes(boolean p_visible)
	{
		m_visibleHitboxes = p_visible;
	}
	
	public void setVisibleGrid(boolean p_visible)
	{
		m_showGrid = p_visible;
	}
	
	private void update(float p_delta)
	{
		m_stage.getPhysicsWorld().update((double)(p_delta), 5);
		
		for(int i = 0; i < m_charControllers.length; i++)
			m_charControllers[i].update(this, p_delta);
	}
	
	public void startBattle(GUI p_gui)
	{
		p_gui.setPage(m_renderer);
		m_gui = p_gui;
		
		battleThread = new Thread(new Runnable()
				{
					public void run()
					{
						gameLoop();
					}
				});
		
		battleThread.start();
	}
	
	public void endBattle()
	{
		battleThread.interrupt();
		
		m_gui.setPage(new ResultsScreen(m_winner, m_gui));
	}
	
	/**
	 * Calculates the transform for adjusting the render target to
	 * the window size.
	 * 
	 * @param p_transform Transform to be modified
	 * @param p_targetWidth Width of target
	 * @param p_targetHeight Height of target
	 * @param p_windowWidth Width of window
	 * @param p_windowHeight Height of window
	 * @param p_scale Optional scale factor
	 */
	private static void calcRenderTargetTransform(AffineTransform p_transform,
			double p_targetWidth, double p_targetHeight,
			double p_windowWidth, double p_windowHeight, double p_scale)
	{
		// targetsize:windowsize
		double widthRatio = Math.min((p_windowHeight / p_targetHeight) * (p_targetWidth / p_windowWidth), 1.0);
		double heightRatio = Math.min((p_windowWidth / p_targetWidth) * (p_targetHeight / p_windowHeight), 1.0);
		
		// Size of new target
		double width = p_windowWidth * widthRatio;
		double height = p_windowHeight * heightRatio;
		
		// Amount to scale the transform
		double xScale = (width * p_scale) / p_targetWidth;
		double yScale = (height * p_scale) / p_targetHeight;
		
		// Translate to center
		double xTrans = (p_windowWidth - width) / 2;
		double yTrans = (p_windowHeight - height) / 2;
		
		// Apply
		p_transform.translate(xTrans, yTrans);
		p_transform.scale(xScale, yScale);
	}
	
	private void gameLoop()
	{
		m_stage.registerTerrainSprites(m_renderList);
		
		for(int i = 0; i < m_charControllers.length; i++)
		{
			if(m_charControllers[i] instanceof PlayerController)
				((PlayerController) m_charControllers[i]).attachPage(m_renderer);
			m_charControllers[i].start();
			m_charControllers[i].getCharacter().setOpponenet(m_charControllers[i == 0 ? 1 : 0].getCharacter());
			if(m_charControllers[i] instanceof AIController)
				((AIController) m_charControllers[i]).setEnemyCharacter(m_charControllers[i == 0 ? 1 : 0].getCharacter());
		}
		
		//debug
		DebugDrawer debugger = new DebugDrawer(m_stage.getPhysicsWorld());
		
		GridRuler gridRuler = new GridRuler();
		for (CharacterController i : m_charControllers)
			gridRuler.addCharacter(i.getCharacter());
		
		Clock gameClock = new Clock();
		float delta = 0;
		
		m_renderer.setSizeScale(1f/32);
		
		
		while(true)
		{ 
			//calculate delta
			delta = gameClock.getElapse();
			gameClock.restart();
			
			//clear the buffer
			m_renderer.clear();
			
			update(delta);
			
			// This transform will affect everything that is drawn to our world.
			AffineTransform worldTransform = new AffineTransform();
			java.awt.Dimension rendererSize = m_renderer.getComponent().getSize();
			calcRenderTargetTransform(worldTransform, 600, 450, rendererSize.width, rendererSize.height, 32);
			
			worldTransform.translate(100.f/32, 75.f/32);
			
			m_renderer.pushTransform(worldTransform);
			
			//update the world
			//draw sprites
			m_renderList.draw(m_renderer);
			
			if(m_showGrid)
				gridRuler.draw(m_renderer);
			
			//debug
			if(m_visibleHitboxes)
				debugger.draw(m_renderer);
			
			m_renderer.popTransform();
			
			//display the current frame
			m_renderer.display();
			
			//check if one player has died, then make the other the winner
			//if we ever add more players, this will have to change
			for(int i = 0; i < m_charControllers.length; i++)
				if(m_charControllers[i].getCharacter().getStock() == 0)
				{
					m_winner = m_charControllers[i == 0 ? 1 : 0];
					endBattle();
					break;
				}
			 
			//delay                         | fps here
			try {                         //V
				long totalNanos = (int)(1e9/30) - (int)(gameClock.getElapse()*1e9f);
				if(totalNanos > 0)
				{
					int nanos = (int) (totalNanos % 1000000);
					long milis = (totalNanos - nanos) / 1000000;
					Thread.sleep(milis, (int)nanos);
				}
				//this one's simpler
				//Thread.sleep(16, 666666);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
