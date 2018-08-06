package characters.characterStates;

import characters.Character;
import graphics.Animation;

/**
 * Class to hold effects on characters, such as temporary
 * animations or selective ignoring of gravity.
 * 
 * Extend this class for effects that do more than
 * play an animation
 */
public class CharacterState
{
	private Animation m_animation;
	private Float m_duration;
	private float m_timer;
	
	protected Character m_character = null;
	
	private String m_animationName = "";
	
	private boolean m_started = false;
	private boolean m_paused  = false;
	
	private boolean m_indefinite;
	
	/**
	 * Plays p_animation for p_duration seconds
	 * @param p_animation
	 * @param p_duration pass a negative value to play indefinitely
	 */
	public CharacterState(Animation p_animation, float p_duration)
	{
		setAnimation(p_animation);
		setDuration(p_duration);
	}
	
	/**
	 * Plays p_animation once, even if it's set to loop
	 * @param p_animation
	 */
	public CharacterState(Animation p_animation)
	{
		this(p_animation, p_animation.getDuration());
	}
	
	/**
	 * Plays an animation for p_duration seconds
	 * @param p_animationName
	 * @param p_duration pass a negative value to play indefinitely
	 */
	public CharacterState(String p_animationName, float p_duration)
	{
		m_animationName = p_animationName;
		setDuration(p_duration);
	}
	
	/**
	 * Plays an animation once, even if it's set to loop
	 * @param p_animationName
	 */
	public CharacterState(String p_animationName)
	{
		m_animationName = p_animationName;
	}
	
	CharacterState() {}
	
	public void setAnimation(Animation p_animation)
	{
		m_animation = p_animation;
		m_animationName = m_animation.getName();
	}
	
	public void setAnimation(String p_animationName)
	{
		if(m_character == null)
			m_animationName = p_animationName;
		else
			setAnimation(m_character.getSprite().getTexture().getAnimation(p_animationName));
	}
	
	public Animation getAnimation()
	{
		return m_animation;
	}
	
	public boolean isIndefinite()
	{
		return m_indefinite;
	}
	
	/**
	 * Set the duration of the state
	 * @param p_duration pass a negative value to make indefinite
	 */
	public void setDuration(float p_duration)
	{
		m_duration = p_duration;
		m_timer = m_duration;
		m_indefinite = m_timer < 0;
	}
	
	public void setCharacter(Character p_character)
	{
		m_character = p_character;
	}
	
	public float getDuration()
	{
		return m_duration;
	}
	
	public float getTimer()
	{
		return m_timer;
	}
	
	public boolean isStarted()
	{
		return m_started;
	}
	
	public void pause()
	{
		m_paused = true;
	}
	
	public void resume()
	{
		m_character.getSprite().setAnimation(m_animation);
		m_paused = true;
	}
	
	public boolean isPaused()
	{
		return m_paused;
	}
	
	/**
	 * Starts the animation and calls init(), you probably don't want to override this one
	 */
	public void start()
	{
		if(m_animation == null);
		{
			setAnimation(m_animationName);
			if(m_duration == null)
				setDuration(m_animation.getDuration());
		}
		m_character.getSprite().setAnimation(m_animation);
		m_started = true;
		init();
	}
	
	/**
	 * Override this method to do everything you want at the start of the state (besides starting the animation)
	 */
	protected void init()
	{
		
	}
	
	/**
	 * Deal with the state being interrupted (e.g. by interruptStates() here
	 */
	public void interrupt()
	{
		
	}
	
	/**
	 * Do stuff at the end of the thing
	 */
	public void end()
	{
		
	}
	
	/**
	 * When a state should do something when a certain condition is met,
	 * set up the condition in activationTest(), then put what should be
	 * done here
	 */
	public void activate()
	{
		
	}
	
	/**
	 * Called at every update(), activate() is called upon returning true
	 * @return
	 */
	protected boolean activationTest()
	{
		return false;
	}
	
	/**
	 * @param p_delta
	 * @return false if p_timer <= 0
	 */
	public final void update(float p_delta)
	{
		if(!m_started)
			start();
		onUpdate();
		//TODO: move to onUpdate()
		/*if(activationTest())
			activate();*/
		m_character.getSprite().setAnimation(m_animation);
		m_timer -= p_delta;
		if(!m_indefinite && m_timer < 0)
			m_character.endState();
	}
	
	protected void onUpdate()
	{
		
	}
	
	/**
	 * Called first in character.performAction() so that the state can handle the action
	 * @param p_action
	 * @return true if the character should continue and perform p_action - default implementation always returns false
	 */
	public boolean handleAction(int p_action)
	{
		return false;
	}
}
