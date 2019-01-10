package gameObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.dyn4j.geometry.Transform;

public class GameObject
{
	private ArrayList<Component> m_components;
	
	private Transform m_transform;
	
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void recieveMessage(Message p_message)
	{
		Class messageClass = p_message.getClass();
		
		for (Component comp : m_components)
		{
			Class componentClass = comp.getClass();
			try
			{
				Method m = componentClass.getMethod("onRecievedMessage", messageClass.getClass());
				m.invoke(comp, p_message);
			}
			catch(NoSuchMethodException e)
			{
				// no thinging-desu
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
}
