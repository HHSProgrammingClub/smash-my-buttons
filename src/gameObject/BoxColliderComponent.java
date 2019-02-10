package gameObject;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Rectangle;

public class BoxColliderComponent extends Component
{
	private BodyFixture m_fixture;
	
	public BoxColliderComponent(GameObject p_object)
	{
		super(p_object);
		
		m_fixture = new BodyFixture(new Rectangle(1, 1));
		m_fixture.setUserData(this);
	}
	
	public void setSize(float p_width, float p_height)
	{
		Convex shape = m_fixture.getShape();
		
		shape = new Rectangle(p_width, p_height);
	}
	
	public BodyFixture getFixture()
	{
		return m_fixture;
	}
	
	void onRecieveMessage(OnInitPhysics p_initMessage)
	{
		PhysicsComponent phys = getObject().getFirstComponent(PhysicsComponent.class);
		
		if(phys == null)
			return;
		
		if(!phys.getBody().containsFixture(m_fixture))
			phys.getBody().addFixture(m_fixture);
	}
}
