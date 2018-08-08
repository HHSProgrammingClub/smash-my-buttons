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

/**
 * This wrapper delegates calls from the interface to a real Character object
 */
class PyCharacterWrapper implements pythonAI.interfaces.PlayerInterface
{
	private Character m_character;
	
	public void setCharacter(Character p_character)
	{
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

	@Override
	public int getDamage() {
		return m_character.getDamage();
	}
}

class PyEnemyWrapper implements pythonAI.interfaces.EnemyInterface {
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
	
	@Override
	public int getDamage() {
		return m_character.getDamage();
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
	private PyEnemyWrapper m_enemyInterface;
	private Editor m_editor;
	private PyInterpreter m_interpreter;
	private PyInterpreterCallback m_callback = new PyInterpreterCallback()
	{
		@Override
		public void onException(PyException p_exception)
		{}

		@Override
		public void onBeginReinitialize()
		{}

		@Override
		public void onEndReinitialize()
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
		m_interpreter.reinitialize();
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
		m_interpreter.reinitialize();
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
		m_interpreter.reinitialize();
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
		m_interpreter.reinitialize();
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
