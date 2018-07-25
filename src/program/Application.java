package program;
import java.awt.Color;

import graphics.DebugBox;
import graphics.IntRect;
import graphics.RenderList;
import graphics.Renderer;
import graphics.Sprite;
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
		
		//initialize the application
		Renderer renderer = new Renderer();
		renderer.init(2000, 1250);
	
		RenderList renderList = new RenderList();
		
		//test sprite
		Texture tex1 = new Texture();
		tex1.openResource("resources/images/dreamland");
		
		Sprite sprite = new Sprite(tex1, "flowers");
		renderList.addDrawable(sprite);
		
		//test rectangles
		//filled
		DebugBox box = new DebugBox(new IntRect(100, 100, 100, 100), Color.RED, 0.5f);
		renderList.addDrawable(box);
		
		//border
		DebugBox box2 = new DebugBox(new IntRect(100, 110, 100, 100), Color.BLUE, 1.0f, 2);
		renderList.addDrawable(box2);
		
		//debug
		//DebugDrawer debugger = new DebugDrawer(world);
		
		//game loop:
		while(running)
		{ 
			//calculate delta
			
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
				Thread.sleep(17); // 60 fps max 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
