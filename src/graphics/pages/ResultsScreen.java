package graphics.pages;

import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import graphics.GUI;
import program.CharacterController;
import program.PlayerController;
import pythonAI.AIController;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;

public class ResultsScreen implements Page
{
	private JPanel m_panel = new JPanel();
	private String m_winnerName;
	
	public ResultsScreen(CharacterController p_winner, GUI p_gui)
	{
		if(p_winner instanceof PlayerController)
			m_winnerName = p_winner.getCharacter().getName();
		else if(p_winner instanceof AIController)
			m_winnerName = p_winner.getAuthor();
		
		setUpPage(p_gui);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void setUpPage(GUI p_gui)
	{
		GridBagLayout layout = new GridBagLayout();
		layout.columnWeights = new double[]{1.0, 0.0, 1.0};
		layout.columnWidths = new int[]{0, 0, 0};
		layout.rowWeights = new double[]{1.0, 0.0, 1.0};
		layout.rowHeights = new int[]{0, 0, 100};
		m_panel.setLayout(layout);
		
		JLabel lblWins = new JLabel(m_winnerName + " wins!");
		GridBagConstraints gbc_lblWins = new GridBagConstraints();
		gbc_lblWins.insets = new Insets(0, 0, 5, 5);
		gbc_lblWins.gridx = 1;
		gbc_lblWins.gridy = 1;
		m_panel.add(lblWins, gbc_lblWins);
		
		JButton btnContinue = new JButton("Continue");
		GridBagConstraints gbc_btnContinue = new GridBagConstraints();
		gbc_btnContinue.insets = new Insets(0, 0, 0, 5);
		gbc_btnContinue.gridx = 1;
		gbc_btnContinue.gridy = 2;
		btnContinue.addActionListener(
				(e) -> p_gui.setPage(new CharacterSelect(p_gui)));
		m_panel.add(btnContinue, gbc_btnContinue);
	}
	
	@Override
	public JComponent getComponent()
	{
		return m_panel;
	}

}
