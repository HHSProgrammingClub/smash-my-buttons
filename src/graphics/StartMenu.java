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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;

public class StartMenu implements Page
{
	private JPanel m_panel = new JPanel();
	
	public StartMenu(GUI p_gui)
	{
		setUpPanel(p_gui);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void setUpPanel(GUI p_gui)
	{
		GridBagLayout gbl_m_panel = new GridBagLayout();
		gbl_m_panel.columnWidths = new int[]{0, 57, 0, 0};
		gbl_m_panel.rowHeights = new int[]{0, 0, 100, 23, 23, 0, 0};
		gbl_m_panel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_m_panel.rowWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		m_panel.setLayout(gbl_m_panel);
		
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//quit
			}
		});
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				p_gui.setPage(new CharacterSelect());
			}
		});
		
		JLabel lblAwesomeTitleText = new JLabel("Awesome Title Text");
		lblAwesomeTitleText.setFont(new Font("Verdana", Font.BOLD, 28));
		GridBagConstraints gbc_lblAwesomeTitleText = new GridBagConstraints();
		gbc_lblAwesomeTitleText.insets = new Insets(0, 0, 5, 5);
		gbc_lblAwesomeTitleText.gridx = 1;
		gbc_lblAwesomeTitleText.gridy = 1;
		m_panel.add(lblAwesomeTitleText, gbc_lblAwesomeTitleText);
		
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 1;
		gbc_startButton.gridy = 3;
		m_panel.add(startButton, gbc_startButton);
		GridBagConstraints gbc_quitButton = new GridBagConstraints();
		gbc_quitButton.insets = new Insets(0, 0, 5, 5);
		gbc_quitButton.gridx = 1;
		gbc_quitButton.gridy = 4;
		m_panel.add(quitButton, gbc_quitButton);
	}

	@Override
	public JComponent getComponent()
	{
		return m_panel;
	}
}
