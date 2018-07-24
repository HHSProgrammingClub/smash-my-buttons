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

public class CharacterSelect implements Page
{
	private JPanel m_panel = new JPanel();
	
	public CharacterSelect()
	{
		setUpPanel();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void setUpPanel()
	{
		m_panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:16dlu"),
				ColumnSpec.decode("60dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(10dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:60dlu"),
				ColumnSpec.decode("right:16dlu"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblAi = new JLabel("AI 1");
		lblAi.setHorizontalAlignment(SwingConstants.CENTER);
		m_panel.add(lblAi, "2, 4");
		
		JLabel lblAi_1 = new JLabel("AI 2");
		m_panel.add(lblAi_1, "6, 4");
		
		JButton btnLoadScript = new JButton("Load Script");
		m_panel.add(btnLoadScript, "2, 6");
		
		JButton btnLoadScript_1 = new JButton("Load Script");
		m_panel.add(btnLoadScript_1, "6, 6");
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		m_panel.add(lblPlayer, "2, 10");
		
		JLabel lblPlayer_1 = new JLabel("Player 2");
		lblPlayer_1.setHorizontalAlignment(SwingConstants.CENTER);
		m_panel.add(lblPlayer_1, "6, 10");
																   //gui editor yells at me if i don't do this for now
		JComboBox<String> characterSelector1 = new JComboBox<String>(/*Character.characterNames*/);
		characterSelector1.setSelectedIndex(0);
		m_panel.add(characterSelector1, "2, 12, fill, center");
		
		JComboBox<String> characterSelector2 = new JComboBox<String>(/*Character.characterNames*/);
		characterSelector2.setSelectedIndex(0);
		m_panel.add(characterSelector2, "6, 12, fill, center");
	}

	@Override
	public JComponent getComponent()
	{
		return m_panel;
	}

}
