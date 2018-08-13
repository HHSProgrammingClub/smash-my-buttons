package pythonAI;

import org.python.core.PyException;

public interface PyInterpreterCallback
{
	public void onException(PyException p_exception);
	public void onBeginRun();
	public void onEndRun();
}
