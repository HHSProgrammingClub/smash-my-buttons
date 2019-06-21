package graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import resourceManager.Resource;

public class Texture implements Resource
{
	private ArrayList<Animation> m_animations = new ArrayList<Animation>();
	private Image m_image;
	
	/**
	 * Get animation by name
	 * @param name Name of the animation
	 * @return Animation object. Null if not found.
	 */
	public Animation getAnimation(String name)
	{
		for(Animation a : m_animations)
			if(a.getName().equals(name))
				return a;
		return null;
	}
	
	/**
	 * Open an image from your sources. This only opens png and xml files.
	 * @param p_path Path to both the texture and the atlas. Just leave out the extension.
	 */
	@Override
	public void openResource(String p_path)
	{
		try
		{
			InputStream imageStream = getClass().getClassLoader().getResourceAsStream(p_path + ".png");
			if (imageStream == null)
				throw new FileNotFoundException("Could not find image resource for \"" + p_path + ".png\"");
			openImage(imageStream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{	
			InputStream atlasStream = getClass().getClassLoader().getResourceAsStream(p_path + ".xml");
			if (atlasStream == null)
				throw new FileNotFoundException("Could not find atlas resource for \"" + p_path + ".xml\"");
			openAtlas(atlasStream);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the BufferedImage object. Used internally in the renderer.
	 * @return BufferedImage object.
	 */
	public Image getImage()
	{
		return m_image;
	}
	
	/**
	 * Open an image from a stream.
	 * @param p_stream Stream to atlas.
	 * @throws IOException Could throw if image is not formatted correctly.
	 */
	private void openImage(InputStream p_stream) throws IOException
	{
		try
		{
			m_image = new Image(p_stream, "file.png", false);
		}
		catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Open an atlas from a stream
	 * @param p_stream Stream to atlas.
	 * @throws Exception Could throw if parse has failed somehow.
	 */
	private void openAtlas(InputStream p_stream) throws Exception
	{
		// https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
		try
		{
			// Parse the document
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(p_stream);
			doc.getDocumentElement().normalize();
			
			// Create an Animation object for each animation
			NodeList nodes = doc.getElementsByTagName("subtexture");
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element ele = (Element)node;
					Animation animation = new Animation();
					animation.parse(ele);
					m_animations.add(animation);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
}
