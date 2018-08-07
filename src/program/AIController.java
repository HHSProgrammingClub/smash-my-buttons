package program;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.python.antlr.ParseException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import characters.Character;

/**
 * This classloader restricts all imports in your script.
 */
class PyClassLoader extends ClassLoader
{
	/**
	 * Throw an exception when the script attempts to import.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class findClass(String p_name) throws ClassNotFoundException
	{
		throw new ClassNotFoundException("Imports are not allowed");
	}
}

/**
 * This wrapper delegates calls from the interface to a real Character object
 */
class PyCharacterWrapper implements pyInterfaces.PlayerInterface
{
	public Character m_character;
	
	public void setCharacter(Character p_character) {
		m_character = p_character;
	}
	
	@Override
	public float getX()
	{
		// TODO Auto-generated method stub
		return (float) m_character.getBody().getWorldCenter().x;
	}

	@Override
	public float getY()
	{
		return (float) m_character.getBody().getWorldCenter().y;
	}
	
	@Override
	public void moveLeft()
	{
		m_character.performAction(Character.ACTION_MOVELEFT);
	}
	
	@Override
	public void moveRight()
	{
		m_character.performAction(Character.ACTION_MOVERIGHT);
	}

	@Override
	public void jab()
	{
		m_character.performAction(Character.ACTION_JAB);
	}

	@Override
	public void tilt()
	{
		m_character.performAction(Character.ACTION_TILT);
	}

	@Override
	public void smash()
	{
		m_character.performAction(Character.ACTION_SMASH);
	}

	@Override
	public void proj()
	{
		m_character.performAction(Character.ACTION_PROJECTILE);
	}

	@Override
	public void recover()
	{
		m_character.performAction(Character.ACTION_RECOVERY);
	}

	@Override
	public void signature()
	{
		m_character.performAction(Character.ACTION_SIGNATURE);
	}

	@Override
	public void jump()
	{
		m_character.performAction(Character.ACTION_JUMP);
		//System.out.print("Jump");
	}

	@Override
	public float getHitstun()
	{
		// TODO Auto-generated method stub
		return m_character.peekState().getTimer();
	}

	@Override
	public boolean jumped() {
		return m_character.jumped();
	}

	@Override
	public boolean recovered() {
		return m_character.recovered();
	}
}

class PyEnemyWrapper implements pyInterfaces.EnemyInterface {
	public Character m_character;
	
	public void setCharacter(Character p_character) {
		m_character = p_character;
	}
	
	@Override
	public float getX()
	{
		// TODO Auto-generated method stub
		return (float) m_character.getBody().getWorldCenter().x;
	}

	@Override
	public float getY()
	{
		return (float) m_character.getBody().getWorldCenter().y;
	}
	
	@Override
	public float getHitstun()
	{
		// TODO Auto-generated method stub
		return m_character.peekState().getTimer();
	}
}

/**
 * This controller is operated by a python script.
 * @author Michael Thompson
 *
 */
public class AIController extends CharacterController
{
	private PythonInterpreter m_interpretor;
	private String m_script;
	private PyObject m_pyLoopFunction;
	private String m_name, m_author, m_targetCharacter;
	private PyCharacterWrapper m_playerInterface;
	private PyEnemyWrapper m_enemyInterface;
	
	public AIController()
	{
		m_interpretor = new PythonInterpreter();
		m_playerInterface = new PyCharacterWrapper();
		m_enemyInterface = new PyEnemyWrapper();
	}
	
	public void setupPlayerInterface() {
		if(m_character != null) {
			m_playerInterface.setCharacter(m_character);
		}else {
			System.out.println("No character set! :(");
		}
	}
	
	public void setupEnemyInterface(Character enemy) {
		if(enemy != null) {
			m_enemyInterface.setCharacter(enemy);
		}else {
			System.out.println("No character set! :(");
		}
	}
	
	/**
	 * Open file from system filesystem.
	 * @param p_path
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void openFile(String p_path) throws IOException, FileNotFoundException
	{
		File file = new File(p_path);
		if (!file.exists())
			throw new FileNotFoundException("Could not find file \"" + p_path + "\"");
		InputStream stream = new FileInputStream(file);
		BufferedReader buf = new BufferedReader(new InputStreamReader(stream));
		m_script = buf.lines().collect(Collectors.joining("\n"));
		buf.close();
		stream.close();
		initScript();
	}
	
	/**
	 * Load script file from your sources.
	 * @param m_path
	 */
	public void openResource(String m_path)
	{
		InputStream script = ClassLoader.getSystemResourceAsStream(m_path);
		if (script == null)
		{
			System.out.println("Could not load " + m_path);
			return;
		}
		m_script = new BufferedReader(new InputStreamReader(script))
				  .lines().collect(Collectors.joining("\n"));
		initScript();
	}
	
	private void initScript()
	{
		try
		{
			System.out.println("Loading Python Interpreter...");
			
			// Reset the interpreter
			m_interpretor.cleanup();
			// Remove access to other classes in the project
			m_interpretor.getSystemState().setClassLoader(new PyClassLoader());
			// Output to the console
			m_interpretor.setOut(System.out);
			
			System.out.println("Python Interpreter Ready");
			System.out.println("Compiling script...");
			
			// Interpret the global scope of the script
			m_interpretor.exec(m_script);
			
			// Get the loop function
			m_pyLoopFunction = m_interpretor.get("loop");
			if (m_pyLoopFunction == null)
				throw new NullPointerException("loop function is missing.");
			
			// Load AIName
			PyObject pyAIName = m_interpretor.get("AIName");
			if (pyAIName == null)
				throw new NullPointerException("Please specify AIName string in script.");
			m_name = pyAIName.asString();
			
			// Load AIAuthor
			PyObject pyAIAuthor = m_interpretor.get("AIAuthor");
			if (pyAIAuthor == null)
				throw new NullPointerException("Please specify AIAuthor string in your script.");
			m_author = pyAIAuthor.asString();
			
			// Load AITargetCharacter
			PyObject pyAITargetCharacter = m_interpretor.get("AITargetCharacter");
			if (pyAITargetCharacter == null)
				throw new NullPointerException("Please specify AITargetCharacter string in your script.");
			m_targetCharacter = pyAITargetCharacter.asString();

			System.out.println("Script Compiled");
		}catch(ParseException e)
		{
			// Could do some more things with this
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String getName()
	{
		return m_name;
	}
	
	@Override
	public String getAuthor()
	{
		return m_author;
	}
	
	//@Override
	public String getTargetCharacter()
	{
		return m_targetCharacter;
	}
	
	@Override
	public void start()
	{
		// Reset script by loading it again
		initScript();
	}

	@Override
	public void update(Battle p_battle, float p_delta)
	{
		callLoop();
		m_character.update(p_delta);
	}

	@Override
	public void reset()
	{
		
	}
	
	/**
	 * Calls the loop function in your script.
	 */
	private void callLoop()
	{
		try
		{
			if (m_pyLoopFunction != null)
				m_pyLoopFunction._jcall(new Object[] {(pyInterfaces.PlayerInterface)m_playerInterface,
														(pyInterfaces.EnemyInterface)m_enemyInterface});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
