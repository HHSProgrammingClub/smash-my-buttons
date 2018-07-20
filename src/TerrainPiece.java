import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Vector2;

public class TerrainPiece implements Drawable
{
	
	public void setBody(Body p_body)
	{
		m_body = p_body;
	}
	
	public void setSprite(Sprite p_sprite)
	{
		m_sprite = p_sprite;
		m_sprite.setPosition(getPosition());
	}
	
	public Vector2 getPosition()
	{
		AABB bodyBounds = m_body.createAABB();
		return new Vector2(bodyBounds.getMinX(), bodyBounds.getMinY());
	}
	
	//i think this is how it works
	@Override
	public void draw(Renderer p_renderer)
	{
		m_sprite.draw(p_renderer);
	}
	
	private Body m_body;
	private Sprite m_sprite;
}
