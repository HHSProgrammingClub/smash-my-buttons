package graphics;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartMenu implements Page
{
	private JPanel m_panel;
	public StartMenu()
	{
		setUpPanel();
	}
	
	private void setUpPanel()
	{
		m_panel.setLayout(new FormLayout(new ColumnSpec[]
				{
				ColumnSpec.decode("197px"),
				ColumnSpec.decode("57px"),},
			new RowSpec[] {
				RowSpec.decode("210px"),
				RowSpec.decode("23px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//go to character select
				
			}
		});
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//quit
			}
		});
		
		m_panel.add(startButton, "2, 2, center, center");
		m_panel.add(quitButton, "2, 4, center, center");
	}

	@Override
	public JComponent getComponent()
	{
		return m_panel;
	}
}
