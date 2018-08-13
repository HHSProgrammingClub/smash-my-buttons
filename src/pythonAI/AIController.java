package pythonAI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.python.core.PyException;
import org.python.core.PyObject;

import characters.Character;
import program.Battle;
import program.CharacterController;
import pythonAI.editor.Editor;
import pythonAI.editor.EditorManager;

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
	private PyEnemyWrapper m_enemyInterface;
	private Editor m_editor;
	private PyInterpreter m_interpreter;
	private PyInterpreterCallback m_callback = new PyInterpreterCallback()
	{
		@Override
		public void onException(PyException p_exception)
		{}

		@Override
		public void onBeginRun()
		{}

		@Override
		public void onEndRun()
		{
			loadGlobals();
		}
	};
	
	public AIController()
	{
		m_interpreter = new PyInterpreter();
		m_interpreter.addCallback(m_callback);
		
		m_playerInterface = new PyCharacterWrapper();
		m_enemyInterface = new PyEnemyWrapper();
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
		m_interpreter.run();
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
		m_interpreter.run();
	}
	
	@Override
	public void setCharacter(Character p_character)
	{
		super.setCharacter(p_character);
		m_playerInterface.setCharacter(p_character);
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
		m_editor = EditorManager.getInstance().openEditor(new File(p_path));
		m_editor.setInterpreter(m_interpreter);
		m_interpreter.run();
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
		m_interpreter.setOutputStream(null);
		m_interpreter.run();
	}


	//@Override
	public String getTargetCharacter()
	{
		return m_targetCharacter;
	}
	
	public void setEnemyCharacter(Character enemy)
	{
		if(enemy != null)
			m_enemyInterface.setCharacter(enemy);
		else
			System.out.println("No character set! :(");
	}
	
	private void loadGlobals()
	{
		try
		{
			// Get loop function
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
	
	/**
	 * Calls the loop function in your script.
	 */
	private void callLoop()
	{
		if (m_pyLoopFunction != null)
			m_interpreter.call(m_pyLoopFunction, new Object[] {(pythonAI.interfaces.PlayerInterface)m_playerInterface,
					(pythonAI.interfaces.EnemyInterface)m_enemyInterface});
	}

}
