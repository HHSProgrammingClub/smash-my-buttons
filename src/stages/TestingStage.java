package stages;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Sprite;
import graphics.Texture;

public class TestingStage extends Stage
{
	public TestingStage()
	{
		World aWorld = new World();
		aWorld.setGravity(new Vector2(0, 23));
		//set up world
		setPhysicsWorld(aWorld);
		m_name = "TestingStage";
		createLand();
	}
	
	private void createLand()
	{
		Rectangle groundShape = new Rectangle(10, 1);
		groundShape.translate(0, 0);
		Body groundBody = new Body();
		Transform t = new Transform();
		t.setTranslation(7, 6);
		groundBody.setTransform(t);
		groundBody.addFixture(groundShape);
		
		Rectangle groundShape2 = new Rectangle(3, 1);
		groundShape2.translate(0, 0);
		Body groundBody2 = new Body();
		Transform t2 = new Transform();
		t2.setTranslation(7, 4);
		groundBody2.setTransform(t2);
		groundBody2.addFixture(groundShape2);
		groundBody2.setMassType(MassType.INFINITE);
		
		Texture groundTexture = new Texture();
		groundTexture.openResource("resources/images/testing_ground");
			
		Sprite groundSprite = new Sprite(groundTexture, "ground");
			
		TerrainPiece ground = new TerrainPiece();
		ground.setBody(groundBody);
		ground.setSprite(groundSprite);
		TerrainPiece ground2 = new TerrainPiece();
		ground2.setBody(groundBody2);
		ground2.setSprite(groundSprite);
			
		getPhysicsWorld().addBody(groundBody);
		//getPhysicsWorld().addBody(groundBody2);
			
		m_terrain.add(ground);
	}
}
