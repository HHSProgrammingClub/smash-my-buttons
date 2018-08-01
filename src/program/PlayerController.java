package program;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

interface KeyBindAction
{
	public void onAction();
}

class KeyBinder
{
	private class KeyBinding
	{
		/**
		 * Key code of this binding.
		 */
		public int key;
		
		/**
		 * True when key is currently being pressed down. False, otherwise.
		 */
		public boolean down = false;
		
		public String action;

		KeyBinding(int p_key)
		{
			key = p_key;
		}
	}
	
	private Map<String, KeyBindAction> m_keyActions = new HashMap<String, KeyBindAction>();
	private Map<String, Map<String, KeyBinding>> m_groups = new HashMap<String, Map<String, KeyBinding>>();
	private Map<String, KeyBinding> m_currentGroup;
	
	private KeyListener m_keyListener;
	
	public KeyBinder()
	{
		m_keyListener = new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (m_currentGroup != null)
					for (Map.Entry<String, KeyBinding> i : m_currentGroup.entrySet())
						if (e.getKeyCode() == i.getValue().key)
							i.getValue().down = true;
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				if (m_currentGroup != null)
					for (Map.Entry<String, KeyBinding> i : m_currentGroup.entrySet())
						if (e.getKeyCode() == i.getValue().key)
							i.getValue().down = false;
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
			m_groups.put(p_name, new HashMap<String, KeyBinding>());
		m_currentGroup = m_groups.get(p_name);
	}
	
	/**
	 * Add a key binding to the current group.
	 * @param p_action Name of the action to call when pressed.
	 * @param p_keyCode Key code from KeyEvent class.
	 */
	public void addKeyBinding(String p_action, int p_keyCode)
	{
		if (m_currentGroup == null)
			throw new IllegalStateException("Group unspecified.");
		m_currentGroup.put(p_action, new KeyBinding(p_keyCode));
	}
	
	public void removeKeyBinding(String p_name)
	{
		if (m_currentGroup == null)
			throw new IllegalStateException("Group unspecified.");
		m_currentGroup.remove(p_name);
	}
	
	/**
	 * Check if a key is down in the current group and call its action
	 * if it is.
	 */
	public void update()
	{
		if (m_currentGroup == null)
			throw new IllegalStateException("Group unspecified.");
		for (Map.Entry<String, KeyBinding> i : m_currentGroup.entrySet())
			if (i.getValue().down == true)
				m_keyActions.get(i.getKey()).onAction();
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
				getCharacter().jump();
			}});
		
		m_keyBinder.addAction("moveLeft", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().moveLeft();
			}});
		
		m_keyBinder.addAction("moveRight", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().moveRight();
			}});
		
		m_keyBinder.addAction("jab", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().jab();
			}});
		
		m_keyBinder.addAction("tilt", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().tilt();
			}});
		
		m_keyBinder.addAction("smash", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().smash();
			}});
		
		m_keyBinder.addAction("signature", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().signature();
			}});
		
		m_keyBinder.addAction("recover", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().recover();
			}});
		
		m_keyBinder.addAction("projectile", new KeyBindAction()
		{
			@Override
			public void onAction()
			{
				getCharacter().projectile();
			}});
		
		// Add key bindings for player 1
		m_keyBinder.setGroup("player1");
		m_keyBinder.addKeyBinding("jump", KeyEvent.VK_W);
		m_keyBinder.addKeyBinding("moveLeft", KeyEvent.VK_A);
		m_keyBinder.addKeyBinding("moveRight", KeyEvent.VK_D);
		
		m_keyBinder.addKeyBinding("projectile", KeyEvent.VK_S); //I would say this is for some well thought out reason but it's because I can't find the KeyEvent corresponding to the originally planned ` key
		m_keyBinder.addKeyBinding("jab", KeyEvent.VK_1);
		m_keyBinder.addKeyBinding("tilt", KeyEvent.VK_2);
		m_keyBinder.addKeyBinding("smash", KeyEvent.VK_3);
		m_keyBinder.addKeyBinding("signature", KeyEvent.VK_4);
		m_keyBinder.addKeyBinding("recover", KeyEvent.VK_5);
		

		// Add key bindings for player 2
		m_keyBinder.setGroup("player2");
		m_keyBinder.addKeyBinding("jump", KeyEvent.VK_UP);
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
