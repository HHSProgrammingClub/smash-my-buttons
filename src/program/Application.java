package program;
import java.awt.Color;

import graphics.DebugBox;
import graphics.GUI;
import graphics.IntRect;
import graphics.RenderList;
import graphics.Renderer;
import graphics.Sprite;
import graphics.StartMenu;
import graphics.Texture;

/**
 * Smash-Bros style game that teaches people how to code in Python
 * @authors Michael Thompson, Catherine Guevara, Benhardt Hansen, Kevin Yang 
 */
public class Application 
{
	private static boolean running = true;
	private static boolean visibleHitboxes = true;
	
	public static void main(String[] args)
	{
		// Test Controller
		AIController controller = new AIController();
		controller.openResource("resources/pyAI.py");
		controller.start();
		controller.update(null, 2);
		
		//initialize the application window
		Renderer renderer = new Renderer();
		GUI gui = new GUI(renderer);
		
		StartMenu start = new StartMenu(gui);
		gui.setPage(start);
	
		
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
		//DebugDrawer debugger = new DebugDrawer(world);
		
		Clock gameClock = new Clock();
		float delta = 0;
		
		//game loop: should move somewhere else and only start
		//once the battle itself starts
		while(running)
		{ 
			//calculate delta
			delta = gameClock.getElapse();
			gameClock.restart();
			
			//clear the buffer
			renderer.clear();
			
			//update the world
			//draw sprites
			renderList.draw(renderer);
			
			//debug
			if(visibleHitboxes);
				//debugger.draw(renderer)
			
			//display the current frame
			renderer.display();
			
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
