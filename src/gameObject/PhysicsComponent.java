package gameObject;

import org.dyn4j.dynamics.Body;

public class PhysicsComponent extends Component
{
	Body m_body;

	public PhysicsComponent(GameObject p_object)
	{
		super(p_object);
		
		m_body = new Body();
	}
	
	void preUpdate(float p_delta)
	{
		//do physics things
		//F = ma
	}
	
	void postUpdate(float p_delta)
	{
		//update body transforms
	}
	
	void onRecieveMessage(OnCollision p_collision)
	{
		
	}

}
