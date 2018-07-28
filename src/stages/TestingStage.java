package stages;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Rectangle;

import graphics.Sprite;
import graphics.Texture;

public class TestingStage extends Stage
{
	public TestingStage()
	{
		World aWorld = new World();
		//set up world
		setPhysicsWorld(aWorld);
		m_name = "TestingStage";
		createLand();
	}
	
	private void createLand()
	{
		Rectangle groundShape = new Rectangle(3, 1);
		
		Body groundBody = new Body();
		groundBody.addFixture(groundShape);
		groundBody.translate(4, 14);
		
		Texture groundTexture = new Texture();
		groundTexture.openResource("resources/images/testing_ground");
		
		Sprite groundSprite = new Sprite(groundTexture);
		
		TerrainPiece ground = new TerrainPiece();
		ground.setBody(groundBody);
		ground.setSprite(groundSprite);
		
		getPhysicsWorld().addBody(groundBody);
		
		m_terrain.add(ground);
	}
}
