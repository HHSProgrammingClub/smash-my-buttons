package program;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.python.antlr.*;
import org.python.core.*;
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
	public Battle m_battle;
	public Character m_character;
	
	@Override
	public float getX()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY()
	{
		return 0;
	}

	@Override
	public void jab()
	{
	}

	@Override
	public void tilt()
	{
		System.out.print("Recover");
	}

	@Override
	public void smash()
	{
		// TODO Auto-generated method stub

		System.out.println("Smash!");
	}

	@Override
	public void proj()
	{
		// TODO Auto-generated method stub

		System.out.println("Shoot dem projectiles!");
	}

	@Override
	public void recover()
	{
		m_character.recover(m_battle);
		System.out.print("Recover");
	}

	@Override
	public void signature()
	{
		// TODO Auto-generated method stub

		System.out.println("Signiture attackness!");
	}

	@Override
	public void jump()
	{
		m_character.jump();
		System.out.print("Jump");
	}

	@Override
	public void getHitstun()
	{
		// TODO Auto-generated method stub

		System.out.println("Get hit, son.");
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
	
	public AIController()
	{
		m_interpretor = new PythonInterpreter();
		m_playerInterface = new PyCharacterWrapper();
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
		try
		{
			System.out.println("Loading Python Interpreter...");
			
			m_interpretor.cleanup(); // Reset the interpreter
			m_interpretor.getSystemState().setClassLoader(new PyClassLoader());
			m_interpretor.setOut(System.out);
			
			System.out.println("Python Interpreter Ready");
			System.out.println("Compiling script...");
			
			// Compile script
			m_interpretor.exec(m_script);
			
			// Get the loop function
			m_pyLoopFunction = m_interpretor.get("loop");
			if (m_pyLoopFunction == null)
				throw new NullPointerException("loop function is missing.");
			
			// Load AIname
			PyObject pyAIName = m_interpretor.get("AIName");
			if (pyAIName == null)
				throw new NullPointerException("Please specify AIName string in script.");
			m_name = pyAIName.asString();
			
			// Load AIauthor
			PyObject pyAIAuthor = m_interpretor.get("AIAuthor");
			if (pyAIAuthor == null)
				throw new NullPointerException("Please specify AIAuthor string in your script.");
			m_author = pyAIAuthor.asString();
			
			// Load AIauthor
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
	public void update(Battle p_battle, float p_delta)
	{
		callLoop();
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
				m_pyLoopFunction._jcall(new Object[] {(pyInterfaces.PlayerInterface)m_playerInterface});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
