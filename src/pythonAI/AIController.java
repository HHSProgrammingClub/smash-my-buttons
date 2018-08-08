package pythonAI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;

import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import characters.Character;
import program.Battle;
import program.CharacterController;
import pythonAI.editor.Editor;

/**
 * This wrapper delegates calls from the interface to a real Character object
 */
class PyCharacterWrapper implements pythonAI.interfaces.PlayerInterface
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
		m_character.performAction(Character.ACTION_RECOVERY);
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
		m_character.performAction(Character.ACTION_JUMP);
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
	private PyObject m_pyLoopFunction;
	private String m_name, m_author, m_targetCharacter;
	private PyCharacterWrapper m_playerInterface;
	private Editor m_editor;
	private PyInterpreter m_interpreter;
	
	public AIController()
	{
		m_interpreter = new PyInterpreter();
		m_playerInterface = new PyCharacterWrapper();
		m_editor = new Editor();
		m_editor.setInterpreter(m_interpreter);
		m_editor.setVisible(true);
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

	@Override
	public void start()
	{
		// Reset script by loading it again
		reinitialize();
	}

	@Override
	public void update(Battle p_battle, float p_delta)
	{
		if (m_interpreter.isReady())
		{
			callLoop();
			m_character.update(p_delta);
		}
	}

	@Override
	public void reset()
	{
		reinitialize();
	}
	
	public void setEditorVisible(boolean p_visible)
	{
		m_editor.setVisible(p_visible);
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
		String script = buf.lines().collect(Collectors.joining("\n"));
		buf.close();
		stream.close();
		m_interpreter.setScript(script);
		m_editor.setSaveFilepath(p_path);
		reinitialize();
	}
	
	/**
	 * Load script file from your sources.
	 * @param m_path
	 */
	public void openResource(String m_path)
	{
		InputStream scream = ClassLoader.getSystemResourceAsStream(m_path);
		if (scream == null)
		{
			System.out.println("Could not load " + m_path);
			return;
		}
		String script = new BufferedReader(new InputStreamReader(scream))
				  .lines().collect(Collectors.joining("\n"));
		m_interpreter.setScript(script);
		reinitialize();
	}
	
	public void reinitialize()
	{
		try
		{
			m_interpreter.reinitialize();
			
			// Get the loop function
			m_pyLoopFunction = m_interpreter.getGlobal("loop");
			if (m_pyLoopFunction == null)
				throw new NullPointerException("loop function is missing.");
			
			// Load AIName
			m_name = m_interpreter.getGlobalString("AIName");
			if (m_name == null)
				throw new NullPointerException("Please specify AIName string in script.");
			else
				m_editor.setTitle(m_name);
			
			// Load AIAuthor
			m_author = m_interpreter.getGlobalString("AIAuthor");
			if (m_author == null)
				throw new NullPointerException("Please specify AIAuthor string in your script.");
			
			// Load AITargetCharacter
			m_targetCharacter = m_interpreter.getGlobalString("AITargetCharacter");
			if (m_targetCharacter == null)
				throw new NullPointerException("Please specify AITargetCharacter string in your script.");
			
			System.out.println("Script Compiled");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//@Override
	public String getTargetCharacter()
	{
		return m_targetCharacter;
	}
	
	/**
	 * Calls the loop function in your script.
	 */
	private void callLoop()
	{
		if (m_pyLoopFunction != null)
			m_interpreter.call(m_pyLoopFunction, new Object[] {(pythonAI.interfaces.PlayerInterface)m_playerInterface});
	}

}
