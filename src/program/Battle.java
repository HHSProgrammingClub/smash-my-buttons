package program;

import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.World;

import graphics.DebugDrawer;
import graphics.GUI;
import graphics.NameLabel;
import graphics.RenderList;
import graphics.Renderer;
import graphics.DamageDisplayer;
import stages.Stage;

public class Battle
{
	private Stage m_stage;

	private boolean m_visibleHitboxes = true;
	
	private CharacterController[] m_charControllers = new CharacterController [2];
	
	private Renderer m_renderer = new Renderer();
	RenderList m_renderList = new RenderList();
	
	private Thread battleThread;
	
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
		switch(p_port) {
		case 1:
			p_controller.getCharacter().getBody().translate(2, 0);
			p_controller.getCharacter().setFacing(1);
		break;
		case 2:
			p_controller.getCharacter().getBody().translate(9, 0);
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
	
	private void update(float p_delta)
	{
		m_stage.getPhysicsWorld().updatev((double)(p_delta));
		
		for(int i = 0; i < m_charControllers.length; i++)
			m_charControllers[i].update(this, p_delta);
	}
	
	public void startBattle(GUI p_gui)
	{
		p_gui.setPage(m_renderer);
		
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
	}
	
	private void gameLoop()
	{
		m_stage.registerTerrainSprites(m_renderList);
		
		for(int i = 0; i < m_charControllers.length; i++)
		{
			if(m_charControllers[i] instanceof PlayerController)
				((PlayerController) m_charControllers[i]).attachPage(m_renderer);
			m_charControllers[i].start();
		}		
		
		//debug
		DebugDrawer debugger = new DebugDrawer(m_stage.getPhysicsWorld());
		
		Clock gameClock = new Clock();
		float delta = 0;
		
		m_renderer.setSizeScale(1f/32); 
		
		//game loop: should move somewhere else and only start
		//once the battle itself starts
		while(true)
		{ 
			//calculate delta
			delta = gameClock.getElapse();
			gameClock.restart();
			
			//clear the buffer
			m_renderer.clear();
			
			update(delta);
			
			// This transform will affect everything that is draw to our world.
			AffineTransform worldTransform = new AffineTransform();
			java.awt.Dimension rendererSize = m_renderer.getComponent().getSize();
			//worldTransform.scale(128, 128); //temporary --- for Cathy's laptop
			worldTransform.scale((rendererSize.width * 32) / 400.0,
					(rendererSize.height * 32) / 300.0); // One unit is 64 pixels
			m_renderer.pushTransform(worldTransform);
			
			//update the world
			//draw sprites
			m_renderList.draw(m_renderer);
			
			//debug
			if(m_visibleHitboxes)
				debugger.draw(m_renderer);
			
			//display the current frame
			m_renderer.display();
			
			m_renderer.popTransform();
			
			//delay
			try {
				long totalNanos = 16666666 - (int)(gameClock.getElapse()*1e9f);
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
