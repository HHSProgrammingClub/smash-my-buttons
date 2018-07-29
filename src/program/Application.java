package program;

import graphics.GUI;
import graphics.Renderer;
import graphics.StartMenu;

/**
 * Smash-Bros style game that teaches people how to code in Python
 * @authors Michael Thompson, Catherine Guevara, Benhardt Hansen, Kevin Yang 
 */
public class Application 
{	
	public static void main(String[] args)
	{
		//initialize the application window
		GUI gui = new GUI();
		
		StartMenu start = new StartMenu(gui);
		gui.setPage(start);
	}
}
