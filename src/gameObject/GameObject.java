package gameObject;

import java.util.ArrayList;

import org.dyn4j.geometry.Transform;

public class GameObject
{
	private ArrayList<Component> m_components;
	
	private Transform m_transform;
	
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
	
}
