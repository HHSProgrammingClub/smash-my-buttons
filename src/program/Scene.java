package program;
import java.util.ArrayList;

import gameObject.GameObject;
import gameObject.Message;

public class Scene
{
	private ArrayList<GameObject> m_objects = new ArrayList<GameObject>();
	
	public void addObject(GameObject p_object)
	{
		assert p_object != null : " attepting to add a null object";
		m_objects.add(p_object);
	}
	
	public void receiveMessage(Message p_message)
	{
		for(GameObject object : m_objects)
		{
			object.receiveMessage(p_message);
		}
	}
}
