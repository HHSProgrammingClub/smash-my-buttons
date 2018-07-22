import java.awt.Color;

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
		controller.openScriptResource("resources/pyAI.py");
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
		DebugDrawer dd = new DebugDrawer();
		
		//game loop:
		while(running)
		{ 
			//calculate delta
			
			renderer.clear();
			
			//debug
			//dd.debugDraw(world, renderList);
			
			//update
			renderList.draw(renderer);
			
			// TODO: Add display method to renderer to update
			
			try {
				Thread.sleep(17); // 60 fps max 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
