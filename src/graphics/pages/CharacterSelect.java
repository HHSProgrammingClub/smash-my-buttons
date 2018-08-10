package graphics.pages;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import characters.Character;
import graphics.GUI;
import characters.*;
import program.Battle;
import program.CharacterController;
import program.PlayerController;
import pythonAI.AIController;
import stages.Stage;
import stages.MainStage;

/*class EnvironmentComboBoxRenderer extends BasicComboBoxRenderer
{
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus)
	{
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		Environment stage = (Environment) value;
		setText(stage.getName());
		return this;
	}
}*/

public class CharacterSelect implements Page
{
	private JPanel m_panel = new JPanel();
	private final JFileChooser m_fileChooser = new JFileChooser();
	
	private CharacterController m_p1;
	private CharacterController m_p2;
	
	private String m_chosenStageName;
	
	private static Stage newStage(String p_name)
	{
		return new MainStage();
	}
	
	public CharacterSelect(GUI p_gui)
	{
		m_fileChooser.setCurrentDirectory(new File("."));
		m_p1 = new PlayerController();
		m_p2 = new PlayerController();
		setUpPanel(p_gui);
	}
	
	private static AIController createAIController(String p_filePath)
	{
		AIController controller = new AIController();
        try
		{
        	controller.openFile(p_filePath);
		} catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
			return null;
		} catch (IOException e1)
		{
			e1.printStackTrace();
			return null;
		}
        if (controller.getTargetCharacter() != null)
        	controller.setCharacter(CharacterFactory.create(controller.getTargetCharacter()));
        return controller;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void setUpPanel(GUI p_gui)
	{
		GridBagLayout gbl_m_panel = new GridBagLayout();
		gbl_m_panel.columnWidths = new int[]{0, 89, 200, 89, 0, 0};
		gbl_m_panel.rowHeights = new int[]{40, 0, 14, 0, 23, 14, 20, 65, 0};
		gbl_m_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_m_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		m_panel.setLayout(gbl_m_panel);
		
		JButton ai1ScriptLoad = new JButton("Load Script");
		GridBagConstraints gbc_ai1ScriptLoad = new GridBagConstraints();
		gbc_ai1ScriptLoad.anchor = GridBagConstraints.NORTH;
		gbc_ai1ScriptLoad.fill = GridBagConstraints.HORIZONTAL;
		gbc_ai1ScriptLoad.insets = new Insets(0, 0, 5, 5);
		gbc_ai1ScriptLoad.gridx = 1;
		gbc_ai1ScriptLoad.gridy = 4;
		ai1ScriptLoad.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int returnVal = m_fileChooser.showOpenDialog(p_gui.getWindow());
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		            File file = m_fileChooser.getSelectedFile();
		            String filePath = file.getPath();
		            System.out.println("Attempting to load " + filePath + " as player 1");
		            
		            m_p1 = createAIController(filePath);
		            
		            if(m_p2 instanceof AIController) {
		            	((AIController)m_p2).setEnemyCharacter(m_p1.getCharacter());
		            }
		            ((AIController)m_p1).setEnemyCharacter(m_p2.getCharacter());
		            System.out.print("Player 1 AI script loaded at " + filePath);
		        }
			}
		});
		ai1ScriptLoad.setEnabled(false);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPlayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer.gridx = 1;
		gbc_lblPlayer.gridy = 1;
		m_panel.add(lblPlayer, gbc_lblPlayer);
		
		JLabel lblPlayer2 = new JLabel("Player 2");
		lblPlayer2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblPlayer_1 = new GridBagConstraints();
		gbc_lblPlayer_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer_1.gridx = 3;
		gbc_lblPlayer_1.gridy = 1;
		m_panel.add(lblPlayer2, gbc_lblPlayer_1);
		
		
		JButton ai2ScriptLoad = new JButton("Load Script");
		GridBagConstraints gbc_ai2ScriptLoad = new GridBagConstraints();
		gbc_ai2ScriptLoad.anchor = GridBagConstraints.NORTH;
		gbc_ai2ScriptLoad.insets = new Insets(0, 0, 5, 5);
		gbc_ai2ScriptLoad.gridx = 3;
		gbc_ai2ScriptLoad.gridy = 4;
		ai2ScriptLoad.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int returnVal = m_fileChooser.showOpenDialog(p_gui.getWindow());
		        if (returnVal == JFileChooser.APPROVE_OPTION)
		        {
		            File file = m_fileChooser.getSelectedFile();
		            String filePath = file.getPath();
		            System.out.println("Attempting to load " + filePath + " as player 2");
		            
		            m_p2 = createAIController(filePath);
		            ((AIController)m_p2).setEnemyCharacter(m_p1.getCharacter());
		            if(m_p1 instanceof AIController) {
		            	((AIController)m_p1).setEnemyCharacter(m_p2.getCharacter());
		            }
		            System.out.print("Player 2 AI script loaded at " + filePath);
		        }
			}
		});
		
		JComboBox<String> stageSelect = new JComboBox<String>();
		for (String i : Stage.stageNames)
			stageSelect.addItem(i);
		stageSelect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JComboBox<String> bawks = (JComboBox<String>)e.getSource();
		        m_chosenStageName = (String)bawks.getSelectedItem();
		        System.out.println("Stage set to " + m_chosenStageName);
			}
		});
		GridBagConstraints gbc_stageSelect = new GridBagConstraints();
		gbc_stageSelect.insets = new Insets(0, 0, 5, 5);
		gbc_stageSelect.gridx = 2;
		gbc_stageSelect.gridy = 2;
		m_panel.add(stageSelect, gbc_stageSelect);
		ai2ScriptLoad.setEnabled(false);
		m_panel.add(ai2ScriptLoad, gbc_ai2ScriptLoad);
		
		JComboBox<String> characterSelector1 = new JComboBox<String>();
		for (String i : Character.characterNames)
			characterSelector1.addItem(i);
		characterSelector1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JComboBox<String> bawks = (JComboBox<String>)e.getSource();
		        String characterName = (String)bawks.getSelectedItem();
		        m_p1.setCharacter(CharacterFactory.create(characterName));
		        System.out.println("Player 1 character set to " + characterName);
			}
		});
		
		
		
		JButton btnStartFight = new JButton("Start Fight");
		GridBagConstraints gbc_btnStartFight = new GridBagConstraints();
		gbc_btnStartFight.insets = new Insets(0, 0, 5, 5);
		gbc_btnStartFight.gridx = 2;
		gbc_btnStartFight.gridy = 5;
		btnStartFight.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Battle royale = new Battle();
						royale.setStage(newStage(m_chosenStageName));
						royale.addCharacter(m_p1, 1);
						royale.addCharacter(m_p2, 2);
						royale.startBattle(p_gui);
					}
				});

		GridBagConstraints gbc_p2AiCheckBox = new GridBagConstraints();
		gbc_p2AiCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_p2AiCheckBox.gridx = 3;
		gbc_p2AiCheckBox.gridy = 2;
		m_panel.add(btnStartFight, gbc_btnStartFight);
		
		
		characterSelector1.setSelectedIndex(0);
		GridBagConstraints gbc_characterSelector1 = new GridBagConstraints();
		gbc_characterSelector1.fill = GridBagConstraints.HORIZONTAL;
		gbc_characterSelector1.insets = new Insets(0, 0, 5, 5);
		gbc_characterSelector1.gridx = 1;
		gbc_characterSelector1.gridy = 6;
		m_panel.add(characterSelector1, gbc_characterSelector1);
		
		JComboBox<String> characterSelector2 = new JComboBox<String>();
		for (String i : Character.characterNames)
			characterSelector2.addItem(i);
		characterSelector2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JComboBox<String> bawks = (JComboBox<String>)e.getSource();
		        String characterName = (String)bawks.getSelectedItem();
		        m_p2.setCharacter(CharacterFactory.create(characterName));
		        System.out.println("Player 2 character set to " + characterName);
			}
		});
		characterSelector2.setSelectedIndex(0);
		GridBagConstraints gbc_characterSelector2 = new GridBagConstraints();
		gbc_characterSelector2.insets = new Insets(0, 0, 5, 5);
		gbc_characterSelector2.fill = GridBagConstraints.HORIZONTAL;
		gbc_characterSelector2.gridx = 3;
		gbc_characterSelector2.gridy = 6;
		m_panel.add(characterSelector2, gbc_characterSelector2);
		
		
		JCheckBox p1AiCheckBox = new JCheckBox("Is AI");
		p1AiCheckBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.DESELECTED)
				{
					ai1ScriptLoad.setEnabled(false);
					characterSelector1.setEnabled(true);
					m_p1 = new PlayerController();
					System.out.println("Player 1 set to human");
				} 
				else if(e.getStateChange() == ItemEvent.SELECTED)
				{
					ai1ScriptLoad.setEnabled(true);
					characterSelector1.setEnabled(false);
					m_p1 = new PlayerController();
					System.out.println("Player 1 set to AI");
				}
			}
		});
		GridBagConstraints gbc_p1AiCheckBox = new GridBagConstraints();
		gbc_p1AiCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_p1AiCheckBox.gridx = 1;
		gbc_p1AiCheckBox.gridy = 2;
		m_panel.add(p1AiCheckBox, gbc_p1AiCheckBox);
		
		JCheckBox p2AiCheckBox = new JCheckBox("Is AI");
		p2AiCheckBox.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() == ItemEvent.DESELECTED)
				{
					ai2ScriptLoad.setEnabled(false);
					characterSelector2.setEnabled(true);
					m_p2 = new PlayerController();
					System.out.println("Player 2 set to human");
				} 
				else if(e.getStateChange() == ItemEvent.SELECTED)
				{
					ai2ScriptLoad.setEnabled(true);
					characterSelector2.setEnabled(false);
					m_p2 = new PlayerController();
					System.out.println("Player 2 set to AI");
				}
			}
		});
		m_panel.add(p2AiCheckBox, gbc_p2AiCheckBox);
		m_panel.add(ai1ScriptLoad, gbc_ai1ScriptLoad);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				p_gui.setPage(new StartMenu(p_gui));
			}
		});
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 2;
		gbc_btnBack.gridy = 7;
		m_panel.add(btnBack, gbc_btnBack);
		
		
	}

	@Override
	public JComponent getComponent()
	{
		return m_panel;
	}

}
