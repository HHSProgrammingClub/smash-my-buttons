package graphics.pages;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.dyn4j.geometry.MassType;

import gameObject.*;
import graphics.GUI;
import graphics.Texture;
import program.Clock;
import program.PhysicsWorld;
import program.Scene;
import resourceManager.ResourceManager;

public class StartMenu implements Page
{
	private JPanel m_panel = new JPanel();
	
	public StartMenu(GUI p_gui)
	{
		setUpPanel(p_gui);
	}
	
	private Thread jank;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void setUpPanel(final GUI p_gui)
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
				System.exit(0);
			}
		});
		
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Renderer rend = new Renderer();
				
				p_gui.setPage(rend);
				
				jank = new Thread(new Runnable()
						{
							public void run()
							{
								Scene jankScene = new Scene();
								
								
								GameObject jankObject = new GameObject();
								
								SpriteComponent jankSprite = jankObject.addComponent(SpriteComponent.class);
								
								jankSprite.setTexture(ResourceManager.getResource(Texture.class, "resources/images/birboi"));
								jankSprite.setAnimation("idle");
								
								jankObject.addComponent(PhysicsComponent.class);
								BoxColliderComponent jankCollider = jankObject.addComponent(BoxColliderComponent.class);
								
								jankCollider.setSize(1, 2);
								jankCollider.getFixture().setDensity(1);
								
								jankScene.addObject(jankObject);
								
								
								GameObject jankPlatform = new GameObject();
								
								SpriteComponent platSprite = jankPlatform.addComponent(SpriteComponent.class);
								
								platSprite.setTexture(ResourceManager.getResource(Texture.class, "resources/images/testing_ground"));
								platSprite.setAnimation("ground");
								
								PhysicsComponent platPhys = jankPlatform.addComponent(PhysicsComponent.class);
								BoxColliderComponent platBox = jankPlatform.addComponent(BoxColliderComponent.class);
								platPhys.getBody().setMassType(MassType.INFINITE);
								platBox.setSize(4, 1);
								platPhys.getBody().translate(-1, 4);
								
								jankScene.addObject(jankPlatform);
								
								
								PhysicsWorld jankPhysics = new PhysicsWorld();
								
								Clock gameClock = new Clock();
								float delta = 0;
								
								while(true)
								{
									delta = gameClock.getElapse();
									gameClock.restart();
									
									rend.clear();
									
									jankScene.receiveMessage(new OnRender(rend));
									
									jankPhysics.update(delta, jankScene);
									
									rend.display();
								}
							}
						});
				
				jank.start();
			}
		});
		String titleText;
		String[] epicTitles = {
			"Code Fighters!",
			"AI Fighters",
			"AI-Vengers Infinity War",
			"Thanos Wholly Approves of This Event",
			"Alexa, Play Despacito",
			"Super Smash Code Ultimate",
			"Code Fight",
			"Smash 'em up",
			"Smash my Buttons"
		};
		JLabel lblAwesomeTitleText = new JLabel(epicTitles[(int)(Math.random() * epicTitles.length)]);
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
