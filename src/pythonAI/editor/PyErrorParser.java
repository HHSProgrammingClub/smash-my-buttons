package pythonAI.editor;

import java.awt.Color;

import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rsyntaxtextarea.parser.ParserNotice.Level;
import org.python.core.PyException;
import org.python.core.PyTuple;

/**
 * Displays the errors thrown by Jython in the text editor.
 * @author Michael Thompson
 *
 */
public class PyErrorParser extends AbstractParser
{
	private DefaultParseResult m_parseResult;
	
	public PyErrorParser()
	{
		m_parseResult = new DefaultParseResult(this);
	}
	
	public void resetException()
	{
		m_parseResult.clearNotices();
	}
	
	public void handleException(PyException p_exception, RSyntaxTextArea m_textArea)
	{
		m_parseResult.clearNotices();
		
		try {
			String message = p_exception.value.__getitem__(0).asString();
			
			DefaultParserNotice notice = null;
			
			if (p_exception.traceback != null)
			{
				int line = p_exception.traceback.tb_lineno - 1;
				notice = new DefaultParserNotice(this, message, line);
				try
				{
					int offset = m_textArea.getLineStartOffset(line);
					m_textArea.setCaretPosition(offset);
				}
				catch (BadLocationException e)
				{
					// ...
				}
			}
			else
			{
				// why jython why
				int line = p_exception.value.__getitem__(1).__getitem__(1).asInt() - 1;
				int column = p_exception.value.__getitem__(1).__getitem__(2).asInt();
				String text = p_exception.value.__getitem__(1).__getitem__(3).asString();
				
				// Get exact offset to error
				int offset;
				try
				{
					offset = m_textArea.getLineStartOffset(line) + column;
					m_textArea.setCaretPosition(offset);
				}
				catch (BadLocationException e)
				{
					offset = -1;
				}
				notice = new DefaultParserNotice(this, message, line, offset, text.length() - column);
			}
			
			notice.setColor(Color.RED);
			notice.setLevel(Level.ERROR);
			notice.setToolTipText(PyException.exceptionClassName(p_exception.type) + ": " + message);
			m_parseResult.addNotice(notice);
		} catch (Exception e)
		{
			// Right now, any exception means that the position in which
			// the error occurred could not be found.
			e.printStackTrace();
			System.out.println("You may ignore the error above.");
		}
	}
	
	@Override
	public ParseResult parse(RSyntaxDocument p_doc, String p_style)
	{
		System.out.println("parse");
		return m_parseResult;
	}
}
