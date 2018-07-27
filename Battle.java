package program;

import characters.*;
import graphics.DebugDrawer;
import graphics.RenderList;
import graphics.Renderer;
import graphics.Sprite;
import graphics.Texture;
import stages.Environment;

import java.util.ArrayList;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;

public class Battle
{
	private ArrayList<Hitbox> m_hitboxes = new ArrayList<Hitbox>();
	// List of active moves, in order to keep track of lasting effects.
	private ArrayList<Move> m_activeMoves  = new ArrayList<Move>();;
	private Environment m_env;

	private boolean m_visibleHitboxes = true;
	
	private CharacterController[] m_charControllers = {
		new AIController(),
		new AIController()
	};
	
	public Battle(Environment p_env)
	{
		setEnvironment(p_env);
		for(int i = 0; i < m_charControllers.length; i++)
		{
			m_charControllers[i].setCharacter(new GeorgeTheGlassCutter());
			m_env.getPhysicsWorld().addBody(
					m_charControllers[i].getCharacter().getBody());
		}
	}
	
	public void setEnvironment(Environment p_env)
	{
		m_env = p_env;
	}
	
	public Hitbox[] getHitboxes()
	{
		return (Hitbox[]) m_hitboxes.toArray();
	}
	
	public void addHitbox(Hitbox p_hitbox)
	{
		m_hitboxes.add(p_hitbox);
	}
	
	public void addMove(Move p_move)
	{
		m_activeMoves.add(p_move);
	}
	
	//Port: Player *1*, Player *2*, etc.
	public void addCharacter(CharacterController p_controller, int p_port)
	{
		m_charControllers[p_port - 1] = p_controller;
	}
	
	public int getCharacterCount()
	{
		return m_charControllers.length;
	}
	
	public void setVisibleHitboxes(boolean p_visible)
	{
		m_visibleHitboxes = p_visible;
	}
	
	public void update(float p_delta)
	{
		m_env.getPhysicsWorld().updatev((double)(p_delta));
		for(int i = m_hitboxes.size() - 1; i >= 0; i--)
		{
			m_hitboxes.get(i).updateTimer(p_delta);
			
			if(!m_hitboxes.get(i).alive())
			{
				m_hitboxes.remove(i);
			}
		}
	}
	
	public void gameLoop(Renderer p_renderer)
	{
		RenderList renderList = new RenderList();
		
		//test sprites
		Texture tex1 = new Texture();
		tex1.openResource("resources/images/birboi");
		
		Sprite sprite = new Sprite(tex1, "idle");
		renderList.addDrawable(sprite);
		
		Texture tex2 = new Texture();
		tex2.openResource("resources/images/coffee");
		
		Sprite sprite2 = new Sprite(tex2, "default");
		renderList.addDrawable(sprite2);
		
		//debug
		DebugDrawer debugger = new DebugDrawer(m_env.getPhysicsWorld());
		
		Clock gameClock = new Clock();
		float delta = 0;
		
		//game loop: should move somewhere else and only start
		//once the battle itself starts
		while(true)
		{ 
			//calculate delta
			delta = gameClock.getElapse();
			gameClock.restart();
			
			//clear the buffer
			p_renderer.clear();
			
			update(delta);
			//update the world
			//draw sprites
			renderList.draw(p_renderer);
			
			//debug
			if(m_visibleHitboxes);
				debugger.draw(p_renderer);
			
			//display the current frame
			p_renderer.display();
			
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
				e.printStackTrace();
			}
		}
	}
}
