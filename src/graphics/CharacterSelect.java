package graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import characters.Character;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterSelect implements Page
{
	private JPanel m_panel = new JPanel();
	
	public CharacterSelect(GUI p_gui)
	{
		setUpPanel(p_gui);
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void setUpPanel(GUI p_gui)
	{
		GridBagLayout gbl_m_panel = new GridBagLayout();
		gbl_m_panel.columnWidths = new int[]{0, 89, 200, 89, 0, 0};
		gbl_m_panel.rowHeights = new int[]{40, 14, 23, 31, 14, 20, 65, 0};
		gbl_m_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_m_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		m_panel.setLayout(gbl_m_panel);
		
		JLabel lblAi = new JLabel("AI 1");
		lblAi.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblAi = new GridBagConstraints();
		gbc_lblAi.anchor = GridBagConstraints.NORTH;
		gbc_lblAi.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAi.insets = new Insets(0, 0, 5, 5);
		gbc_lblAi.gridx = 1;
		gbc_lblAi.gridy = 1;
		m_panel.add(lblAi, gbc_lblAi);
		
		JLabel lblAi_1 = new JLabel("AI 2");
		GridBagConstraints gbc_lblAi_1 = new GridBagConstraints();
		gbc_lblAi_1.anchor = GridBagConstraints.NORTH;
		gbc_lblAi_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblAi_1.gridx = 3;
		gbc_lblAi_1.gridy = 1;
		m_panel.add(lblAi_1, gbc_lblAi_1);
		
		JButton ai1ScriptLoad = new JButton("Load Script");
		GridBagConstraints gbc_ai1ScriptLoad = new GridBagConstraints();
		gbc_ai1ScriptLoad.anchor = GridBagConstraints.NORTH;
		gbc_ai1ScriptLoad.fill = GridBagConstraints.HORIZONTAL;
		gbc_ai1ScriptLoad.insets = new Insets(0, 0, 5, 5);
		gbc_ai1ScriptLoad.gridx = 1;
		gbc_ai1ScriptLoad.gridy = 2;
		m_panel.add(ai1ScriptLoad, gbc_ai1ScriptLoad);
		
		JButton ai2ScriptLoad = new JButton("Load Script");
		GridBagConstraints gbc_ai2ScriptLoad = new GridBagConstraints();
		gbc_ai2ScriptLoad.anchor = GridBagConstraints.NORTH;
		gbc_ai2ScriptLoad.insets = new Insets(0, 0, 5, 5);
		gbc_ai2ScriptLoad.gridx = 3;
		gbc_ai2ScriptLoad.gridy = 2;
		m_panel.add(ai2ScriptLoad, gbc_ai2ScriptLoad);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.anchor = GridBagConstraints.NORTH;
		gbc_lblPlayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPlayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer.gridx = 1;
		gbc_lblPlayer.gridy = 4;
		m_panel.add(lblPlayer, gbc_lblPlayer);
		
		JLabel lblPlayer_1 = new JLabel("Player 2");
		lblPlayer_1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPlayer_1 = new GridBagConstraints();
		gbc_lblPlayer_1.anchor = GridBagConstraints.NORTH;
		gbc_lblPlayer_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer_1.gridx = 3;
		gbc_lblPlayer_1.gridy = 4;
		m_panel.add(lblPlayer_1, gbc_lblPlayer_1);
		//gui editor yells at me if i don't do this for now
		JComboBox<String> characterSelector1 = new JComboBox<String>(Character.characterNames);
		characterSelector1.setSelectedIndex(0);
		GridBagConstraints gbc_characterSelector1 = new GridBagConstraints();
		gbc_characterSelector1.fill = GridBagConstraints.HORIZONTAL;
		gbc_characterSelector1.insets = new Insets(0, 0, 5, 5);
		gbc_characterSelector1.gridx = 1;
		gbc_characterSelector1.gridy = 5;
		m_panel.add(characterSelector1, gbc_characterSelector1);
		
		JComboBox<String> characterSelector2 = new JComboBox<String>(Character.characterNames);
		characterSelector2.setSelectedIndex(0);
		GridBagConstraints gbc_characterSelector2 = new GridBagConstraints();
		gbc_characterSelector2.insets = new Insets(0, 0, 5, 5);
		gbc_characterSelector2.fill = GridBagConstraints.HORIZONTAL;
		gbc_characterSelector2.gridx = 3;
		gbc_characterSelector2.gridy = 5;
		m_panel.add(characterSelector2, gbc_characterSelector2);
		
		JButton btnStartFight = new JButton("Start Fight");
		GridBagConstraints gbc_btnStartFight = new GridBagConstraints();
		gbc_btnStartFight.insets = new Insets(0, 0, 0, 5);
		gbc_btnStartFight.gridx = 2;
		gbc_btnStartFight.gridy = 6;
		btnStartFight.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						p_gui.setPage(p_gui.getRenderer());
					}
				});
		m_panel.add(btnStartFight, gbc_btnStartFight);
		
		
	}

	@Override
	public JComponent getComponent()
	{
		return m_panel;
	}

}
