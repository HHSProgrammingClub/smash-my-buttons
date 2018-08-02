package program;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import characters.Character;

interface KeyBindAction
{
	public void onAction();
}

class KeyBinder
{
	public static final int ACTIVATION_PRESSED = 0;
	public static final int ACTIVATION_DOWN = 1;
	public static final int ACTIVATION_RELEASE = 2;
	
	private class KeyBinding
	{
		private static final int STATE_UP = 0;
		private static final int STATE_PRESSED = 1;
		private static final int STATE_DOWN = 2;
		private static final int STATE_RELEASE = 3;
		
		/**
		 * Key code of this binding.
		 */
		private int m_key = 0;
		
		/**
		 * True when key is currently being pressed down. False, otherwise.
		 */
		private int m_state = STATE_UP;
		
		private int m_activate = ACTIVATION_DOWN;
		
		private String m_action;

		public KeyBinding(int p_key, String p_action, int p_activation)
		{
			m_key = p_key;
			m_action = p_action;
			m_activate = p_activation;
		}
		
		public int getKey()
		{
			return m_key;
		}
		
		public void press()
		{
			if (m_state != STATE_DOWN)
			{
				m_state = STATE_PRESSED;
				System.out.println("pressed");
			}
		}
		
		public void release()
		{
			m_state = STATE_RELEASE;
		}
		
		public void update()
		{
			switch(m_state)
			{
			case STATE_PRESSED:
				m_state = STATE_DOWN;
				break;
			case STATE_RELEASE:
				m_state = STATE_UP;
				break;
			}
		}
		
		public boolean isActive()
		{
			switch(m_activate)
			{
			case ACTIVATION_RELEASE:
				return m_state == STATE_RELEASE;
			case ACTIVATION_PRESSED:
				return m_state == STATE_PRESSED;
			case ACTIVATION_DOWN:
				return m_state == STATE_PRESSED ||
						m_state == STATE_DOWN;
			}
			return false;
		}
		
		public String getAction()
		{
			return m_action;
		}
	}
	
	private Map<String, KeyBindAction> m_keyActions = new HashMap<String, KeyBindAction>();
	private Map<String, ArrayList<KeyBinding>> m_groups = new HashMap<String, ArrayList<KeyBinding>>();
	private ArrayList<KeyBinding> m_currentGroup;
	
	private KeyListener m_keyListener;
	
	public KeyBinder()
	{
		m_keyListener = new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (m_currentGroup != null)
					for (KeyBinding i : m_currentGroup)
						if (e.getKeyCode() == i.getKey())
							i.press();
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (m_currentGroup != null)
					for (KeyBinding i : m_currentGroup)
						if (e.getKeyCode() == i.getKey())
							i.release();
			}

			@Override
			public void keyTyped(KeyEvent e)
			{
				// Do nothing...
			}
		};
	}
	
	/**
	 * Add a function to be called when a key is down.
	 * You bind keys to the name of this action.
	 * @param p_name Name of action. Use this in your key bindings.
	 * @param p_action Function to be called when a key is down.
	 */
	public void addAction(String p_name, KeyBindAction p_action)
	{
		m_keyActions.put(p_name, p_action);
	}
	
	public void removeAction(String p_name)
	{
		m_keyActions.remove(p_name);
	}
	
	/**
	 * Set the current key binding group.
	 * @param p_name Name of group.
	 */
	public void setGroup(String p_name)
	{
		if (!m_groups.containsKey(p_name))
			m_groups.put(p_name, new ArrayList<KeyBinding>());
		m_currentGroup = m_groups.get(p_name);
	}
	
	/**
	 * Add a key binding to the current group.
	 * @param p_action Name of the action to call when pressed.
	 * @param p_keyCode Key code from KeyEvent class.
	 */
	public void addKeyBinding(String p_action, int p_keyCode, int p_activation)
	{
		if (m_currentGroup == null)
			throw new IllegalStateException("Group unspecified.");
		m_currentGroup.add(new KeyBinding(p_keyCode, p_action, p_activation));
	}
	
	// Not supported or useful yet
	/*public void removeKeyBinding(String p_name)
	{
		if (m_currentGroup == null)
			throw new IllegalStateException("Group unspecified.");
		m_currentGroup.remove(p_name);
	}*/
	
	/**
	 * Check if a key is down in the current group and call its action
	 * if it is.
	 */
	public void update()
	{
		if (m_currentGroup == null)
			throw new IllegalStateException("Group unspecified.");
		
		
		for (KeyBinding i : m_currentGroup)
		{
			if (i.isActive())
			{
				KeyBindAction action = m_keyActions.get(i.getAction());
				if (action  == null)
					throw new RuntimeException("Action \"" + i.getAction() + "\" does not exist.");
				else
					action.onAction();
			}
			i.update();
		}
	}
	
	public KeyListener getKeyListener()
	{
		return m_keyListener;
	}
}

public class PlayerController extends CharacterController
{
	private graphics.Page m_page;
	
	private KeyBinder m_keyBinder = new KeyBinder();
	
	public PlayerController()
	{
		// Define the actions
		m_keyBinder.addAction("jump", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_JUMP);
			}});
		
		m_keyBinder.addAction("moveLeft", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_MOVELEFT);
			}});
		
		m_keyBinder.addAction("moveRight", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_MOVERIGHT);
			}});
		
		m_keyBinder.addAction("stopRunning", new KeyBindAction()
				{
					@Override
					public void onAction()
					{
						getCharacter().stopRunning();
					}
				});
		
		m_keyBinder.addAction("jab", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_JAB);
			}});
		
		m_keyBinder.addAction("tilt", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_TILT);
			}});
		
		m_keyBinder.addAction("smash", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_SMASH);
			}});
		
		m_keyBinder.addAction("signature", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_SIGNATURE);
			}});
		
		m_keyBinder.addAction("recover", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_RECOVERY);
			}});
		
		m_keyBinder.addAction("projectile", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().performAction(Character.ACTION_PROJECTILE);
			}});
		
		// Add key bindings for player 1
		m_keyBinder.setGroup("player1");
		m_keyBinder.addKeyBinding("jump", KeyEvent.VK_W, KeyBinder.ACTIVATION_PRESSED);
		m_keyBinder.addKeyBinding("moveLeft", KeyEvent.VK_A, KeyBinder.ACTIVATION_DOWN);
		m_keyBinder.addKeyBinding("moveRight", KeyEvent.VK_D, KeyBinder.ACTIVATION_DOWN);
		m_keyBinder.addKeyBinding("stopRunning", KeyEvent.VK_A, KeyBinder.ACTIVATION_RELEASE);
		m_keyBinder.addKeyBinding("stopRunning", KeyEvent.VK_D, KeyBinder.ACTIVATION_RELEASE);
		
		m_keyBinder.addKeyBinding("projectile", KeyEvent.VK_S, KeyBinder.ACTIVATION_PRESSED); //I would say this is for some well thought out reason but it's because I can't find the KeyEvent corresponding to the originally planned ` key
		m_keyBinder.addKeyBinding("jab", KeyEvent.VK_1, KeyBinder.ACTIVATION_PRESSED);
		m_keyBinder.addKeyBinding("tilt", KeyEvent.VK_2, KeyBinder.ACTIVATION_PRESSED);
		m_keyBinder.addKeyBinding("smash", KeyEvent.VK_3, KeyBinder.ACTIVATION_PRESSED);
		m_keyBinder.addKeyBinding("signature", KeyEvent.VK_4, KeyBinder.ACTIVATION_PRESSED);
		m_keyBinder.addKeyBinding("recover", KeyEvent.VK_5, KeyBinder.ACTIVATION_PRESSED);
		

		// Add key bindings for player 2
		m_keyBinder.setGroup("player2");
		//m_keyBinder.addKeyBinding("jump", KeyEvent.VK_UP);
	}
	
	/**
	 * Give this controller a page to receive key events from.
	 * @param p_page
	 */
	public void attachPage(graphics.Page p_page)
	{
		m_page = p_page;
		m_page.getComponent().addKeyListener(m_keyBinder.getKeyListener());
	}
	
	@Override
	public String getName()
	{
		return "User Controlled";
	}
	
	@Override
	public String getAuthor()
	{
		return "N/A";
	}
	
	@Override
	public void start()
	{
		// Use player1 group.
		m_keyBinder.setGroup("player1");
	}

	@Override
	public void update(Battle p_battle, float p_delta)
	{
		// This will automatically call the actions we set earlier.
		m_keyBinder.update();
		m_character.update(p_delta);
	}

	@Override
	public void reset()
	{
		
	}
}
