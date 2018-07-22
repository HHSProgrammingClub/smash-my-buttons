import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class TerrainPiece implements Drawable
{
	private Body m_body;
	private Sprite m_sprite;
	
	public TerrainPiece() {}
	
	public TerrainPiece(Body p_body)
	{
		setBody(p_body);
		m_body.setMassType(MassType.INFINITE);
	}
	
	public TerrainPiece(Convex p_shape)
	{
		m_body = new Body ();
		m_body.setMassType(MassType.INFINITE);
		m_body.addFixture(p_shape);
	}
	
	public void setBody(Body p_body)
	{
		m_body = p_body;
	}
	
	public Body getBody()
	{
		return m_body;
	}
	
	public void setSprite(Sprite p_sprite)
	{
		m_sprite = p_sprite;
		m_sprite.setPosition(getPosition());
	}
	
	private Vector2 getPosition()
	{
		AABB bodyBounds = m_body.createAABB();
		return new Vector2(bodyBounds.getMinX(), bodyBounds.getMinY());
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		m_sprite.draw(p_renderer);
	}
	
}
