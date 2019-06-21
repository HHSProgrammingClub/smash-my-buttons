package stages;

import java.awt.geom.AffineTransform;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import graphics.Sprite;
import graphics.Texture;
import graphics.pages.Renderer;
import resourceManager.ResourceManager;

public class MainStage extends Stage
{
	private Sprite m_background;
	
	public MainStage()
	{
		Texture BGtexture = ResourceManager.getResource(Texture.class, "resources/images/BG_placeholder");
		m_background = new Sprite(BGtexture, "default");
		//TODO: Move this scaling stuff into something else - Ben would know better
		m_background.setScale(new Vector2(1.5, 1.5));
		m_background.setPosition(new Vector2(-100.f/32, -75.f/32));
		
		setBackground(m_background);
		
		World aWorld = new World();
		aWorld.getSettings().setStepFrequency(1/30.0);
		aWorld.setGravity(new Vector2(0, 27));
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
		t.setTranslation(6.25, 6);
		groundBody.setTransform(t);
		groundBody.addFixture(groundShape);
		
		Texture groundTexture = ResourceManager.getResource(Texture.class, "resources/images/stage");
			
		Sprite groundSprite = new Sprite(groundTexture, "default");
			
		TerrainPiece ground = new TerrainPiece();
		ground.setBody(groundBody);
		ground.setSprite(groundSprite);
		
		// Offset the stage a little.
		ground.setPosition(new Vector2(1.25, 5));
			
		getPhysicsWorld().addBody(groundBody);
		//getPhysicsWorld().addBody(groundBody2);
			
		m_terrain.add(ground);
	}
}
