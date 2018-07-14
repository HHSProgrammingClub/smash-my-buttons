import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture 
{
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private BufferedImage image;
	
	public Animation getAnimation(String name)
	{
		for(Animation a : animations)
		{
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}
	
	public void openImage(String path)
	{
		//I'm guessing this is part of WGE. Putting this here anyway
        try
            {image = ImageIO.read(new File("Resources/" + path + ".png"));}
        catch(IOException e)
            {e.printStackTrace();}
	}
	
	public void openAtlas(String atlas)
	{
		//more WGE, I presume
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
}
