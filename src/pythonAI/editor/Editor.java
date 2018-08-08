package pythonAI.editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.python.core.PyException;

import pythonAI.PyInterpreter;
import pythonAI.PyInterpreterCallback;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

public class Editor
{
	private JFrame m_window;
	private RSyntaxTextArea m_textArea;
	private PyErrorParser m_parser = new PyErrorParser();
	private PyInterpreter m_pyInterpreter;
	private JButton m_btRun;
	private OutputTextArea m_outputConsole;
	private String m_saveFilepath;
	private PyInterpreterCallback m_callback = new PyInterpreterCallback()
			{
				@Override
				public void onException(PyException p_exception)
				{
					handleException(p_exception);
				}

				@Override
				public void onReinitialize()
				{
					m_parser.resetException();
					m_textArea.setText(m_pyInterpreter.getScript());
				}
			};
	
	public Editor()
	{
		m_window = new JFrame("Python Editor");
		m_window.setSize(600, 800);
		m_window.setResizable(true);
		m_window.setVisible(false); 
		m_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new BorderLayout());
		 
		m_textArea = new RSyntaxTextArea(20, 60);
		m_textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
		m_textArea.setCodeFoldingEnabled(true);
		m_textArea.addParser(m_parser);
		
		ErrorStrip errorStrip = new ErrorStrip(m_textArea);
		RTextScrollPane scrollPane = new RTextScrollPane(m_textArea);
		
		JPanel ctrlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		m_btRun = new JButton();
		m_btRun.setText("Run/Restart");
		m_btRun.setSize(50, 25);
		m_btRun.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				save();
				m_pyInterpreter.setScript(m_textArea.getText());
				m_pyInterpreter.reinitialize();
			}
		});
		m_btRun.setToolTipText("Run your script");
		ctrlPanel.add(m_btRun);
		
		panel.add(ctrlPanel, BorderLayout.NORTH);
		panel.add(scrollPane);
		panel.add(errorStrip, BorderLayout.LINE_END);
		
		m_outputConsole = new OutputTextArea();
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, m_outputConsole);
		
		m_window.setContentPane(splitPane);
		m_window.pack();
		m_window.setLocationRelativeTo(null);
	}
	
	public void setVisible(boolean p_visible)
	{
		m_window.setVisible(p_visible);
	}
	
	public void setInterpreter(PyInterpreter p_pyInterpreter)
	{
		m_pyInterpreter = p_pyInterpreter;
		p_pyInterpreter.setOutputStream(m_outputConsole.getOutputStream());
		p_pyInterpreter.setCallback(m_callback);
		m_textArea.setText(p_pyInterpreter.getScript());
	}
	
	/**
	 * Displays in the window title as "Script Editor: " + p_str.
	 * @param p_str
	 */
	public void setTitle(String p_str)
	{
		m_window.setTitle("Script Editor: " + p_str);
	}
	
	/**
	 * Set filepath of the document to save to.
	 * @param p_filepath
	 */
	public void setSaveFilepath(String p_filepath)
	{
		m_saveFilepath = p_filepath;
	}
	
	/**
	 * Save the script to a file.
	 * @see setSaveFilepath
	 */
	public void save()
	{
		if (m_saveFilepath == null)
			return;
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(m_saveFilepath);
			out.println(m_textArea.getText());
			out.flush();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();
		}
	}
	
	private void handleException(PyException p_exception)
	{
		m_window.setVisible(true);
		m_window.requestFocus();
		m_outputConsole.getOutputStream().println(p_exception);
		m_outputConsole.getOutputStream().flush();
		m_parser.handleException(p_exception, m_textArea);
	}
}
