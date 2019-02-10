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
	
	public void preUpdate(float p_delta)
	{
		//do physics things
		//F = ma
	}
	
	public void postUpdate(float p_delta)
	{
		//update body transforms
	}
	
	public Body getBody()
	{
		return m_body;
	}
	
	public void onRecieveMessage(OnCollision p_collision)
	{
		
	}
	
	public void onRecieveMessage(OnInitPhysics p_initMessage)
	{
		if(!p_initMessage.getWorld().containsBody(m_body))
		{
			p_initMessage.getWorld().addBody(m_body);
		}
	}

}
