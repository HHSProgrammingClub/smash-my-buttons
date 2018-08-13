package pythonAI;

import java.io.OutputStream;
import java.util.ArrayList;

import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

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

public class PyInterpreter
{
	private PythonInterpreter m_interpreter;
	private ArrayList<PyInterpreterCallback> m_callback = new ArrayList<PyInterpreterCallback>();
	private boolean m_ready = false;
	private String m_script = "";
	private String m_name = "";
	
	public PyInterpreter()
	{
		m_interpreter = new PythonInterpreter();
	}
	
	/**
	 * Loads the python runtime.
	 */
	public static void initializeRuntime()
	{
		PySystemState.initialize(System.getProperties(), null, new String[] {""});
		Py.importSiteIfSelected();
	}
	
	public void run()
	{
		try
		{
			for (PyInterpreterCallback i : m_callback)
				i.onBeginRun();
			
			System.out.println("Loading Python Interpreter...");
			
			// Reset the interpreter
			m_interpreter.cleanup();
			// Remove access to other classes in the project
			m_interpreter.getSystemState().setClassLoader(new PyClassLoader());
			
			System.out.println("Python Interpreter Ready");
			System.out.println("Compiling script...");
			
			// Interpret the global scope of the script
			m_interpreter.exec(m_script);
			
			System.out.println("Script Compiled");
			
			m_ready = true;
			
			for (PyInterpreterCallback i : m_callback)
				i.onEndRun();
		}
		catch(PyException e)
		{
			for (PyInterpreterCallback i : m_callback)
				i.onException(e);
			m_ready = false;
		}
	}

	public boolean isReady()
	{
		return m_ready;
	}
	
	public void setScript(String p_text)
	{
		m_script = p_text;
	}

	public String getScript()
	{
		return  m_script;
	}
	
	public void setName(String p_name)
	{
		m_name = p_name;
	}
	
	public String getName()
	{
		return m_name;
	}

	/**
	 * Adds a callback to this interpreter.
	 * @return True if this object is unique and successfully added.
	 */
	public boolean addCallback(PyInterpreterCallback p_callback)
	{
		if (!m_callback.contains(p_callback))
			return m_callback.add(p_callback);
		return false;
	}
	
	/**
	 * Remove a callback from this interpreter.
	 * @return True if successfully removed
	 */
	public boolean removeCallback(PyInterpreterCallback p_callback)
	{
		return m_callback.remove(p_callback);
	}
	
	/**
	 * Set the stream in which this interpreter will output to.
	 */
	public void setOutputStream(OutputStream p_outputStream)
	{
		m_interpreter.setOut(p_outputStream);
		m_interpreter.setErr(p_outputStream);
	}
	
	/**
	 * Get a global variable as a string.
	 * @return Null if the variable could not be found
	 */
	public String getGlobalString(String p_name)
	{
		try
		{
			PyObject obj = m_interpreter.get(p_name);
			if (obj == null)
				return null;
			return obj.asString();
		}
		catch(PyException e)
		{
			for (PyInterpreterCallback i : m_callback)
				i.onException(e);
			return null;
		}
	}
	
	/**
	 * Get a global variable as a PyObject.
	 * Use this to get a global function and call it with call() method in this class.
	 */
	public PyObject getGlobal(String p_name)
	{
		try
		{
			return m_interpreter.get(p_name);
		}
		catch(PyException e)
		{
			for (PyInterpreterCallback i : m_callback)
				i.onException(e);
			return null;
		}
	}
	
	/**
	 * Call a global python function.
	 * @param p_obj
	 * @param p_params
	 */
	public void call(PyObject p_obj, Object[] p_params)
	{
		try
		{
			p_obj._jcall(p_params);
		}
		catch(PyException e)
		{
			for (PyInterpreterCallback i : m_callback)
				i.onException(e);
			m_ready = false;
		}
	}
}
