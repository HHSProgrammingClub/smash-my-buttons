package pythonAI.editor;

import java.awt.BorderLayout;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JCheckBox;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class OutputTextArea extends JPanel
{
	private static final long serialVersionUID = -3513854888514447837L;
	
	private JTextArea m_outputTA;
	private JScrollPane m_outputScrollPane;
	
	private ByteArrayOutputStream m_outputStream = new ByteArrayOutputStream();
	private PrintStream m_printStream = new PrintStream(m_outputStream)
	{
		@Override
		public void flush()
		{
			super.flush();
			m_outputTA.setText(m_outputStream.toString());
		}
	};
	private JPanel panel;
	private JButton btnClear;
	private JCheckBox chckbxLockAtBottom;;

	public OutputTextArea()
	{
		super(new BorderLayout());
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(1);
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel, BorderLayout.NORTH);
		
		JLabel lbl = new JLabel("Output");
		panel.add(lbl);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		btnClear.setToolTipText("Clear all output text");
		panel.add(btnClear);
		
		chckbxLockAtBottom = new JCheckBox("Lock at bottom");
		chckbxLockAtBottom.setSelected(true);
		panel.add(chckbxLockAtBottom);
		
		m_outputTA = new JTextArea();
		m_outputTA.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				if (chckbxLockAtBottom.isSelected())
					m_outputScrollPane.getVerticalScrollBar().setValue(
						m_outputScrollPane.getVerticalScrollBar().getMaximum());
			}
		});
		m_outputTA.setFont(new Font("Consolas", Font.PLAIN, 15));
		m_outputTA.setEditable(false);
		m_outputScrollPane = new JScrollPane(m_outputTA);
		m_outputScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		m_outputScrollPane.getVerticalScrollBar().getValue();
		add(m_outputScrollPane);
		
	}
	
	public void clear()
	{
		m_outputStream.reset();
		m_outputTA.setText("");
	}
	
	public PrintStream getOutputStream()
	{
		return m_printStream;
	}
}
