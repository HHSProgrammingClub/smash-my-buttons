package program;

import graphics.GUI;
import graphics.pages.StartMenu;

/**
 * Smash-Bros style game that teaches people how to code in Python
 * @authors Michael Thompson, Catherine Guevara, Benhardt Hansen, Kevin Yang 
 */
public class Application 
{	
	public static void main(String[] args)
	{
		// Load up Jython's runtime so editors can be quick later on.
		//PyInterpreter.initializeRuntime();
		
		//initialize the application window
		GUI gui = new GUI();
		
		StartMenu start = new StartMenu(gui);
		gui.setPage(start);
	}
}
