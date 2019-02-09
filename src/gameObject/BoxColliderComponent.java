package gameObject;

import org.dyn4j.dynamics.BodyFixture;
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
	
	public BodyFixture getFixture()
	{
		return m_fixture;
	}
}
