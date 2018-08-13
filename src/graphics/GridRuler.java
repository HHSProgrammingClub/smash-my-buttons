package graphics;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import org.dyn4j.geometry.Vector2;

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
				p_renderer.drawLine(i, 0, i, 11, Color.BLACK, 0.05f, 0.25f);
			for(int i = 0; i < 11; i++)
				p_renderer.drawLine(0, i, 13, i, Color.BLACK, 0.05f, 0.25f);
			
			
			// Draw coordinates text
			for (int x = 0; x < 13; x++)
			{
				for (int y = 0; y < 11; y++)
				{
					AffineTransform t = new AffineTransform();
					t.translate(x, y);
					t.translate(0.02, 0.2); // Offset it a bit for a better look
					t.scale(0.2, 0.2); // Scale it down otherwise we get huge text
					p_renderer.pushTransform(t);
					p_renderer.drawText("(" + x + ", " + y + ")", "Consolas", Color.BLACK,  1, 0.25f);
					p_renderer.popTransform();
				}
			}
			
			AffineTransform textOffset = new AffineTransform();
			textOffset.scale(0.25, 0.25);
			
			for(int i = 1; i <= m_charas.size(); i++)
			{
				// Adjust the precision so we aren't getting 10 to 20 digit numbers
				Vector2 pos = m_charas.get(i-1).getBody().getWorldCenter();
				String strCoord = String.format("(%.2f, %.2f)", pos.x, pos.y);
				
				String theory = "Player " + i + ": " + strCoord;
				textOffset.translate(0.01, i);
				
				p_renderer.pushTransform(textOffset);
				p_renderer.drawText(theory, "Consolas", Color.BLACK, 1, 0.5f);
				p_renderer.popTransform();
			}
		}
	}
}
