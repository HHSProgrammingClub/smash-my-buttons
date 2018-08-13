package pythonAI.editor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EditorManager
{
	private Map<File, Editor> m_editors = new HashMap<File, Editor>();
	
	private static EditorManager staticInstance = new EditorManager();
	public static EditorManager getInstance()
	{
		return staticInstance;
	}
	
	/**
	 * Open an editor corresponding to a specific file.
	 */
	public Editor openEditor(File p_File)
	{
		if (m_editors.containsKey(p_File))
			return m_editors.get(p_File);
		
		try
		{
			Editor newEditor = new Editor(p_File);
			newEditor.setVisible(true);
			m_editors.put(p_File, newEditor);
			return newEditor;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
