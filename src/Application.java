
public class Application 
{
	private static boolean running = true;
	private static boolean visibleHitboxes = true;
	
	public static void main(String[] args)
	{
		AIController controller = new AIController();
		controller.openScriptResource("resources/pyAI.py");
		controller.start();
		controller.update(null, 2);
		/*
		//initialize the application
		Renderer renderer = new Renderer();
		renderer.init(2000, 1250);
	
		RenderList renderList = new RenderList();
		
		Texture tex1 = new Texture();
		tex1.openResource("resources/images/dreamland");
		
		Sprite sprite = new Sprite(tex1, "flowers");
		renderList.addDrawable(sprite);
		
		//current time
		//game loop:
		while(running)
		{
			//calculate delta
			
			renderer.clear();
			
			//update
			renderList.draw(renderer);
			
			// TODO: Add display method to renderer to update
			
			try {
				Thread.sleep(17); // 60 fps max 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		*/
	}
}
