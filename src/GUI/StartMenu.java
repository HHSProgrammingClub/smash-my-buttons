package GUI;

import javax.swing.JPanel;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormSpecs;

public class StartMenu extends JPanel
{
	public StartMenu()
	{
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//go to character select
			}
		});
		setLayout(new FormLayout(new ColumnSpec[]
				{
				ColumnSpec.decode("197px"),
				ColumnSpec.decode("57px"),},
			new RowSpec[] {
				RowSpec.decode("210px"),
				RowSpec.decode("23px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("23px"),}));
		add(startButton, "2, 2, center, center");
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//quit
			}
		});
		add(quitButton, "2, 4, center, center");
		
	}
}
