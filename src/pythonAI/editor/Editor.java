package pythonAI.editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.python.core.PyException;

import pythonAI.PyInterpreter;
import pythonAI.PyInterpreterCallback;
import util.Util;

import java.awt.Dimension;

public class Editor
{
	private JFrame m_window;
	private RSyntaxTextArea m_textArea;
	private PyErrorParser m_parser = new PyErrorParser();
	private PyInterpreter m_pyInterpreter;
	private JButton m_btRun;
	private JButton m_btUndo;
	private JButton m_btRedo;
	private OutputTextArea m_outputConsole;
	private File m_saveFile;
	class PyInterEditorCallback implements PyInterpreterCallback
	{
		PyInterpreter m_pyInterpreter;
		
		public PyInterEditorCallback(PyInterpreter p_pyInterpreter)
		{
			m_pyInterpreter = p_pyInterpreter;
		}
		
		@Override
		public void onException(PyException p_exception)
		{
			handleException(p_exception);
		}

		@Override
		public void onBeginRun()
		{
			m_parser.resetException();
			m_pyInterpreter.setScript(getText());
		}

		@Override
		public void onEndRun()
		{}
	}
	
	public void updateUndoRedoButtons()
	{
		m_btUndo.setEnabled(m_textArea.canUndo());
		m_btRedo.setEnabled(m_textArea.canRedo());
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public Editor()
	{
		m_window = new JFrame("Python Editor");
		m_window.setSize(500, 500);
		m_window.setResizable(true);
		m_window.setVisible(false); 
		m_window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JPanel panel = new JPanel(new BorderLayout());
		 
		m_textArea = new RSyntaxTextArea(20, 60);
		m_textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
		m_textArea.setCodeFoldingEnabled(true);
		m_textArea.addParser(m_parser);
		
		m_textArea.removeCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0)
			{
				
			}
			
		});
		
		ErrorStrip errorStrip = new ErrorStrip(m_textArea);
		RTextScrollPane scrollPane = new RTextScrollPane(m_textArea);
		
		JPanel ctrlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		m_btRun = new JButton("Run/Restart");
		m_btRun.setSize(50, 25);
		m_btRun.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				save();
				m_pyInterpreter.run();
			}
		});
		m_btRun.setToolTipText("Run your script! This button automatically save your script.");
		ctrlPanel.add(m_btRun);
		
		JButton btSave = new JButton("Save");
		btSave.setSize(50, 25);
		btSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				save();
			}
		});
		btSave.setToolTipText("Save your script disk!");
		ctrlPanel.add(btSave);
		
		m_btUndo = new JButton("Undo");
		m_btRedo = new JButton("Redo");
		updateUndoRedoButtons();
		
		m_btUndo.setToolTipText("Undo the last thing you edited!");
		m_btUndo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (m_textArea.canUndo())
					m_textArea.undoLastAction();
				updateUndoRedoButtons();
			}
		});
		
		m_btRedo.setToolTipText("Pressed undo but want to go back? Use this to redo your last undo!");
		m_btRedo.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (m_textArea.canRedo())
					m_textArea.redoLastAction();
				updateUndoRedoButtons();
			}
		});
		
		// Because this thing doesn't have an action listener of some kind
		// we will just update the buttons whenever we can somehow...
		scrollPane.addMouseMotionListener(new MouseMotionListener()
		{
			@Override
			public void mouseDragged(MouseEvent arg0) {}

			@Override
			public void mouseMoved(MouseEvent arg0)
			{
				updateUndoRedoButtons();
			}
		});
		scrollPane.addKeyListener(new KeyListener() {
			@Override public void keyPressed(KeyEvent arg0) {}
			@Override public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {
				updateUndoRedoButtons();
			}
			
		});
		
		ctrlPanel.add(m_btUndo);
		ctrlPanel.add(m_btRedo);
		
		panel.add(ctrlPanel, BorderLayout.NORTH);
		panel.add(scrollPane);
		panel.add(errorStrip, BorderLayout.LINE_END);
		
		m_outputConsole = new OutputTextArea();
		m_outputConsole.setPreferredSize(new Dimension(106, 200));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, m_outputConsole);
		splitPane.setDividerLocation(300);
		
		m_window.setContentPane(splitPane);
		m_window.pack();
		m_window.setLocationRelativeTo(null);
	}
	
	public Editor(File p_file) throws IOException
	{
		this();
		openFile(p_file);
	}
	
	public void setVisible(boolean p_visible)
	{
		m_window.setVisible(p_visible);
	}
	
	public void setInterpreter(PyInterpreter p_pyInterpreter)
	{
		m_pyInterpreter = p_pyInterpreter;
		m_pyInterpreter.setOutputStream(m_outputConsole.getOutputStream());
		m_pyInterpreter.addCallback(new PyInterEditorCallback(m_pyInterpreter));
		m_pyInterpreter.setScript(getText());
	}
	
	/**
	 * Displays in the window title as "Script Editor: " + p_str.
	 * @param p_str
	 */
	public void setTitle(String p_str)
	{
		m_window.setTitle("Script Editor: " + p_str);
	}
	
	public void setText(String p_text)
	{
		m_parser.resetException();
		m_textArea.setText(p_text);
		if (m_pyInterpreter != null)
			m_pyInterpreter.setScript(getText());
	}
	
	public String getText()
	{
		return m_textArea.getText();
	}
	
	public void openFile(File p_file) throws IOException
	{
		if (!p_file.exists())
			throw new FileNotFoundException("Could not find file \"" + p_file + "\"");
		String script = Util.getStreamAsString(new FileInputStream(p_file));
		setText(script);
		m_saveFile = p_file;
	}
	
	/**
	 * Set file of the document to save to.
	 * @param p_filepath
	 */
	public void setSaveFile(File p_file)
	{
		m_saveFile = p_file;
	}
	
	public File getSaveFile()
	{
		return m_saveFile;
	}
	
	/**
	 * Save the script to a file.
	 * @see setSaveFilepath
	 */
	public void save()
	{
		if (m_saveFile == null && 
				m_saveFile.canWrite())
			return;
		PrintWriter out = null;
		try
		{
			out = new PrintWriter(m_saveFile);
			out.println(getText());
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
