
public class Application 
{
	private static boolean running = true;
	
	public static void main(String[] args)
	{
		//initialize the application
		Renderer renderer = new Renderer();
		renderer.init(2000, 1250);
	
		RenderList renderList = new RenderList();
		
		/*
		//current time
		//game loop:
		while(running)
		{
			//calculate delta
			 
			//update
			renderList.draw(renderer);
			
			//Thread.sleep(42069); or whatever dank delay
		}*/
		
	}
}
