package program;

import graphics.GUI;
import graphics.Renderer;
import graphics.StartMenu;
import stages.Environment;
import stages.TestingStage;

/**
 * Smash-Bros style game that teaches people how to code in Python
 * @authors Michael Thompson, Catherine Guevara, Benhardt Hansen, Kevin Yang 
 */
public class Application 
{	
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
		
		Environment e = new TestingStage();
		Battle battle = new Battle(e);
		
		StartMenu start = new StartMenu(gui);
		gui.setPage(start);
		
		battle.gameLoop(renderer);
	}
}
