package graphics;


import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.Color;

import characters.Character;

import graphics.pages.Renderer;
import stages.Stage;

public class GridRuler implements Drawable
{
	private boolean isVisible = true;
	
	private Stage m_stage;
	private ArrayList<Character> m_charas= new ArrayList<Character>();
	
	public void setVisible(boolean p_visibility)
	{
		isVisible = p_visibility;
	}
	
	public boolean getVisibility()
	{
		return isVisible;
	}
	
	public void setStage(Stage p_stage)
	{
		m_stage = p_stage;
	}
	
	public void addCharacter(Character p_chara)
	{
		m_charas.add(p_chara);
	}
	
	@Override
	public void draw(Renderer p_renderer)
	{
		//TODO: account for different stage sizes
		if(isVisible)
		{
			// Draw lines
			for(int i = 0; i < 13; i++)
				p_renderer.drawLine(i, 0, i, 11, Color.red, 0.05f, 0.25f);
			for(int i = 0; i < 11; i++)
				p_renderer.drawLine(0, i, 13, i, Color.red, 0.05f, 0.25f);
			
			
			// Draw coordinates text
			for (int x = 0; x < 13; x++)
			{
				for (int y = 0; y < 11; y++)
				{
					p_renderer.pushTransform()
						.translate(x, y)
						.translate(0.02f, 0.02f) // Offset it a bit for a better look
						.scale(0.2f, 0.2f); // Scale it down otherwise we get huge text
					p_renderer.drawText("(" + x + ", " + y + ")", "Consolas", Color.black,  1, 0.25f);
					p_renderer.popTransform();
				}
			}
			
			for(int i = 1; i <= m_charas.size(); i++)
			{
				// Adjust the precision so we aren't getting 10 to 20 digit numbers
				Vector2 pos = m_charas.get(i-1).getBody().getWorldCenter();
				String strCoord = String.format("(%.2f, %.2f)", pos.x, pos.y);
				
				String theory = "Player " + i + ": " + strCoord;
				
				p_renderer.pushTransform()
					.scale(0.25f, 0.25f)
					.translate(0.01f, i);
				p_renderer.drawText(theory, "Consolas", Color.black, 1, 0.5f);
				p_renderer.popTransform();
			}
		}
	}
}
