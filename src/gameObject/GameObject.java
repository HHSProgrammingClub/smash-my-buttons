package gameObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.dyn4j.geometry.Transform;

import graphics.Texture;
import resourceManager.ResourceManager;

public class GameObject
{
	private ArrayList<Component> m_components = new ArrayList<Component>();
	
	private Transform m_transform = new Transform();
	
	public Transform getTransform()
	{
		return m_transform;
	}

	public void setTransform(Transform m_transform)
	{
		this.m_transform = m_transform;
	}

	//break update things down into messages?
	public void preUpdate()
	{
		for(Component comp : m_components)
			comp.preUpdate();
	}
	
	public void update()
	{
		for(Component comp : m_components)
			comp.update();
	}
	
	public void postUpdate()
	{
		for(Component comp : m_components)
			comp.postUpdate();
	}
	
	public void addJankSprite()
	{
		Texture jankTexture = ResourceManager.getResource(Texture.class, "resources/images/birboi");
		
		m_components.add(new SpriteComponent(this, jankTexture, "idle"));
	}
	
	public <T> T addComponent(Class<T> p_class)
    {
        try
		{
			T comp = p_class.getDeclaredConstructor(GameObject.class).newInstance(this);
			m_components.add((Component) comp);
			return comp;
		}
        catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void receiveMessage(Message p_message)
	{
		Class messageClass = p_message.getClass();
		
		for (Component comp : m_components)
		{
			Class componentClass = comp.getClass();
			try
			{
				// check if the component has a method named "onRecievedMessage" that accepts the type of message being received
				Method m = componentClass.getMethod("onRecieveMessage", messageClass);
				// if it does, call it
				m.invoke(comp, p_message);
			}
			catch(NoSuchMethodException e)
			{
				// no thinging-desu
				// do nuthin
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
}
