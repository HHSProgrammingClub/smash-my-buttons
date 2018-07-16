
public class Application 
{
	private static boolean running = true;
	
	public static void main(String[] args)
	{
		//initialize the application
		Renderer renderer = new Renderer();
		renderer.init(2000, 1250);
	
		RenderList renderList = new RenderList();
		
		Texture tex1 = new Texture();
		tex1.openResource("resources/images/dreamland");
		
		System.out.print("asd");
		
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
			
			
			try {
				Thread.sleep(32);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
